package in.kyle.parser.statement;

import java.util.Collections;
import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.expression.JExpression;
import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JExpressionStatement implements JStatement {
    
    private JExpression expression;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{};", expression);
    }
    
    @Override
    public List<JObject> getChildren() {
        return Collections.emptyList();
    }
}
