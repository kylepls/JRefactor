package in.kyle.parser.unit;

import java.util.Collections;
import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;

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
    
    @Override
    public List<JObject> getChildren() {
        return Collections.emptyList();
    }
    
    public static JModifier fromJava(String string) {
        return valueOf(string.toUpperCase());
    }
}
