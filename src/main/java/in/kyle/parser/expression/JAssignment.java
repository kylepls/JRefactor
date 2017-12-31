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
public class JAssignment implements JExpression {
    
    private Operator operator;
    private final RewriteableField<JExpression> left = new RewriteableField<>();
    private final RewriteableField<JExpression> right = new RewriteableField<>();
    
    public void setLeft(JExpression expression) {
        this.left.setValue(expression);
    }
    
    public JExpression getLeft() {
        return left.getValue();
    }
    
    public void setRight(JExpression expression) {
        this.right.setValue(expression);
    }
    
    public JExpression getRight() {
        return right.getValue();
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{} {} {}", left, operator, right);
    }
    
    @Override
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(left, operator, right);
    }
    
    @AllArgsConstructor
    public enum Operator implements JObject {
        EQUAL("="),
        MULTIPLY("*="),
        DIVIDE("/="),
        MODULUS("%="),
        ADD("+="),
        SUBTRACT("-="),
        SHIFT_LEFT("<<="),
        SHIFT_RIGHT(">>="),
        ALLIGN_RIGHT(">>>="),
        BINARY_AND("&="),
        BINARY_EXCLUSIVE_OR("^="),
        BINARCY_INCLUSIVE_OR("|=");
        
        @Getter
        private final String javaString;
    
        @Override
        public void write(CodeWriter writer) {
            writer.append(javaString);
        }
        
        public static Operator fromJava(String string) {
            return valueOf(string.toUpperCase());
        }
    }
}
