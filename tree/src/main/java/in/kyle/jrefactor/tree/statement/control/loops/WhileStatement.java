package in.kyle.jrefactor.tree.statement.control.loops;

import in.kyle.jrefactor.tree.expression.JExpression;
import in.kyle.jrefactor.tree.statement.JStatement;
import lombok.Data;

@Data
public abstract class WhileStatement implements JLoopStatement {
    
    private JExpression expression;
    private JStatement statement;
    
}
