package in.kyle.parser.statement;

import in.kyle.parser.unit.JIdentifier;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JLabledStatement implements JStatement {
    
    private JIdentifier identifier;
    private JStatement statement;
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{}: {}", identifier, statement);
    }
}
