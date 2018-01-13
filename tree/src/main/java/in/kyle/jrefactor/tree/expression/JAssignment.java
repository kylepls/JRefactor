package in.kyle.jrefactor.tree.expression;

import in.kyle.jrefactor.tree.JObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class JAssignment implements JExpression {
    
    private JAssignmentOperator operator;
    private JExpression left;
    private JExpression right;
    
    @AllArgsConstructor
    public enum JAssignmentOperator implements JObject {
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
        
        public static JAssignmentOperator fromJava(String string) {
            for (JAssignmentOperator operator : values()) {
                if (operator.getJavaString().equals(string)) {
                    return operator;
                }
            }
            throw new IllegalArgumentException(
                    "No enum constant for " + JAssignmentOperator.class + "." + string);
        }
        
    }
}
