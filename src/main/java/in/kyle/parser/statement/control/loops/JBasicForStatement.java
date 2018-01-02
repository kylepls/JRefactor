package in.kyle.parser.statement.control.loops;

import in.kyle.parser.expression.JExpression;
import in.kyle.writer.CodeWriter;

public class JBasicForStatement implements JLoopStatement {
    
    private JExpression init;
    private JExpression expression;
    private JExpression update;
    
    @Override
    public void write(CodeWriter writer) {
        
    }
}
