package in.kyle.parser.unit.body;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JVariable implements JObject {
    
    private String name;
    private JVariableInitializer initializer;
    
    @Override
    public void write(CodeWriter writer) {
        if (initializer != null) {
            writer.append("{} = {}", name, initializer);
        } else {
            writer.append(name);
        }
    }
    
}
