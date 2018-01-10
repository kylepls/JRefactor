package in.kyle.jrefactor.parser.unit.types.classtype;

import in.kyle.jrefactor.parser.unit.JAnnotatable;
import in.kyle.jrefactor.parser.unit.JAnnotationList;
import in.kyle.jrefactor.parser.unit.body.VariableHolder;
import in.kyle.jrefactor.parser.unit.types.annotationtype.JAnnotationMember;
import lombok.Data;

@Data
public class JField extends VariableHolder implements JAnnotatable, JClassMember, JAnnotationMember {
    
    private JAnnotationList annotations = new JAnnotationList();
}
