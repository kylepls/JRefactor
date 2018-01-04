package in.kyle.parser.statement.control;

import in.kyle.JObjectList;
import in.kyle.parser.statement.JBlock;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JTryStatement implements JControlStatement {
    
    private JBlock block;
    private JObjectList<JCatchClause> catchClauses = new JObjectList<>();
    private JBlock finallyBlock;
    
    public boolean addCatchClause(JCatchClause catchClause) {
        return catchClauses.add(catchClause);
    }
    
    public boolean removeCatchClause(JCatchClause catchClause) {
        return catchClauses.remove(catchClause);
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
