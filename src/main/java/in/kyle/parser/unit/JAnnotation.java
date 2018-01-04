package in.kyle.parser.unit;

import in.kyle.parser.unit.body.annotationtype.JElementValue;
import in.kyle.writer.CodeWriter;
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
