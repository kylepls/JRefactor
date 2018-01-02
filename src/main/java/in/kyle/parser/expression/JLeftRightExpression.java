package in.kyle.parser.expression;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class JLeftRightExpression implements JExpression {
    
    private Operation operation;
    private JExpression left;
    private JExpression right;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{} {} {}", left, operation, right);
    }
    
    @Override
    public List<JObject> getChildren() {
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
