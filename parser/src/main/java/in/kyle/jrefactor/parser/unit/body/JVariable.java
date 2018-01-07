package in.kyle.jrefactor.parser.unit.body;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.unit.JIdentifier;
import lombok.Data;

@Data
public class JVariable implements JObject {
    
    private JIdentifier identifier;
    private JVariableInitializer initializer;
    
    @Override
    public void write(CodeWriter writer) {
        if (initializer != null) {
            writer.append("{} = {}", identifier, initializer);
        } else {
            writer.append(identifier);
        }
    }
    
}
