package in.kyle.jrefactor.parser.statement.control;

import in.kyle.jrefactor.parser.expression.JExpression;
import in.kyle.jrefactor.CodeWriter;
import lombok.Data;

@Data
public class JThrowStatement implements JControlStatement {
    
    private JExpression expression;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("throw {}", expression);
    }
}
