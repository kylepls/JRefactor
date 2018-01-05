package in.kyle.jrefactor.parser.unit.body.classtype;

import in.kyle.jrefactor.parser.unit.body.VariableHolder;
import in.kyle.jrefactor.parser.unit.body.annotationtype.JAnnotationMember;
import lombok.Data;

@Data
public class JField extends VariableHolder implements JClassMember, JAnnotationMember {
    
}
