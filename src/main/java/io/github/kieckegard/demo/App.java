package io.github.kieckegard.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Hello world!
 *
 */
public class App {

    public static final String JSON = "{ \"root\": { \"a\": \"A\", \"type\": \"FIRST_LEVEL\", \"subType\": \"SECOND_LEVEL_1\", \"b\": \"B\", \"c1\": \"C1\" } }";

    public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ReallyRoot root = objectMapper.readValue(JSON, ReallyRoot.class);
        System.out.println(root);
    }
}
