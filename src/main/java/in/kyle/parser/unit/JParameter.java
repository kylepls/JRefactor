package in.kyle.parser.unit;

import java.util.Collections;
import java.util.List;

import in.kyle.parser.RewriteableField;
import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JParameter extends JModifiable {
    
    private String name;
    private final RewriteableField<JTypeName> type = new RewriteableField<>();
    
    public void setType(JTypeName type) {
        this.type.setValue(type);
    }
    
    public JTypeName getType() {
        return type.getValue();
    }
    
    @Override
    public void write(CodeWriter writer) {
        writeAnnotations(writer);
        writeModifiers(writer);
        writer.append("{} {}", name, type);
    }
    
    @Override
    public List<RewriteableField> getChildren() {
        return Collections.singletonList(type);
    }
}
