package in.kyle.parser.unit;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JIdentifier implements JObject {
    
    private String name;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(name);
    }
}
