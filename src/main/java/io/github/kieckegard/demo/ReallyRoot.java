package io.github.kieckegard.demo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class ReallyRoot {
    
    @JsonDeserialize(using = PolymorphicDeserializer.class)
    private Root root;

    protected ReallyRoot() {}

    public Root getRoot() {
        return this.root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }


    @Override
    public String toString() {
        return "{" +
            " root='" + getRoot() + "'" +
            "}";
    }
}