package in.kyle.jrefactor.tree.unit;

import in.kyle.jrefactor.tree.JObject;
import lombok.Data;

@Data
public class JIdentifier implements JObject {
    
    private String name;
    
    public JIdentifier(String name) {
        this.name = name;
    }
}
