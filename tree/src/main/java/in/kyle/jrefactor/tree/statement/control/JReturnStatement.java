package in.kyle.jrefactor.tree.statement.control;

import java.util.Optional;

import in.kyle.jrefactor.tree.expression.JExpression;
import lombok.Data;

@Data
public class JReturnStatement implements JControlStatement {
    
    private Optional<JExpression> expression = Optional.empty();
    
}
