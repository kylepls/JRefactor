package in.kyle.parser.block;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;

public class JBlock implements JObject {
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("{").append("}");
    }
}
