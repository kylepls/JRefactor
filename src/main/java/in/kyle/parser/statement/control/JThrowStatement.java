package in.kyle.parser.statement.control;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.expression.JExpression;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JThrowStatement implements JControlStatement {
    
    private JExpression expression;
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(expression);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("throw {}", expression);
    }
}
