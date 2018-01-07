package in.kyle.jrefactor.parser.statement;

import in.kyle.jrefactor.parser.unit.body.VariableHolder;
import in.kyle.jrefactor.CodeWriter;

public class JLocalVariableDeclaration extends VariableHolder implements JStatement {
    
    @Override
    public void write(CodeWriter writer) {
        super.write(writer);
        writer.append(";");
    }
    
    @Override
    public void writeAnnotations(CodeWriter writer) {
        
    }
}
