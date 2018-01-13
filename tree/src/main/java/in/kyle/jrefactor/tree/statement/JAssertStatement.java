package in.kyle.jrefactor.tree.statement;

import java.util.Optional;

import in.kyle.jrefactor.tree.expression.JExpression;
import lombok.Data;

@Data
public class JAssertStatement implements JStatement {
    
    private JExpression assertion;
    private Optional<JExpression> message;
    
}
