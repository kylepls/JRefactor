package in.kyle.parser.expression;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
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
    
    @Override
    public List<JObject> getChildren() {
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
