package in.kyle.parser.unit;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.RewriteableField;
import in.kyle.writer.CodeWriter;

public abstract class VariableHolder extends Modifiable {
    
    private final RewriteableField<JTypeName> type = new RewriteableField<>();
    private final Set<RewriteableField<JVariable>> variables = new LinkedHashSet<>();
    
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
    
    public void setVariables(Collection<JVariable> variables) {
        CollectionUtils.overwrite(this.variables, variables);
    }
    
    public Set<JVariable> getVariables() {
        return CollectionUtils.convertToJObjectSet(variables);
    }
    
    @Override
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(super.getChildren(), type, variables);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writeModifiers(writer);
        writer.append(getType()).append(" ");
        writeVariables(writer);
    }
    
    private void writeVariables(CodeWriter writer) {
        for (Iterator<JVariable> iterator = getVariables().iterator(); iterator.hasNext(); ) {
            JVariable variable = iterator.next();
            writer.append(variable);
            if (iterator.hasNext()) {
                writer.append(", ");
            }
        }
    }
}
