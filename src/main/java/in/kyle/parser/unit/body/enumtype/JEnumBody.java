package in.kyle.parser.unit.body.enumtype;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.parser.unit.body.classtype.JClassBody;
import lombok.Data;

@Data
public class JEnumBody extends JClassBody {
    
    private Set<JEnumConstant> constants = new LinkedHashSet<>();
    
    public boolean addConstant(JEnumConstant constant) {
        return constants.add(constant);
    }
    
    public boolean removeConstant(JEnumConstant constant) {
        return constants.remove(constant);
    }
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(constants);
    }
}
