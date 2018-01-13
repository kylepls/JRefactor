package in.kyle.jrefactor.tree.expression;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JParenthesisExpression implements JExpression {
    
    private JExpression value;
    
}
