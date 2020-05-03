package io.github.kieckegard.demo;

public class SecondLevel2 extends FirstLevel {
    
    private String c2;

    protected SecondLevel2() {
        super();
        super.subType = FirstLevel.SECOND_LEVEL_2;
    }

    public String getC2() {
        return this.c2;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

    @Override
    public String toString() {
        return "{" +
            " c2='" + getC2() + "'" +
            "}";
    }
}