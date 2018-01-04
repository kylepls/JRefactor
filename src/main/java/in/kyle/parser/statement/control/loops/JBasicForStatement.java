package in.kyle.parser.statement.control.loops;

import in.kyle.parser.expression.JExpression;
import in.kyle.parser.statement.JStatement;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JBasicForStatement implements JLoopStatement {
    
    private JExpression init;
    private JExpression expression;
    private JExpression update;
    private JStatement statement;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("for ({}; {}; {}) {}", init, expression, update, statement);
    }
}
