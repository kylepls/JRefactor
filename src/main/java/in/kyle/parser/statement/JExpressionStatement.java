package in.kyle.parser.statement;

import in.kyle.parser.expression.JExpression;
import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JExpressionStatement implements JStatement {
    
    private JExpression expression;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{};", expression);
    }
    
}
