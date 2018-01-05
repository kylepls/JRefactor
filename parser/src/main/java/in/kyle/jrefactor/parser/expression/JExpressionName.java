package in.kyle.jrefactor.parser.expression;

import in.kyle.jrefactor.parser.unit.JIdentifier;
import in.kyle.jrefactor.CodeWriter;
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
