package in.kyle.parser.statement;

import in.kyle.parser.RewriteableField;
import in.kyle.parser.expression.JExpression;
import in.kyle.writer.CodeWriter;

public class JExpressionStatement implements JBlockStatement {
    
    private final RewriteableField<JExpression> expression = new RewriteableField<>();
    
    public JExpressionStatement(JExpression expression) {
        this.expression.setValue(expression);
    }
    
    public void setExpression(JExpression expression) {
        this.expression.setValue(expression);
    }
    
    public JExpression getExpression() {
        return expression.getValue();
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{};", expression);
    }
}
