package in.kyle.jrefactor.tree.unit;

import in.kyle.jrefactor.tree.JObject;
import in.kyle.jrefactor.tree.JObjectList;
import in.kyle.jrefactor.tree.unit.types.annotationtype.JElementValue;
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
