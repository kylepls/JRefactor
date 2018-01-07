package in.kyle.jrefactor.parser.statement;

import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.expression.lambda.JLambdaBody;
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
