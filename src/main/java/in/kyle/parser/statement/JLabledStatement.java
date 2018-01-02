package in.kyle.parser.statement;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.parser.unit.JIdentifier;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JLabledStatement implements JStatement {
    
    private JIdentifier identifier;
    private JStatement statement;
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(identifier, statement);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{}: {}", identifier, statement);
    }
}
