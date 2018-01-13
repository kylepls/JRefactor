package in.kyle.jrefactor.tree.unit.types.enumtype;

import in.kyle.jrefactor.tree.JObjectList;
import in.kyle.jrefactor.tree.unit.types.classtype.JClassBody;
import lombok.Data;

@Data
public class JEnumBody extends JClassBody {
    
    private JObjectList<JEnumConstant> constants = new JObjectList<>();
    
}
