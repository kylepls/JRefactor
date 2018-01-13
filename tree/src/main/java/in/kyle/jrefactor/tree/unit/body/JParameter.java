package in.kyle.jrefactor.tree.unit.body;

import in.kyle.jrefactor.tree.unit.JAnnotatable;
import in.kyle.jrefactor.tree.unit.JAnnotationList;
import in.kyle.jrefactor.tree.unit.JIdentifier;
import in.kyle.jrefactor.tree.unit.JModifiable;
import in.kyle.jrefactor.tree.unit.JModifierList;
import in.kyle.jrefactor.tree.unit.JTypeName;
import lombok.Data;

@Data
public class JParameter implements JModifiable, JAnnotatable {
    
    private JTypeName type;
    private JIdentifier identifier;
    private JModifierList modifiers = new JModifierList();
    private JAnnotationList annotations = new JAnnotationList();
    
    public JParameter(JTypeName type, JIdentifier identifier) {
        this.type = type;
        this.identifier = identifier;
    }
}
