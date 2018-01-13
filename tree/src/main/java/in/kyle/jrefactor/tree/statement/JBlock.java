package in.kyle.jrefactor.tree.statement;

import in.kyle.jrefactor.tree.JObjectList;
import in.kyle.jrefactor.tree.expression.lambda.JLambdaBody;
import lombok.Data;

@Data
public class JBlock implements JStatement, JLambdaBody {
    
    private JObjectList<JStatement> statements;
    
    public JBlock(JObjectList<JStatement> statements) {
        this.statements = statements;
    }
    
    public JBlock() {
        this.statements = new JObjectList<>();
    }
    
    public boolean addStatement(JStatement statement) {
        return statements.add(statement);
    }
    
    public boolean removeStatement(JStatement statement) {
        return statements.remove(statement);
    }
}
