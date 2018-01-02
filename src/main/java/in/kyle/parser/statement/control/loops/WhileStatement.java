package in.kyle.parser.statement.control.loops;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.expression.JExpression;
import in.kyle.parser.statement.JStatement;
import in.kyle.parser.unit.CollectionUtils;
import lombok.Data;

@Data
public abstract class WhileStatement implements JLoopStatement {
    
    private JExpression expression;
    private JStatement statement;
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(expression, statement);
    }
}
