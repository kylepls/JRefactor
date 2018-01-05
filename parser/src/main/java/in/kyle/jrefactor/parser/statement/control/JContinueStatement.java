package in.kyle.jrefactor.parser.statement.control;

import in.kyle.jrefactor.parser.unit.JIdentifier;
import in.kyle.jrefactor.CodeWriter;
import lombok.Data;

@Data
public class JContinueStatement implements JControlStatement {
    
    private JIdentifier identifier;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("continue");
        if (identifier != null) {
            writer.append(" {}", identifier);
        }
    }
}
