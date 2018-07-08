package in.kyle.ast.code.file;

import in.kyle.ast.util.StringTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JavaField implements WritableElement {
    
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
        return getGeneric() != null;
    }
    
    public boolean hasValue() {
        return getValue() != null;
    }
    
    @Override
    public String write() {
        return StringTemplate.render("field", this);
    }
}
