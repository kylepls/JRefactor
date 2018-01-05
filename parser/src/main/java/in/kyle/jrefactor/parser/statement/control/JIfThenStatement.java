package in.kyle.jrefactor.parser.statement.control;

import in.kyle.jrefactor.parser.expression.JExpression;
import in.kyle.jrefactor.parser.statement.JStatement;
import in.kyle.jrefactor.CodeWriter;
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
