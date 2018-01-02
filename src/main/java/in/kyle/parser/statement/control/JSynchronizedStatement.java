package in.kyle.parser.statement.control;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.expression.JExpression;
import in.kyle.parser.statement.JBlock;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JSynchronizedStatement implements JControlStatement {
    
    private JExpression expression;
    private JBlock block;
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(expression, block);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("synchronized ({}) {}", expression, block);
    }
}
