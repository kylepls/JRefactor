package in.kyle.parser.statement.control;

import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.parser.unit.JIdentifier;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JContinueStatement implements JControlStatement {
    
    private JIdentifier identifier;
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(identifier);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("continue");
        if (identifier != null) {
            writer.append(" {}", identifier);
        }
    }
}
