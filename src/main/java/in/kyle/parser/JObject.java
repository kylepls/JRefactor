package in.kyle.parser;

import java.util.Collections;
import java.util.List;

import in.kyle.writer.CodeWriter;

public interface JObject {
    
    void write(CodeWriter writer);
    
    default List<JObject> getChildren() {
        return Collections.emptyList();
    }
}
