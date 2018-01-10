package in.kyle.jrefactor.parser.statement.control;

import in.kyle.jrefactor.parser.expression.JExpression;
import lombok.Data;

@Data
public class JThrowStatement implements JControlStatement {
    
    private JExpression expression;
    
}
