package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObject;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
public abstract class Typeable extends Modifiable {
    
    @Delegate(excludes = JObject.class)
    private final JTypeParameterList typeParameterList = new JTypeParameterList();
    
}
