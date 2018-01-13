package in.kyle.jrefactor.tree.expression;

import in.kyle.jrefactor.tree.unit.JTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JTypeReferenceExpression implements JExpression {
    
    private JTypeName reference;
    
}
