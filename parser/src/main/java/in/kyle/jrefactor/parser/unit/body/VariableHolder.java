package in.kyle.jrefactor.parser.unit.body;

import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.unit.JModifiable;
import in.kyle.jrefactor.parser.unit.JModifierList;
import in.kyle.jrefactor.parser.unit.JTypeName;
import lombok.Data;

@Data
public abstract class VariableHolder implements JModifiable {
    
    private JModifierList modifiers = new JModifierList();
    private JTypeName type;
    private JObjectList<JVariable> variables = new JObjectList<>();
    
}
