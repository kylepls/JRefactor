package in.kyle.jrefactor.tree.unit;

import lombok.Data;

@Data
public abstract class Typeable implements JModifiable, JAnnotatable {
    
    private JModifierList modifiers = new JModifierList();
    private JAnnotationList annotations = new JAnnotationList();
    private JTypeParameterList typeParameters = new JTypeParameterList();
    
}
