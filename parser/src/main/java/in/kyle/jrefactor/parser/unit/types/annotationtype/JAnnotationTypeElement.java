package in.kyle.jrefactor.parser.unit.types.annotationtype;

import in.kyle.jrefactor.parser.unit.JAnnotatable;
import in.kyle.jrefactor.parser.unit.JAnnotationList;
import in.kyle.jrefactor.parser.unit.JIdentifier;
import in.kyle.jrefactor.parser.unit.JModifiable;
import in.kyle.jrefactor.parser.unit.JModifierList;
import in.kyle.jrefactor.parser.unit.JTypeName;
import lombok.Data;

@Data
public class JAnnotationTypeElement implements JModifiable, JAnnotatable {
    
    private JAnnotationList annotations = new JAnnotationList();
    private JModifierList modifiers = new JModifierList();
    private JTypeName type;
    private JIdentifier identifier;
    private JElementValue defaultValue;
    
}
