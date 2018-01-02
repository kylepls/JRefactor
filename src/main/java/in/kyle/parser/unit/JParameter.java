package in.kyle.parser.unit;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JParameter extends Modifiable {
    
    private String name;
    private JTypeName type;
    
    @Override
    public void write(CodeWriter writer) {
        writeAnnotations(writer);
        writeModifiers(writer);
        writer.append("{} {}", name, type);
    }
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(type);
    }
}
