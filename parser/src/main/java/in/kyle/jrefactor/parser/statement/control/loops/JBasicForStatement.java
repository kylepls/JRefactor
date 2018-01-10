package in.kyle.jrefactor.parser.statement.control.loops;

import java.util.Optional;

import in.kyle.jrefactor.parser.expression.JExpression;
import in.kyle.jrefactor.parser.statement.JStatement;
import lombok.Data;

@Data
public class JBasicForStatement implements JLoopStatement {
    
    private Optional<JExpression> init;
    private Optional<JExpression> condition;
    private Optional<JExpression> update;
    private JStatement statement;
    
}
