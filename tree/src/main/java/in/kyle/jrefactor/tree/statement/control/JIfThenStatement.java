package in.kyle.jrefactor.tree.statement.control;

import in.kyle.jrefactor.tree.expression.JExpression;
import in.kyle.jrefactor.tree.statement.JStatement;
import lombok.Data;

@Data
public class JIfThenStatement implements JControlStatement {
    
    private JExpression expression;
    private JStatement statement;
    
}
