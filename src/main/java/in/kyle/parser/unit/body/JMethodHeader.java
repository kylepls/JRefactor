package in.kyle.parser.unit.body;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.parser.unit.JIdentifier;
import in.kyle.parser.unit.JParameterList;
import in.kyle.parser.unit.JThrowsList;
import in.kyle.parser.unit.JTypeName;
import in.kyle.parser.unit.Typeable;
import in.kyle.writer.CodeWriter;
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
    public List<JObject> getChildren() {
        return CollectionUtils.createList(super.getChildren(),
                                          name,
                                          resultType,
                                          parameterList,
                                          throwsList);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writeModifiers(writer);
        writeTypeParameters(writer);
        writer.append(resultType).append(" ");
        writer.append(name);
        parameterList.write(writer);
        throwsList.write(writer);
    }
}
