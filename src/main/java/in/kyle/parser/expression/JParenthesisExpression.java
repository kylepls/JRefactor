package in.kyle.parser.expression;

import java.util.List;

import in.kyle.parser.RewriteableField;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
import lombok.ToString;

@ToString
public class JParenthesisExpression implements JExpression {
    
    private final RewriteableField<JExpression> value = new RewriteableField<>();
    
    public JParenthesisExpression(JExpression value) {
        this.value.setValue(value);
    }
    
    public void setValue(JExpression expression) {
        this.value.setValue(expression);
    }
    
    public JExpression getValue() {
        return value.getValue();
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("({})", value);
    }
    
    @Override
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(value);
    }
}
