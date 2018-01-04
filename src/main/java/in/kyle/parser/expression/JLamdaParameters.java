package in.kyle.parser.expression;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.JIdentifier;
import in.kyle.writer.CodeWriter;
import lombok.Data;

public interface JLamdaParameters extends JObject {
    
    @Data
    class JIdentifierParameter implements JLamdaParameters {
        
        private JIdentifier identifier;
        
        @Override
        public void write(CodeWriter writer) {
            writer.append(identifier);
        }
    
    }
    
}
