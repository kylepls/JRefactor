package in.kyle.jrefactor.parser.unit.body;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.unit.JIdentifier;
import in.kyle.jrefactor.parser.unit.JParameterList;
import in.kyle.jrefactor.parser.unit.JThrowsList;
import in.kyle.jrefactor.parser.unit.JTypeName;
import in.kyle.jrefactor.parser.unit.Typeable;
import in.kyle.jrefactor.CodeWriter;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
public class JMethodHeader extends Typeable implements JObject {
    
    private JIdentifier name;
    private JTypeName resultType;
    @Delegate(excludes = JObject.class)
    private JParameterList parameterList = new JParameterList();
    @Delegate(excludes = JObject.class)
    private JThrowsList throwsList = new JThrowsList();
    
    public JMethodHeader(JIdentifier name) {
        this.name = name;
    }
    
    @Override
    public void write(CodeWriter writer) {
        super.write(writer);
        writer.append(getTypeParameterList());
        writer.append(resultType).append(" ");
        writer.append(name);
        parameterList.write(writer);
        throwsList.write(writer);
    }
}
