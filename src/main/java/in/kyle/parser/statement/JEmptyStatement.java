package in.kyle.parser.statement;

import java.util.Collections;
import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;

public class JEmptyStatement implements JStatement {
    @Override
    public void write(CodeWriter writer) {
        writer.append(";");
    }
    
    @Override
    public List<JObject> getChildren() {
        return Collections.emptyList();
    }
}
