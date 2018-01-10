package in.kyle.jrefactor.parser.unit.body;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.unit.JIdentifier;
import in.kyle.jrefactor.parser.unit.JParameterList;
import in.kyle.jrefactor.parser.unit.JThrowsList;
import in.kyle.jrefactor.parser.unit.JTypeName;
import in.kyle.jrefactor.parser.unit.Typeable;
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
