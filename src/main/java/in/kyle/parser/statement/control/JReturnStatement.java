package in.kyle.parser.statement.control;

import in.kyle.parser.expression.JExpression;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JReturnStatement implements JControlStatement {
    
    private JExpression expression;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("return");
        if (expression != null) {
            writer.append(" {}", expression);
        }
    }
}
