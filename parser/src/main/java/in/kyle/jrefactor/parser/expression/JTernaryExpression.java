package in.kyle.jrefactor.parser.expression;

import in.kyle.jrefactor.CodeWriter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JTernaryExpression implements JExpression {
    
    private JExpression condition;
    private JExpression left;
    private JExpression right;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{} ? {} : {}", condition, left, right);
    }
}
