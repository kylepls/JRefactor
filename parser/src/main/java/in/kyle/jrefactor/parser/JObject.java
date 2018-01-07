package in.kyle.jrefactor.parser;

import java.io.Serializable;

import in.kyle.jrefactor.CodeWriter;

public interface JObject extends Serializable {
    
    void write(CodeWriter writer);
    
}
