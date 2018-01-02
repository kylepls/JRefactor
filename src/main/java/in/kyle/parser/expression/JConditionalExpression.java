package in.kyle.parser.expression;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JConditionalExpression implements JExpression {
    
    private JExpression condition;
    private JExpression left;
    private JExpression right;
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(condition, left, right);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{} ? {} : {}", condition, left, right);
    }
}
