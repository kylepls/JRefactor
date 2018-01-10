package in.kyle.jrefactor.parser.statement;

import in.kyle.jrefactor.parser.unit.JIdentifier;
import lombok.Data;

@Data
public class JLabledStatement implements JStatement {
    
    private JIdentifier identifier;
    private JStatement statement;
    
}
