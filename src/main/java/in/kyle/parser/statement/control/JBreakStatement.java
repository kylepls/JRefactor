package in.kyle.parser.statement.control;

import in.kyle.parser.unit.JIdentifier;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JBreakStatement implements JControlStatement {
    
    private JIdentifier identifier;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("break");
        if (identifier != null) {
            writer.append(" {}", identifier);
        }
    }
}
