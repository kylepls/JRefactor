package in.kyle.parser.unit.body;

import in.kyle.parser.unit.JTypeName;
import in.kyle.parser.unit.Modifiable;
import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JParameter extends Modifiable {
    
    private String name;
    private JTypeName type;
    
    @Override
    public void write(CodeWriter writer) {
        writeAnnotations(writer);
        writer.append(getModifiers());
        writer.append("{} {}", name, type);
    }
    
}
