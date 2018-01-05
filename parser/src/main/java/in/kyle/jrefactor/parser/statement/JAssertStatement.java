package in.kyle.jrefactor.parser.statement;

import in.kyle.jrefactor.parser.expression.JExpression;
import in.kyle.jrefactor.CodeWriter;
import lombok.Data;

@Data
public class JAssertStatement implements JStatement {
    
    private JExpression assertion;
    private JExpression message;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("assert {}");
        if (message != null) {
            writer.append(" : {}", message);
        }
        writer.append(";");
    }
    
}
