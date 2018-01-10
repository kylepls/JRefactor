package in.kyle.jrefactor.parser.statement.control;

import in.kyle.jrefactor.parser.expression.JExpression;
import in.kyle.jrefactor.parser.statement.JStatement;
import lombok.Data;

@Data
public class JIfThenElseStatement implements JControlStatement {
    
    private JExpression expression;
    private JStatement ifCondition;
    private JStatement elseCondition;
    
}
