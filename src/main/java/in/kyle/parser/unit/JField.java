package in.kyle.parser.unit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.parser.RewriteableField;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JField extends JModifiable implements JClassMember, JAnnotatable, JObject {
    
    private final Set<RewriteableField<JAnnotation>> annotations = new LinkedHashSet<>();
    private final Set<RewriteableField<JVariable>> variables = new LinkedHashSet<>();
    private final RewriteableField<JTypeName> type = new RewriteableField<>();
    
    public void setType(JTypeName type) {
        this.type.setValue(type);
    }
    
    public JTypeName getType() {
        return type.getValue();
    }
    
    public boolean addVariable(JVariable variable) {
        return variables.add(new RewriteableField<>(variable));
    }
    
    public boolean removeVariable(JVariable variable) {
        return CollectionUtils.removeValue(variables, variable);
    }
    
    @Override
    public boolean addAnnotation(JAnnotation annotation) {
        return annotations.add(new RewriteableField<>(annotation));
    }
    
    @Override
    public boolean removeAnnotation(JAnnotation annotation) {
        return CollectionUtils.removeValue(annotations, annotation);
    }
    
    @Override
    public void setAnnotations(Set<JAnnotation> set) {
        annotations.clear();
        annotations.addAll(CollectionUtils.convertToRewriteableSet(set));
    }
    
    @Override
    public Set<JAnnotation> getAnnotations() {
        return CollectionUtils.convertToJObjectSet(annotations);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writeModifiers(writer);
        writer.append(type).append(" ");
        writeVariables(writer);
        writer.append(";");
    }
    
    private void writeVariables(CodeWriter writer) {
        for (Iterator<RewriteableField<JVariable>> iterator =
             variables.iterator(); iterator.hasNext(); ) {
            JVariable variable = iterator.next().getValue();
            writer.append(variable);
            if (iterator.hasNext()) {
                writer.append(", ");
            }
        }
    }
    
    @Override
    public List<RewriteableField> getChildren() {
        List<RewriteableField> list = new ArrayList<>(annotations);
        list.add(type);
        list.addAll(variables);
        return list;
    }
}
