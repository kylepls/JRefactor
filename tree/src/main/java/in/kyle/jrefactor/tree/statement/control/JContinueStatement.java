package in.kyle.jrefactor.tree.statement.control;

import java.util.Optional;

import in.kyle.jrefactor.tree.unit.JIdentifier;
import lombok.Data;

@Data
public class JContinueStatement implements JControlStatement {
    
    private Optional<JIdentifier> identifier;
    
}
