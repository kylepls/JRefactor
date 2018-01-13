package in.kyle.jrefactor.tree.expression;

import in.kyle.jrefactor.tree.unit.JIdentifier;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JExpressionName implements JExpression {
    
    private JIdentifier identifier;
    
}
