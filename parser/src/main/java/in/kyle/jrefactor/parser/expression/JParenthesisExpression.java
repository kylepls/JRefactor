package in.kyle.jrefactor.parser.expression;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JParenthesisExpression implements JExpression {
    
    private JExpression value;
    
}
