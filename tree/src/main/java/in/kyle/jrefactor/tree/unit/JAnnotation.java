package in.kyle.jrefactor.tree.unit;

import in.kyle.jrefactor.tree.unit.types.annotationtype.JElementValue;
import lombok.Data;

@Data
public class JAnnotation implements JElementValue {
    
    private JTypeName type;
    private JAnnotationValue value;
    
}
