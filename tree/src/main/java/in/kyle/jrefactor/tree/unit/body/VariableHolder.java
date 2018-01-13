package in.kyle.jrefactor.tree.unit.body;

import in.kyle.jrefactor.tree.JObjectList;
import in.kyle.jrefactor.tree.unit.JModifiable;
import in.kyle.jrefactor.tree.unit.JModifierList;
import in.kyle.jrefactor.tree.unit.JTypeName;
import lombok.Data;

@Data
public abstract class VariableHolder implements JModifiable {
    
    private JModifierList modifiers = new JModifierList();
    private JTypeName type;
    private JObjectList<JVariable> variables = new JObjectList<>();
    
}
