package in.kyle.parser.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import in.kyle.parser.RewriteableField;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
import lombok.ToString;

@ToString
public class JBlock implements JBlockStatement {
    
    private final List<RewriteableField<JBlockStatement>> statements = new ArrayList<>();
    
    public boolean addStatement(JBlockStatement statement) {
        return CollectionUtils.addValue(statements, statement);
    }
    
    public boolean removeStatement(JBlockStatement statement) {
        return CollectionUtils.removeValue(statements, statement);
    }
    
    public void setStatements(Set<JBlockStatement> statements) {
        CollectionUtils.overwrite(this.statements, statements);
    }
    
    public Set<JBlockStatement> getStatements() {
        return CollectionUtils.convertToJObjectSet(statements);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{");
        writer.indent();
        writer.appendLine();
        for (RewriteableField<JBlockStatement> statement : statements) {
            writer.append(statement).appendLine();
        }
        writer.dedent();
        writer.appendLine("}");
    }
    
    @Override
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(statements);
    }
}
