package io.github.kieckegard.demo;

public class SecondLevel1 extends FirstLevel {
    
    private String c1;

    protected SecondLevel1() {
        super();
        super.subType = FirstLevel.SECOND_LEVEL_1;
    }

    public String getC1() {
        return this.c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    @Override
    public String toString() {
        return "{" +
            " c1='" + getC1() + "'" +
            "}";
    }

}