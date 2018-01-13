package in.kyle.jrefactor.tree.statement.control;

import java.util.LinkedHashSet;
import java.util.Set;

import in.kyle.jrefactor.tree.statement.JBlock;

public class JTryWithResourcesStatement implements JControlStatement {
    
    // TODO: 1/2/2018  
    private JBlock block;
    private Set<JCatchClause> catchClauses = new LinkedHashSet<>();
    private JBlock finallyBlock;
    
}
