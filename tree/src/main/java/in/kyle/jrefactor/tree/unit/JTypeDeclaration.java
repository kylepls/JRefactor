package in.kyle.jrefactor.tree.unit;

import in.kyle.jrefactor.tree.unit.body.JTypeBody;
import in.kyle.jrefactor.tree.unit.types.classtype.JClassMember;
import lombok.Data;

@Data
public abstract class JTypeDeclaration<T extends JTypeBody>
        implements JClassMember, JModifiable, JAnnotatable {
    
    private JAnnotationList annotations = new JAnnotationList();
    private JModifierList modifiers = new JModifierList();
    
    private JIdentifier identifier;
    private T body;
    
}
