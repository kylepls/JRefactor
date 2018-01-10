package in.kyle.jrefactor.parser.statement;

import java.util.Optional;

import in.kyle.jrefactor.parser.expression.JExpression;
import lombok.Data;

@Data
public class JAssertStatement implements JStatement {
    
    private JExpression assertion;
    private Optional<JExpression> message;
    
}
