package io.github.kieckegard.demo;

import io.github.kieckegard.demo.spi.SubType;
import io.github.kieckegard.demo.spi.SubTypes;
import io.github.kieckegard.demo.spi.TypeInfo;

@TypeInfo(property = "subType")
@SubTypes({
    @SubType(value = SecondLevel1.class, name = FirstLevel.SECOND_LEVEL_1),
    @SubType(value = SecondLevel2.class, name = FirstLevel.SECOND_LEVEL_2)
})
public class FirstLevel extends Root {
    
    public static final String SECOND_LEVEL_1 = "SECOND_LEVEL_1";
    public static final String SECOND_LEVEL_2 = "SECOND_LEVEL_2";
    private String b;
    protected String subType;

    protected FirstLevel() {
        super();
        super.type = Root.FIRST_LEVEL;
    }


    public String getB() {
        return this.b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getSubType() {
        return this.subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    @Override
    public String toString() {
        return "{" +
            " b='" + getB() + "'" +
            ", subType='" + getSubType() + "'" +
            "}";
    }

}