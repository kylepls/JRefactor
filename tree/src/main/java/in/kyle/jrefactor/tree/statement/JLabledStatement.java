package in.kyle.jrefactor.tree.statement;

import in.kyle.jrefactor.tree.unit.JIdentifier;
import lombok.Data;

@Data
public class JLabledStatement implements JStatement {
    
    private JIdentifier identifier;
    private JStatement statement;
    
}
