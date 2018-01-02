package in.kyle.parser.statement;

import in.kyle.parser.unit.body.VariableHolder;
import in.kyle.writer.CodeWriter;

public class JLocalVariableDeclaration extends VariableHolder implements JStatement {
    
    @Override
    public void writeAnnotations(CodeWriter writer) {
        
    }
}
