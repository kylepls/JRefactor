package in.kyle.parser.statement.control;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.expression.JExpression;
import in.kyle.parser.statement.JStatement;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JIfThenElseStatement implements JControlStatement {
    
    private JExpression expression;
    private JStatement ifCondition;
    private JStatement elseCondition;
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(expression, ifCondition, elseCondition);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.appendLine("if ({}) {}", expression, ifCondition);
        writer.appendLine("else {}", elseCondition);
    }
}
