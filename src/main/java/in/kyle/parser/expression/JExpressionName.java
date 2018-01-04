package in.kyle.parser.expression;

import in.kyle.parser.unit.JIdentifier;
import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JExpressionName implements JExpression {
    
    private JIdentifier identifier;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(identifier);
    }
    
}
