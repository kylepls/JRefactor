package in.kyle.parser.unit;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import in.kyle.parser.RewriteableField;
import in.kyle.parser.block.JBlock;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JMethod extends JTypeable implements JClassMember {
    
    private String name;
    private final RewriteableField<JTypeName> resultType = new RewriteableField<>();
    private final Set<RewriteableField<JParameter>> parameters = new LinkedHashSet<>();
    private final Set<RewriteableField<JTypeName>> throwsTypes = new LinkedHashSet<>();
    private final RewriteableField<JBlock> body = new RewriteableField<>();
    
    public JMethod(String name) {
        this.name = name;
    }
    
    public void setResultType(JTypeName typeName) {
        this.resultType.setValue(typeName);
    }
    
    public JTypeName getResultType() {
        return resultType.getValue();
    }
    
    public boolean addParameter(JParameter parameter) {
        return CollectionUtils.addValue(parameters, parameter);
    }
    
    public boolean removeParameter(JParameter parameter) {
        return CollectionUtils.removeValue(parameters, parameter);
    }
    
    public void setParameters(Set<JParameter> parameters) {
        CollectionUtils.overwrite(this.parameters, parameters);
    }
    
    public Set<JParameter> getParameters() {
        return CollectionUtils.convertToJObjectSet(parameters);
    }
    
    public boolean addThrowsType(JTypeName typeName) {
        return CollectionUtils.addValue(throwsTypes, typeName);
    }
    
    public boolean removeThrowsType(JTypeName typeName) {
        return CollectionUtils.removeValue(throwsTypes, typeName);
    }
    
    public void setThrowsTypes(Set<JTypeName> set) {
        CollectionUtils.overwrite(throwsTypes, set);
    }
    
    public Set<JTypeName> getThrowsTypes() {
        return CollectionUtils.convertToJObjectSet(throwsTypes);
    }
    
    public void setBody(JBlock body) {
        this.body.setValue(body);
    }
    
    public JBlock getBody() {
        return body.getValue();
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
        for (Iterator<RewriteableField<JParameter>> iterator =
             parameters.iterator(); iterator.hasNext(); ) {
            JParameter parameter = iterator.next().getValue();
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
            for (Iterator<RewriteableField<JTypeName>> iterator =
                 throwsTypes.iterator(); iterator.hasNext(); ) {
                RewriteableField<JTypeName> throwsType = iterator.next();
                writer.append(throwsType.getValue());
                if (iterator.hasNext()) {
                    writer.append(", ");
                }
            }
        }
    }
}
