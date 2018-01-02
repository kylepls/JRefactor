package in.kyle.parser.statement.control;

import java.util.Collections;
import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.expression.JExpression;
import in.kyle.writer.CodeWriter;

public class JSwitchStatement implements JControlStatement {
    
    private JExpression expression;
    
    @Override
    public void write(CodeWriter writer) {
        
    }
    
    @Override
    public List<JObject> getChildren() {
        return Collections.emptyList();
    }
}
