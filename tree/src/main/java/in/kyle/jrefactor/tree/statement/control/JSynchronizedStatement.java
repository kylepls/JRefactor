package in.kyle.jrefactor.tree.statement.control;

import in.kyle.jrefactor.tree.expression.JExpression;
import in.kyle.jrefactor.tree.statement.JBlock;
import lombok.Data;

@Data
public class JSynchronizedStatement implements JControlStatement {
    
    private JExpression expression;
    private JBlock block;
    
}
