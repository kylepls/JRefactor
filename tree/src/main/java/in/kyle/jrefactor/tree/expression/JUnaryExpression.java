package in.kyle.jrefactor.tree.expression;

import in.kyle.jrefactor.tree.JObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class JUnaryExpression implements JExpression {
    
    private Operator operator;
    private JExpression expression;
    
    @AllArgsConstructor
    public enum Operator implements JObject {
        PRE_INCREMENT("++", true),
        POST_INCREMENT("++", false),
        PRE_DECREMENT("--", true),
        POST_DECREMENT("--", false);
        
        @Getter
        private final String javaString;
        @Getter
        private final boolean beforeOperator;
        
        public static Operator fromJava(String string) {
            return valueOf(string.toUpperCase());
        }
    }
}
