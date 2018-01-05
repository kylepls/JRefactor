package in.kyle.jrefactor.parser.statement;

import in.kyle.jrefactor.parser.expression.JExpression;
import in.kyle.jrefactor.CodeWriter;
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
