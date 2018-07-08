package in.kyle.ast.code.file;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JavaField {
    
    private String type;
    private String generic;
    private String name;
    private String value;
    
    public JavaField(String type, String name) {
        if (type.contains("<")) {
            this.generic = type.substring(type.indexOf("<") + 1, type.lastIndexOf(">"));
            type = type.substring(0, type.indexOf("<"));
        }
        this.type = type;
        this.name = name;
    }
    
    public boolean hasGeneric() {
        return generic != null;
    }
    
    public boolean hasValue() {
        return getValue() != null;
    }
    
    public String getSimpleType() {
        if (type.contains(".")) {
            return type.substring(type.lastIndexOf(".") + 1);
        } else {
            return type;
        }
    }
}
