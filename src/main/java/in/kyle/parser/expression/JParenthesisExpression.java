package in.kyle.parser.expression;

import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JParenthesisExpression implements JExpression {
    
    private JExpression value;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("({})", value);
    }
    
}
