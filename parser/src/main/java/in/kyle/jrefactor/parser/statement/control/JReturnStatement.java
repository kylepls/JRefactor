package in.kyle.jrefactor.parser.statement.control;

import java.util.Optional;

import in.kyle.jrefactor.parser.expression.JExpression;
import lombok.Data;

@Data
public class JReturnStatement implements JControlStatement {
    
    private Optional<JExpression> expression = Optional.empty();
    
}
