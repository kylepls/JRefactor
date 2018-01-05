package in.kyle.jrefactor.parser.statement.control.loops;

import in.kyle.jrefactor.parser.expression.JExpression;
import in.kyle.jrefactor.parser.statement.JStatement;
import lombok.Data;

@Data
public abstract class WhileStatement implements JLoopStatement {
    
    private JExpression expression;
    private JStatement statement;
    
}
