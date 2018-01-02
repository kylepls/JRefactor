package in.kyle.parser.statement;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.expression.JExpression;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
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
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(assertion, message);
    }
}
