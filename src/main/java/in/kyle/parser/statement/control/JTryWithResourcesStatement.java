package in.kyle.parser.statement.control;

import java.util.LinkedHashSet;
import java.util.Set;

import in.kyle.parser.statement.JBlock;
import in.kyle.writer.CodeWriter;

public class JTryWithResourcesStatement implements JControlStatement {
    
    // TODO: 1/2/2018  
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
    public void write(CodeWriter writer) {
        
    }
    
}
