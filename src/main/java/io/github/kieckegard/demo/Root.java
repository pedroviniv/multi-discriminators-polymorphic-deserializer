package io.github.kieckegard.demo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.github.kieckegard.demo.spi.SubType;
import io.github.kieckegard.demo.spi.SubTypes;
import io.github.kieckegard.demo.spi.TypeInfo;

@TypeInfo(property = "type")
@SubTypes({
    @SubType(value = FirstLevel.class, name = Root.FIRST_LEVEL)
})
@JsonDeserialize(using = PolymorphicDeserializer.class)
public class Root {

    public static final String FIRST_LEVEL = "FIRST_LEVEL";

    private String a;
    protected String type;
    
    protected Root() {}

    public String getA() {
        return this.a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" +
            " a='" + getA() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }

}