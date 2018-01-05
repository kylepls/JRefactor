package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.CodeWriter;
import lombok.Data;

@Data
public class JIdentifier implements JObject {
    
    private String name;
    
    public JIdentifier(String name) {
        this.name = name;
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(name);
    }
    
}
