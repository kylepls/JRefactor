package in.kyle.jrefactor.parser.statement.control;

import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.statement.JBlock;
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
