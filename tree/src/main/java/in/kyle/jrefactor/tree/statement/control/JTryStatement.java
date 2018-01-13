package in.kyle.jrefactor.tree.statement.control;

import java.util.Optional;

import in.kyle.jrefactor.tree.JObjectList;
import in.kyle.jrefactor.tree.statement.JBlock;
import lombok.Data;

@Data
public class JTryStatement implements JControlStatement {
    
    private JBlock block;
    private JObjectList<JCatchClause> catchClauses = new JObjectList<>();
    private Optional<JBlock> finallyBlock;
    
}
