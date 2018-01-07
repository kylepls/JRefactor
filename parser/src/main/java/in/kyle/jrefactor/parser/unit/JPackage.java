package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JPackage implements JObject {
    
    private String name;
    
    @Override
    public void write(CodeWriter writer) {
        writer.appendLine("package {};", name);
    }
}