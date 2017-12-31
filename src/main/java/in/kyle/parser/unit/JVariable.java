package in.kyle.parser.unit;

import java.util.Collections;
import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.RewriteableField;
import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class JVariable implements JObject {
    
    private String name;
    private RewriteableField<JVariableInitializer> initializer = new RewriteableField<>();
    
    public JVariableInitializer getInitializer() {
        return initializer.getValue();
    }
    
    public void setInitializer(JVariableInitializer initializer) {
        this.initializer.setValue(initializer);
    }
    
    @Override
    public void write(CodeWriter writer) {
        if (initializer != null) {
            writer.append("{} = {}", name, initializer);
        } else {
            writer.append(name);
        }
    }
    
    @Override
    public List<RewriteableField> getChildren() {
        return Collections.singletonList(initializer);
    }
}
