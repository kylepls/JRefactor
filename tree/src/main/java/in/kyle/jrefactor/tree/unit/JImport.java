package in.kyle.jrefactor.tree.unit;

import in.kyle.jrefactor.tree.JObject;
import lombok.Data;

@Data
public class JImport implements JObject {
    
    private String packageName = "";
    private String name;
    private boolean statik;
    private boolean onDemand;
    
}
