### JRefactor

---

Some day this is going to be a Java obfuscator, until then...

##### Usage Examples
######Literal Simplification
[LiteralOptimizer.java](kylepls/JRefactor/blob/master/refactor/src/test/java/in/kyle/jrefactor/refactor/LiteralOptimizer.java)
```Java
    //Subject:
    private static final String string = "Hello" + " World" + 1 + (1 + 1 + 1 + 1) + ((" Swag")); 

    //Pass 1:
    private static final String string = "Hello" + " World" + 1 + (1 + 1 + 1 + 1) + ((" Swag"));
    
    //Pass 2:
    private static final String string = "Hello World1" + (3 + 1) + " Swag";
    
    //Pass 3:
    private static final String string = "Hello World1" + (4) + " Swag";
    
    //Pass 4:
    private static final String string = "Hello World14" + " Swag";
    
    //Pass 5:
    private static final String string = "Hello World14 Swag";
    
    //Pass 6:
    private static final String string = "Hello World14 Swag";
    
    //Pass 7:
    private static final String string = "Hello World14 Swag";
```