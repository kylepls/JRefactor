package in.kyle.parser.statement.control.loops;

import in.kyle.parser.expression.JExpression;
import in.kyle.parser.statement.JStatement;
import lombok.Data;

@Data
public abstract class WhileStatement implements JLoopStatement {
    
    private JExpression expression;
    private JStatement statement;
    
}
