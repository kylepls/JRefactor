package in.kyle.jrefactor.tree.statement;

import in.kyle.jrefactor.tree.expression.JExpression;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JExpressionStatement implements JStatement {
    
    private JExpression expression;
    
}
