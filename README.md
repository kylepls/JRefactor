### JRefactor

An over-the-top API for writing refactoring utilities for Java code.

---

##### Usage Examples
###### Literal Simplification
[LiteralOptimizer.java](https://github.com/kylepls/JRefactor/blob/master/refactor/src/test/java/in/kyle/jrefactor/refactor/LiteralOptimizer.java)
```Java
    //Subject:
    private static final String string = "Hello" + " World" + 1 + (1 + 1 + 1 + 1) + ((" Two")); 

    //Pass 1:
    private static final String string = "Hello" + " World" + 1 + (1 + 1 + 1 + 1) + ((" Two"));
    
    //Pass 2:
    private static final String string = "Hello World1" + (3 + 1) + " Two";
    
    //Pass 3:
    private static final String string = "Hello World1" + (4) + " Two";
    
    //Pass 4:
    private static final String string = "Hello World14" + " Two";
    
    //Pass 5:
    private static final String string = "Hello World14 Two";
    
    //Pass 6:
    private static final String string = "Hello World14 Two";
    
    //Pass 7:
    private static final String string = "Hello World14 Two";
```
