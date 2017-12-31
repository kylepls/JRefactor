package in.kyle.parser.expression;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.RewriteableField;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class JLeftRightExpression implements JExpression {
    
    private final RewriteableField<Operation> operation = new RewriteableField<>();
    private final RewriteableField<JExpression> left = new RewriteableField<>();
    private final RewriteableField<JExpression> right = new RewriteableField<>();
    
    public JLeftRightExpression(Operation operation, JExpression left, JExpression right) {
        this.operation.setValue(operation);
        this.left.setValue(left);
        this.right.setValue(right);
    }
    
    public Operation getOperation() {
        return operation.getValue();
    }
    
    public void setOperation(Operation operation) {
        this.operation.setValue(operation);
    }
    
    public JExpression getLeft() {
        return left.getValue();
    }
    
    public void setLeft(JExpression expression) {
        this.left.setValue(expression);
    }
    
    public JExpression getRight() {
        return right.getValue();
    }
    
    public void setRight(JExpression expression) {
        this.right.setValue(expression);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{} {} {}", left, operation, right);
    }
    
    @Override
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(left, right);
    }
    
    @AllArgsConstructor
    public enum Operation implements JObject {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("*"),
        DIVIDE("/"),
        MODULUS("%"),
        CONDITIONAL_AND("&&"),
        CONDITIONAL_OR("||"),
        CONDITIONAL_LESS_THAN("<"),
        CONDITIONAL_GREATER_THAN(">"),
        CONDITIONAL_LESS_THAN_EQUAL("<="),
        CONDITIONAL_GREATER_EQUAL(">="),
        INSTANCE_OF("instanceof"),
        EQUAL("=="),
        NOT_EQUAL("!="),
        BINARY_SHIFT_LEFT("<<"),
        BINARY_SHIFT_RIGHT(">>"),
        BINARY_ALLIGN_RIGHT(">>>"),
        BINARY_INCLUSIVE_OR("|"),
        BINARY_EXCLUSIVE_OR("^"),
        BINARY_AND("&");
        
        @Getter
        private final String javaString;
        
        @Override
        public void write(CodeWriter writer) {
            writer.append(javaString);
        }
    }
}
