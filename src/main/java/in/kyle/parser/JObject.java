package in.kyle.parser;

import java.util.List;

import in.kyle.writer.CodeWriter;

public interface JObject {
    
    void write(CodeWriter writer);
    
    List<JObject> getChildren();
}
