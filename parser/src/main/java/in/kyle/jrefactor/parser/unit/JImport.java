package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObject;
import lombok.Data;

@Data
public class JImport implements JObject {
    
    private String packageName = "";
    private String name;
    private boolean statik;
    private boolean onDemand;
    
}
