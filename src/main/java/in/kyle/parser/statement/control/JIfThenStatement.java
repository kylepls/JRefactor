package in.kyle.parser.statement.control;

import in.kyle.parser.expression.JExpression;
import in.kyle.parser.statement.JStatement;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JIfThenStatement implements JControlStatement {
    
    private JExpression expression;
    private JStatement statement;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("if ({}) {}", expression, statement);
    }
}
