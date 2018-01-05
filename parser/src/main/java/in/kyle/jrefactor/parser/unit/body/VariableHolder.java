package in.kyle.jrefactor.parser.unit.body;

import java.util.Iterator;

import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.unit.JTypeName;
import in.kyle.jrefactor.parser.unit.Modifiable;
import lombok.Data;

@Data
public abstract class VariableHolder extends Modifiable {
    
    private JTypeName type;
    private JObjectList<JVariable> variables = new JObjectList<>();
    
    public boolean addVariable(JVariable variable) {
        return variables.add(variable);
    }
    
    public boolean removeVariable(JVariable variable) {
        return variables.remove(variable);
    }
    
    @Override
    public void write(CodeWriter writer) {
        super.write(writer);
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
