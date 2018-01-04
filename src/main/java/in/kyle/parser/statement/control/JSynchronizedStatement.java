package in.kyle.parser.statement.control;

import in.kyle.parser.expression.JExpression;
import in.kyle.parser.statement.JBlock;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JSynchronizedStatement implements JControlStatement {
    
    private JExpression expression;
    private JBlock block;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("synchronized ({}) {}", expression, block);
    }
}
