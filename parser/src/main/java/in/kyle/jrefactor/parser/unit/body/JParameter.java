package in.kyle.jrefactor.parser.unit.body;

import in.kyle.jrefactor.parser.unit.JAnnotatable;
import in.kyle.jrefactor.parser.unit.JAnnotationList;
import in.kyle.jrefactor.parser.unit.JIdentifier;
import in.kyle.jrefactor.parser.unit.JModifiable;
import in.kyle.jrefactor.parser.unit.JModifierList;
import in.kyle.jrefactor.parser.unit.JTypeName;
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
