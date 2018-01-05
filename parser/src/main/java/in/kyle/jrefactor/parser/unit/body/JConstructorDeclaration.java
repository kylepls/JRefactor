package in.kyle.jrefactor.parser.unit.body;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.statement.JBlock;
import in.kyle.jrefactor.parser.unit.JParameterList;
import in.kyle.jrefactor.parser.unit.JThrowsList;
import in.kyle.jrefactor.parser.unit.JTypeName;
import in.kyle.jrefactor.parser.unit.Typeable;
import in.kyle.jrefactor.parser.unit.body.classtype.JClassMember;
import in.kyle.jrefactor.CodeWriter;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
public class JConstructorDeclaration extends Typeable implements JClassMember {
    
    private JTypeName identifier;
    @Delegate(excludes = JObject.class)
    private JParameterList parameterList = new JParameterList();
    @Delegate(excludes = JObject.class)
    private JThrowsList throwsList = new JThrowsList();
    private JBlock body = new JBlock();
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(getModifiers());
        writer.append(getTypeParameterList());
        writer.append("{}({}){} {}", identifier, parameterList, throwsList, body);
    }
    
}
