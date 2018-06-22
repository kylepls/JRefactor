package in.kyle.ast.code.file;

import in.kyle.ast.util.Formatter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Field implements WritableElement {
    
    private String type;
    private String generic;
    private String name;
    private String value;
    
    public Field(String type, String name) {
        if (type.contains("<")) {
            this.generic = type.substring(type.indexOf("<") + 1, type.lastIndexOf(">"));
            type = type.substring(0, type.indexOf("<"));
        }
        this.type = type;
        this.name = name;
    }
    
    @Override
    public String write() {
        return Formatter.fromTemplate("field", this);
    }
}
