package in.kyle.jrefactor.parser.statement.control;

import java.util.Optional;

import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.statement.JBlock;
import lombok.Data;

@Data
public class JTryStatement implements JControlStatement {
    
    private JBlock block;
    private JObjectList<JCatchClause> catchClauses = new JObjectList<>();
    private Optional<JBlock> finallyBlock;
    
}
