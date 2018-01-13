package in.kyle.jrefactor.tree.unit.types.classtype;

import in.kyle.jrefactor.tree.unit.JAnnotatable;
import in.kyle.jrefactor.tree.unit.JAnnotationList;
import in.kyle.jrefactor.tree.unit.body.VariableHolder;
import in.kyle.jrefactor.tree.unit.types.annotationtype.JAnnotationMember;
import lombok.Data;

@Data
public class JField extends VariableHolder implements JAnnotatable, JClassMember, JAnnotationMember {
    
    private JAnnotationList annotations = new JAnnotationList();
}
