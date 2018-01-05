package in.kyle.jrefactor.parser.expression.literal;

import in.kyle.jrefactor.parser.expression.JExpression;
import in.kyle.jrefactor.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class JLiteral<T> implements JExpression {
    
    private T value;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(value);
    }
    
    public T getValue() {
        return value;
    }
    
}
