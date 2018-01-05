package in.kyle.jrefactor.parser.statement;

import in.kyle.jrefactor.parser.unit.JIdentifier;
import in.kyle.jrefactor.CodeWriter;
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
