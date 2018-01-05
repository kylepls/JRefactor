package in.kyle.jrefactor.parser.unit.body.annotationtype;

import in.kyle.jrefactor.parser.unit.JIdentifier;
import in.kyle.jrefactor.parser.unit.JTypeName;
import in.kyle.jrefactor.parser.unit.Modifiable;
import in.kyle.jrefactor.CodeWriter;
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
