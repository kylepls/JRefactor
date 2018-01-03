package in.kyle.parser.unit.body.annotationtype;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.CollectionUtils;
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
        writeModifiers(writer);
        writer.append("{} {}()", type, identifier);
        writer.append(" {}", defaultValue);
    }
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(super.getChildren(), type, identifier, defaultValue);
    }
}
