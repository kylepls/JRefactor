package in.kyle.parser.statement.control;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.parser.statement.JBlock;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JTryStatement implements JControlStatement {
    
    private JBlock block;
    private Set<JCatchClause> catchClauses = new LinkedHashSet<>();
    private JBlock finallyBlock;
    
    public boolean addCatchClause(JCatchClause catchClause) {
        return catchClauses.add(catchClause);
    }
    
    public boolean removeCatchClause(JCatchClause catchClause) {
        return catchClauses.remove(catchClause);
    }
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(block, catchClauses, finallyBlock);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("try {}", block);
        for (JCatchClause catchClause : catchClauses) {
            writer.append(catchClause);
        }
        if (finallyBlock != null) {
            writer.append("finally {}", finallyBlock);
        }
    }
    
}
