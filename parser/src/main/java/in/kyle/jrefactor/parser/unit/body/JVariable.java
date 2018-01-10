package in.kyle.jrefactor.parser.unit.body;

import java.util.Optional;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.unit.JIdentifier;
import lombok.Data;

@Data
public class JVariable implements JObject {
    
    private JIdentifier identifier;
    private Optional<JVariableInitializer> initializer = Optional.empty();
    
}
