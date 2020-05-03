package io.github.kieckegard.demo;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import io.github.kieckegard.demo.spi.SubType;
import io.github.kieckegard.demo.spi.SubTypes;
import io.github.kieckegard.demo.spi.TypeInfo;

public class PolymorphicDeserializer extends JsonDeserializer<Object> implements ContextualDeserializer {

    private JavaType type;

    /**
     * just for assigning the annotated field type as member field of this instance
     */
    @Override
    public JsonDeserializer<?> createContextual(final DeserializationContext ctxt, final BeanProperty property)
            throws JsonMappingException {
        if (property != null) {
            this.type = property.getType();
        }
        return this;
    }

    /**
     * navigates through the given class hierarchy to find the concrete implementation based
     * in the provided discriminator properties defined in the class 
     * @param current
     * @param fieldValueExtractor
     * @return
     */
    private Class<?> resolveConcreteType(final Class<?> current, Function<String, String> fieldValueExtractor) {

        /**
         * gets, through reflection, the TypeInfo annotation from the class
         */
        final TypeInfo currentTypeInfo = current.getAnnotation(TypeInfo.class);

        /**
         * if it isn't annotated, the given class is the leaf
         */
        if (currentTypeInfo == null) {
            return current;
        }

        /**
         * gets the SubTypes annotation thich defines what is the specializations
         * of the super type mapped to the discriminator value
         */
        SubTypes subTypes = current.getAnnotation(SubTypes.class);
        final Map<String, Class<?>> subTypesByDiscriminatorValue = Stream.of(subTypes.value())
            .collect(Collectors.toMap(SubType::name, SubType::value));

        /**
         * gets the JSON property to use as the discriminator
         */
        final String subTypeDiscriminatorProperty = currentTypeInfo.property();

        /**
         * gets the discriminator value using the JSON property obtained above
         */
        String discriminatorValue = fieldValueExtractor.apply(subTypeDiscriminatorProperty);

        /**
         * finally, through the map created above, it gets the class mapped
         * to the discriminator value.
         */
        Class<?> subType = subTypesByDiscriminatorValue.get(discriminatorValue);

        /**
         * recursively calls the method with the subType found and the
         * JSON field value extractor.
         */
        return this.resolveConcreteType(subType, fieldValueExtractor);
    }

    @Override
    public Object deserialize(final JsonParser jsonParser, final DeserializationContext context)
            throws IOException, JsonProcessingException {

        /**
         * gets the json node being deserialized
         */
        final ObjectCodec oc = jsonParser.getCodec();
        final JsonNode node = oc.readTree(jsonParser);

        /**
         * gets the type of the node being serialized
         */
        final Class<?> deserializingType = this.type.getRawClass();

        /**
         * resolve the concrete type based in the node being serialized and the super type
         * of the field
         */
        Class<?> concreteType = this.resolveConcreteType(deserializingType, (fieldName) -> {
            JsonNode jsonNode = node.get(fieldName);
            return jsonNode.asText();
        });

        /**
         * traverses the node to get the node parser and finally,
         * reads the node providing the concrete class that must be created
         */
        JsonParser parserOfNode = node.traverse(oc);
        Object concreteInstance = parserOfNode.readValueAs(concreteType);

        return concreteInstance;
    }

    
    
}