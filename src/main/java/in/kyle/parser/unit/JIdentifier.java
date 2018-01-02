package in.kyle.parser.unit;

import java.util.Collections;
import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JIdentifier implements JObject {
    
    private String name;
    
    public JIdentifier(String name) {
        this.name = name;
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(name);
    }
    
    @Override
    public List<JObject> getChildren() {
        return Collections.emptyList();
    }
}
