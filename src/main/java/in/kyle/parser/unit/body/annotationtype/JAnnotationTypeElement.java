package in.kyle.parser.unit.body.annotationtype;

import in.kyle.parser.unit.JIdentifier;
import in.kyle.parser.unit.JTypeName;
import in.kyle.parser.unit.Modifiable;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JAnnotationTypeElement extends Modifiable implements JAnnotationMember {
    
    private JTypeName type;
    private JIdentifier identifier;
    private JElementValue defaultValue;
    
    @Override
    public void write(CodeWriter writer) {
        writeAnnotations(writer);
        writer.append(writer);
        writer.append("{} {}()", type, identifier);
        writer.append(" {}", defaultValue);
    }
    
}
