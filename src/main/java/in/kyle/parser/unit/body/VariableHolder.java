package in.kyle.parser.unit.body;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.parser.unit.JTypeName;
import in.kyle.parser.unit.Modifiable;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public abstract class VariableHolder extends Modifiable {
    
    private JTypeName type;
    private Set<JVariable> variables = new LinkedHashSet<>();
    
    public boolean addVariable(JVariable variable) {
        return variables.add(variable);
    }
    
    public boolean removeVariable(JVariable variable) {
        return variables.remove(variable);
    }
    
    @Override
    public List<JObject> getChildren() {
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
