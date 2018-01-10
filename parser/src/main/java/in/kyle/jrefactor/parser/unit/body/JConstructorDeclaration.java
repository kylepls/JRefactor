package in.kyle.jrefactor.parser.unit.body;

import in.kyle.jrefactor.parser.statement.JBlock;
import in.kyle.jrefactor.parser.unit.JParameterList;
import in.kyle.jrefactor.parser.unit.JThrowsList;
import in.kyle.jrefactor.parser.unit.JTypeName;
import in.kyle.jrefactor.parser.unit.Typeable;
import in.kyle.jrefactor.parser.unit.types.classtype.JClassMember;
import lombok.Data;

@Data
public class JConstructorDeclaration extends Typeable implements JClassMember {
    
    private JTypeName identifier;
    private JParameterList parameterList = new JParameterList();
    private JThrowsList throwsList = new JThrowsList();
    private JBlock body = new JBlock();
    
}
