package in.kyle.parser.statement.control.loops;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.expression.JExpression;
import in.kyle.parser.statement.JStatement;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JBasicForStatement implements JLoopStatement {
    
    private JExpression init;
    private JExpression expression;
    private JExpression update;
    private JStatement statement;
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(init, expression, update, statement);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("for ({}; {}; {}) {}", init, expression, update, statement);
    }
}
