package in.kyle.jrefactor.parser.expression.lambda;

import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.expression.JExpression;
import in.kyle.jrefactor.parser.statement.JBlock;
import in.kyle.jrefactor.parser.unit.JParameterList;
import lombok.Data;

@Data
public class JLambdaExpression implements JExpression {
    
    private JLambdaParameters parameters = new JParameterList();
    private JLambdaBody body = new JBlock();
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{} -> {}", parameters, body);
    }
}
