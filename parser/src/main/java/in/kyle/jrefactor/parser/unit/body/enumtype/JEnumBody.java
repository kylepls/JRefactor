package in.kyle.jrefactor.parser.unit.body.enumtype;

import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.unit.body.classtype.JClassBody;
import lombok.Data;

@Data
public class JEnumBody extends JClassBody {
    
    private JObjectList<JEnumConstant> constants = new JObjectList<>();
    
    public boolean addConstant(JEnumConstant constant) {
        return constants.add(constant);
    }
    
    public boolean removeConstant(JEnumConstant constant) {
        return constants.remove(constant);
    }
    
}
