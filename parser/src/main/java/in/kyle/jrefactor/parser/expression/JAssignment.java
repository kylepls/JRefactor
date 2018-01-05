package in.kyle.jrefactor.parser.expression;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class JAssignment implements JExpression {
    
    private Operator operator;
    private JExpression left;
    private JExpression right;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{} {} {}", left, operator, right);
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
            for (Operator operator : values()) {
                if (operator.getJavaString().equals(string)) {
                    return operator;
                }
            }
            throw new IllegalArgumentException(
                    "No enum constant for " + Operator.class + "." + string);
        }
        
    }
}
