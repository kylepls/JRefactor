package in.kyle.jrefactor.parser.unit.body;

import in.kyle.jrefactor.parser.unit.JTypeName;
import in.kyle.jrefactor.parser.unit.Modifiable;
import in.kyle.jrefactor.CodeWriter;
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
