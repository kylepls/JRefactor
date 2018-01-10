package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JTypeName implements JObject {
    
    public static JTypeName VOID = new JTypeName("void");
    
    private String name;
    
    public static JTypeName fromClass(Class<?> clazz) {
        return new JTypeName(clazz.getName().replace("$", "."));
    }
    
}
