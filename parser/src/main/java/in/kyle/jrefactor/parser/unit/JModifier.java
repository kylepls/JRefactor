package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObject;

public enum JModifier implements JObject {
    PUBLIC,
    PRIVATE,
    PROTECTED,
    FINAL,
    STATIC,
    STRICTFP,
    VOLATILE,
    ABSTRACT,
    SYNCHRONIZED,
    TRANSIENT,
    NATIVE;
    
    public static JModifier fromJava(String string) {
        return valueOf(string.toUpperCase());
    }
}
