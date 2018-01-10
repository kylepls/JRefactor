package in.kyle.jrefactor.parser.statement.control;

import java.util.Optional;

import in.kyle.jrefactor.parser.unit.JIdentifier;
import lombok.Data;

@Data
public class JContinueStatement implements JControlStatement {
    
    private Optional<JIdentifier> identifier;
    
}
