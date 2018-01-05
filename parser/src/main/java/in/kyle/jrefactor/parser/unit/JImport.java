package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.CodeWriter;
import lombok.Data;

@Data
public class JImport implements JObject {
    
    private String packageName = "";
    private String name;
    private boolean statik;
    private boolean onDemand;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("import ");
        if (isStatik()) {
            writer.append("static ");
        }
        if (!packageName.isEmpty()) {
            writer.append(packageName).append(".");
        }
    
        writer.append(name).append(";");
    }
    
}
