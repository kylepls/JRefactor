package in.kyle.parser.unit;

import in.kyle.parser.JObject;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
public abstract class Typeable extends Modifiable {
    
    @Delegate(excludes = JObject.class)
    private final JTypeParameterList typeParameterList = new JTypeParameterList();
    
}
