package in.kyle.jrefactor.tree.expression.lambda;

import in.kyle.jrefactor.tree.expression.JExpression;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JLambdaExpression implements JExpression {
    
    private JLambdaParameters parameters;
    private JLambdaBody body;
    
}
