package in.kyle.parser.unit.body;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.statement.JBlock;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.parser.unit.JParameterList;
import in.kyle.parser.unit.JThrowsList;
import in.kyle.parser.unit.JTypeName;
import in.kyle.parser.unit.Typeable;
import in.kyle.parser.unit.body.classtype.JClassMember;
import in.kyle.writer.CodeWriter;
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
        writeModifiers(writer);
        writeTypeParameters(writer);
        writer.append("{}({}){} {}", identifier, parameterList, throwsList, body);
    }
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(super.getChildren(), identifier, throwsList, body);
    }
}
