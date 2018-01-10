package in.kyle.jrefactor.parser.expression.lambda;

import in.kyle.jrefactor.parser.expression.JExpression;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JLambdaExpression implements JExpression {
    
    private JLambdaParameters parameters;
    private JLambdaBody body;
    
}
