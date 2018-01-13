package in.kyle.jrefactor.tree.expression.literal;

import in.kyle.jrefactor.tree.expression.JExpression;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class JLiteral<T> implements JExpression {
    
    private T value;
    
    public T getValue() {
        return value;
    }
    
}
