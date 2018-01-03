package in.kyle.parser.unit;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.body.classtype.JClassMember;
import in.kyle.parser.unit.body.JTypeBody;
import lombok.Data;

@Data
public abstract class JType<T extends JTypeBody> extends Modifiable implements JClassMember {
    
    private JIdentifier identifier;
    private T body;
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(identifier, body);
    }
}
