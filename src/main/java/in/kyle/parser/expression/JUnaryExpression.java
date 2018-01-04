package in.kyle.parser.expression;

import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class JUnaryExpression implements JExpression {
    
    private Operator operator;
    private JExpression expression;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(operator.writerString, expression);
    }
    
    @AllArgsConstructor
    public enum Operator {
        PRE_INCREMENT("++", "++{}"),
        POST_INCREMENT("++", "{}++"),
        PRE_DECREMENT("--", "--{}"),
        POST_DECREMENT("--","{}--");
        
        @Getter
        private final String javaString;
        private final String writerString;
        
        public static Operator fromJava(String string) {
            return valueOf(string.toUpperCase());
        }
    }
}
