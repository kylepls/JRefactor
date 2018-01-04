package in.kyle.parser.statement.control;

import in.kyle.parser.expression.JExpression;
import in.kyle.parser.statement.JStatement;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JIfThenElseStatement implements JControlStatement {
    
    private JExpression expression;
    private JStatement ifCondition;
    private JStatement elseCondition;
    
    @Override
    public void write(CodeWriter writer) {
        writer.appendLine("if ({}) {}", expression, ifCondition);
        writer.appendLine("else {}", elseCondition);
    }
}
