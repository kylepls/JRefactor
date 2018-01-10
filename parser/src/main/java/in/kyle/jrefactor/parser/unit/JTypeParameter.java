package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.JObjectList;
import lombok.Data;

@Data
public class JTypeParameter implements JObject {
    
    private JIdentifier name;
    private JObjectList<JTypeName> bounds = new JObjectList<>();
    
    public JTypeParameter(JIdentifier name) {
        this.name = name;
    }
}
