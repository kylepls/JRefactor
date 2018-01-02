package in.kyle.parser.expression;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JParenthesisExpression implements JExpression {
    
    private JExpression value;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("({})", value);
    }
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(value);
    }
}
