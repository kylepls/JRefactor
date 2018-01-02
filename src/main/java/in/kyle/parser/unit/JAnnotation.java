package in.kyle.parser.unit;

import java.util.Collections;
import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JAnnotation implements JObject {
    
    private JTypeName type;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("@{}", type);
    }
    
    @Override
    public List<JObject> getChildren() {
        return Collections.singletonList(type);
    }
}
