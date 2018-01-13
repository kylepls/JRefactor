package in.kyle.jrefactor.tree.expression;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JTernaryExpression implements JExpression {
    
    private JExpression condition;
    private JExpression left;
    private JExpression right;
    
}
