package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.CodeWriter;

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
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(name().toLowerCase());
    }
    
    public static JModifier fromJava(String string) {
        return valueOf(string.toUpperCase());
    }
}
