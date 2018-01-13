package in.kyle.jrefactor.tree.unit.body;

import in.kyle.jrefactor.tree.JObject;
import in.kyle.jrefactor.tree.unit.JIdentifier;
import in.kyle.jrefactor.tree.unit.JParameterList;
import in.kyle.jrefactor.tree.unit.JThrowsList;
import in.kyle.jrefactor.tree.unit.JTypeName;
import in.kyle.jrefactor.tree.unit.Typeable;
import lombok.Data;

@Data
public class JMethodHeader extends Typeable implements JObject {
    
    private JIdentifier name;
    private JTypeName resultType = JTypeName.VOID;
    private JParameterList parameterList = new JParameterList();
    private JThrowsList throwsList = new JThrowsList();
    
    public JMethodHeader(JIdentifier name) {
        this.name = name;
    }
}
