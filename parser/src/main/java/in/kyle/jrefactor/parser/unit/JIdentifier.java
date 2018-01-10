package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObject;
import lombok.Data;

@Data
public class JIdentifier implements JObject {
    
    private String name;
    
    public JIdentifier(String name) {
        this.name = name;
    }
}
