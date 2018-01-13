package in.kyle.jrefactor.tree.unit.types.annotationtype;

import in.kyle.jrefactor.tree.unit.JAnnotatable;
import in.kyle.jrefactor.tree.unit.JAnnotationList;
import in.kyle.jrefactor.tree.unit.JIdentifier;
import in.kyle.jrefactor.tree.unit.JModifiable;
import in.kyle.jrefactor.tree.unit.JModifierList;
import in.kyle.jrefactor.tree.unit.JTypeName;
import lombok.Data;

@Data
public class JAnnotationTypeElement implements JModifiable, JAnnotatable {
    
    private JAnnotationList annotations = new JAnnotationList();
    private JModifierList modifiers = new JModifierList();
    private JTypeName type;
    private JIdentifier identifier;
    private JElementValue defaultValue;
    
}
