# multi-discriminators-polymorphic-deserializer
A generic jackson JsonDeserializer that supports multiple discriminator columns when deserializing a supertype that has multi level inheritance

The jackson-databind library, by default, doesn't support defining multiple @JsonTypeInfo and they doesn't have plans to support such feature,
as can be seen here:

- https://github.com/FasterXML/jackson-databind/issues/374
- https://github.com/FasterXML/jackson-databind/issues/2000

Which makes hard to (de)serialize JSONs when we have a multi-level inheritance with multiple fields being used as discriminators like:

```java
public abstract class QuestionChoice {

 public static final String INPUT = "INPUT";

 private String description;
 protected String type;
 
 //no-arg-constructor, getters & setters
}

public abstract class InputQuestionChoice extends QuestionChoice {

  public static final String NUMBER = "NUMBER";
  public static final String TEXT = "TEXT";

  private String placeholder;
  protected String inputType;
  
  protected InputQuestionChoice() {
    super.type = QuestionChoice.INPUT;
  }
  
  //getters & setters
}

public class NumberInputQuestionChoice extends InputQuestionChoice {

  private int min;
  private int max;
  
  protected NumberInputQuestionChoice() {
    super.inputType = InputQuestionChoice.NUMBER;
  }
  
  //getters & setters
}

public class TextInputQuestionChoice extends InputQuestionChoice {

  private int minLength;
  private int maxLength;
  private String pattern;
  
  protected TextInputQuestionChoice() {
    super.inputType = InputQuestionChoice.TEXT;
  }
  
  //getters & setters
}
```

Since jackson-databind limits us to define just one @JsonTypeInfo, we would have to maintain just a single field with all the possible discriminator
types which wouldn't scale well as the hierarchy grows.

To solve this, this project offers a custom JsonDeserializer that makes possible to define multiple discriminator fields. Example below:

```java
@TypeInfo(property = "type") // default is "type" so in this case we woudln't have to explicitly define the property
@SubTypes({
  @SubType(value = InputQuestionChoice.class, name = QuestionChoice.INPUT)
})
public abstract class QuestionChoice {

 public static final String INPUT = "INPUT";

 private String description;
 protected String type;
 
 //no-arg-constructor, getters & setters
}

@TypeInfo(property = "inputType")
@SubTypes({
  @SubType(value = NumberInputQuestionChoice.class, name = QuestionChoice.NUMBER),
  @SubType(value = TextInputQuestionChoice.class, name = QuestionChoice.TEXT)
})
public abstract class InputQuestionChoice extends QuestionChoice {

  public static final String NUMBER = "NUMBER";
  public static final String TEXT = "TEXT";

  private String placeholder;
  protected String inputType;
  
  protected InputQuestionChoice() {
    super.type = QuestionChoice.INPUT;
  }
  
  //getters & setters
}

public class NumberInputQuestionChoice extends InputQuestionChoice {

  private int min;
  private int max;
  
  protected NumberInputQuestionChoice() {
    super.inputType = InputQuestionChoice.NUMBER;
  }
  
  //getters & setters
}

public class TextInputQuestionChoice extends InputQuestionChoice {

  private int minLength;
  private int maxLength;
  private String pattern;
  
  protected TextInputQuestionChoice() {
    super.inputType = InputQuestionChoice.TEXT;
  }
  
  //getters & setters
}
```
