package in.kyle.jrefactor.tree.unit;

import in.kyle.jrefactor.tree.JObject;
import in.kyle.jrefactor.tree.JObjectList;
import lombok.Data;

@Data
public class JTypeParameter implements JObject {
    
    private JIdentifier name;
    private JObjectList<JTypeName> bounds = new JObjectList<>();
    
    public JTypeParameter(JIdentifier name) {
        this.name = name;
    }
}
