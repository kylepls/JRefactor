package in.kyle.jrefactor.parser.unit.types.enumtype;

import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.unit.types.classtype.JClassBody;
import lombok.Data;

@Data
public class JEnumBody extends JClassBody {
    
    private JObjectList<JEnumConstant> constants = new JObjectList<>();
    
}
