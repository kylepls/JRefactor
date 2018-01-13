package in.kyle.jrefactor.tree.unit.body;

import java.util.Optional;

import in.kyle.jrefactor.tree.JObject;
import in.kyle.jrefactor.tree.unit.JIdentifier;
import lombok.Data;

@Data
public class JVariable implements JObject {
    
    private JIdentifier identifier;
    private Optional<JVariableInitializer> initializer = Optional.empty();
    
}
