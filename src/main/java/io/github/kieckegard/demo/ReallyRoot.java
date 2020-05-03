package io.github.kieckegard.demo;

public class ReallyRoot {
    
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