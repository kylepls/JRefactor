package in.kyle.parser.unit;

import java.util.Collections;
import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JPackage implements JObject {
    
    private String name;
    
    @Override
    public List<JObject> getChildren() {
        return Collections.emptyList();
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.appendLine("package {};", name);
    }
}
