package in.kyle.parser.expression;

import java.util.List;

import in.kyle.parser.RewriteableField;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class JUnaryExpression implements JExpression {
    
    private Operator operator;
    private RewriteableField<JExpression> expression = new RewriteableField<>();
    
    public void setExpression(JExpression expression) {
        this.expression.setValue(expression);
    }
    
    public JExpression getExpression() {
        return expression.getValue();
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(operator.writerString, expression);
    }
    
    @Override
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(expression);
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
