package in.kyle.parser.expression;

import java.util.Collections;
import java.util.List;

import in.kyle.parser.RewriteableField;
import in.kyle.writer.CodeWriter;
import lombok.ToString;

@ToString
public class JParenthesisExpression extends JExpression {
    
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
        return Collections.singletonList(value);
    }
}
