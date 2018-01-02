package in.kyle.parser.expression.literal;

import java.util.Collections;
import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.expression.JExpression;
import in.kyle.writer.CodeWriter;
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
    
    @Override
    public List<JObject> getChildren() {
        return Collections.emptyList();
    }
}
