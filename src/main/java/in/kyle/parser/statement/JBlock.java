package in.kyle.parser.statement;

import java.util.ArrayList;
import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JBlock implements JStatement {
    
    private List<JStatement> statements = new ArrayList<>();
    
    public boolean addStatement(JStatement statement) {
        return statements.add(statement);
    }
    
    public boolean removeStatement(JStatement statement) {
        return statements.remove(statement);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{");
        writer.indent();
        writer.appendLine();
        for (JStatement statement : statements) {
            writer.append(statement).appendLine();
        }
        writer.dedent();
        writer.appendLine("}");
    }
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(statements);
    }
}
