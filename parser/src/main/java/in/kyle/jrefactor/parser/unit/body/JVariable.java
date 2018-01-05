package in.kyle.jrefactor.parser.unit.body;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.CodeWriter;
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
