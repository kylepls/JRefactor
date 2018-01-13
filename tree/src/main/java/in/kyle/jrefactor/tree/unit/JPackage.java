package in.kyle.jrefactor.tree.unit;

import in.kyle.jrefactor.tree.JObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JPackage implements JObject {
    
    private String name;
    
}
