package in.kyle.jrefactor.parser.expression;

import in.kyle.jrefactor.parser.unit.JTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JTypeReferenceExpression implements JExpression {
    
    private JTypeName reference;
    
}
