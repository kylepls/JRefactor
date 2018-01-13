package in.kyle.jrefactor.tree.statement.control;

import in.kyle.jrefactor.tree.expression.JExpression;
import lombok.Data;

@Data
public class JThrowStatement implements JControlStatement {
    
    private JExpression expression;
    
}
