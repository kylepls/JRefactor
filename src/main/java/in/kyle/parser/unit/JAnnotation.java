package in.kyle.parser.unit;

import java.util.Collections;
import java.util.List;

import in.kyle.parser.JObject;
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
    
    @Override
    public List<JObject> getChildren() {
        return Collections.singletonList(type);
    }
}
