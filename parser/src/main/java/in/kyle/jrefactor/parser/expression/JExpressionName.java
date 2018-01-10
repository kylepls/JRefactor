package in.kyle.jrefactor.parser.expression;

import in.kyle.jrefactor.parser.unit.JIdentifier;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JExpressionName implements JExpression {
    
    private JIdentifier identifier;
    
}
