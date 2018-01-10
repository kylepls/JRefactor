package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.unit.types.annotationtype.JElementValue;
import lombok.Data;

@Data
public class JAnnotation implements JElementValue {
    
    private JTypeName type;
    private JAnnotationValue value;
    
}
