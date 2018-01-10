package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.unit.types.annotationtype.JElementValue;
import lombok.Data;

public interface JAnnotationValue extends JObject {
    
    @Data
    class JPairCollection implements JAnnotationValue {
        
        private JObjectList<JElementPair> values = new JObjectList<>();
        
    }
    
    @Data
    class JSingleValue implements JAnnotationValue {
    
        private JElementValue value; 
        
    }
    
    @Data
    class JElementPair implements JObject {
    
        private JIdentifier identifier;
        private JElementValue value;
        
    }
}
