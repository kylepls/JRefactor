package in.kyle.parser.expression;

import java.util.List;

import in.kyle.parser.RewriteableField;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JConditionalExpression implements JExpression {
    
    private final RewriteableField<JExpression> condition = new RewriteableField<>();
    private final RewriteableField<JExpression> left = new RewriteableField<>();
    private final RewriteableField<JExpression> right = new RewriteableField<>();
    
    public JConditionalExpression(JExpression condition, JExpression left, JExpression right) {
        this.condition.setValue(condition);
        this.left.setValue(left);
        this.right.setValue(right);
    }
    
    public JExpression getCondition() {
        return condition.getValue();
    }
    
    public void setCondition(JExpression expression) {
        this.condition.setValue(expression);
    }
    
    public JExpression getLeft() {
        return left.getValue();
    }
    
    public void setLeft(JExpression left) {
        this.left.setValue(left);
    }
    
    public JExpression getRight() {
        return right.getValue();
    }
    
    public void setRight(JExpression expression) {
        this.right.setValue(expression);
    }
    
    @Override
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(condition, left, right);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{} ? {} : {}", condition.getValue(), left.getValue(), right.getValue());
    }
}
