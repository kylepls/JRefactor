package in.kyle.parser.unit;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.parser.statement.JBlock;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JMethod extends Typeable implements JClassMember {
    
    private String name;
    private JTypeName resultType;
    private Set<JParameter> parameters = new LinkedHashSet<>();
    private Set<JTypeName> throwsTypes = new LinkedHashSet<>();
    private JBlock body;
    
    public JMethod(String name) {
        this.name = name;
    }
    
    public boolean addParameter(JParameter parameter) {
        return parameters.add(parameter);
    }
    
    public boolean removeParameter(JParameter parameter) {
        return parameters.remove(parameter);
    }
    
    public boolean addThrowsType(JTypeName typeName) {
        return throwsTypes.add(typeName);
    }
    
    public boolean removeThrowsType(JTypeName typeName) {
        return throwsTypes.remove(typeName);
    }
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(super.getChildren(),
                                          resultType,
                                          parameters,
                                          throwsTypes,
                                          body);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writeModifiers(writer);
        writeTypeParameters(writer);
        writer.append(resultType).append(" ");
        writer.append(name);
        writeParameters(writer);
        writeThrows(writer);
        writer.append(body);
    }
    
    void writeParameters(CodeWriter writer) {
        writer.append("(");
        for (Iterator<JParameter> iterator = parameters.iterator(); iterator.hasNext(); ) {
            JParameter parameter = iterator.next();
            writer.append(parameter);
            if (iterator.hasNext()) {
                writer.append(", ");
            }
        }
        writer.append(")");
    }
    
    void writeThrows(CodeWriter writer) {
        if (!throwsTypes.isEmpty()) {
            writer.append("throws ");
            for (Iterator<JTypeName> iterator = throwsTypes.iterator(); iterator.hasNext(); ) {
                JTypeName throwsType = iterator.next();
                writer.append(throwsType);
                if (iterator.hasNext()) {
                    writer.append(", ");
                }
            }
        }
    }
}
