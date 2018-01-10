package in.kyle.jrefactor.parser.statement.control;

import in.kyle.jrefactor.parser.expression.JExpression;
import in.kyle.jrefactor.parser.statement.JBlock;
import lombok.Data;

@Data
public class JSynchronizedStatement implements JControlStatement {
    
    private JExpression expression;
    private JBlock block;
    
}
