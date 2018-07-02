package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.unit.body.annotationtype.JElementValue;
import in.kyle.jrefactor.CodeWriter;
import lombok.Data;

@Data
public class JAnnotation implements JElementValue {
    
    private JTypeName type;
    private JAnnotationValue value;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("@{}{}", type, value);
    }
    
}
