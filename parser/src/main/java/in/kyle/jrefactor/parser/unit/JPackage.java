package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JPackage implements JObject {
    
    private String name;
    
}
