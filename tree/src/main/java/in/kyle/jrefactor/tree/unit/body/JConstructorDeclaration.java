package in.kyle.jrefactor.tree.unit.body;

import in.kyle.jrefactor.tree.statement.JBlock;
import in.kyle.jrefactor.tree.unit.JParameterList;
import in.kyle.jrefactor.tree.unit.JThrowsList;
import in.kyle.jrefactor.tree.unit.JTypeName;
import in.kyle.jrefactor.tree.unit.Typeable;
import in.kyle.jrefactor.tree.unit.types.classtype.JClassMember;
import lombok.Data;

@Data
public class JConstructorDeclaration extends Typeable implements JClassMember {
    
    private JTypeName identifier;
    private JParameterList parameterList = new JParameterList();
    private JThrowsList throwsList = new JThrowsList();
    private JBlock body = new JBlock();
    
}
