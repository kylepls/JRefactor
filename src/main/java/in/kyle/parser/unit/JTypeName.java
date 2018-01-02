package in.kyle.parser.unit;

import java.util.Collections;
import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JTypeName implements JObject {
    
    private String name;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(name);
    }
    
    @Override
    public List<JObject> getChildren() {
        return Collections.emptyList();
    }
}
