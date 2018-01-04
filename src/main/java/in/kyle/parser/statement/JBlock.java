package in.kyle.parser.statement;

import in.kyle.JObjectList;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JBlock implements JStatement {
    
    private JObjectList<JStatement> statements = new JObjectList<>();
    
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
    
}
