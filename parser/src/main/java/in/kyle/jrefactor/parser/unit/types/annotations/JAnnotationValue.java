package in.kyle.jrefactor.parser.unit;

import java.util.Iterator;

import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.unit.body.annotationtype.JElementValue;
import lombok.Data;

public interface JAnnotationValue extends JObject {
    
    @Data
    class JPairCollection implements JAnnotationValue {
        
        private JObjectList<JElementPair> values = new JObjectList<>();
        
        public boolean addValue(JElementPair pair) {
            return values.add(pair);
        }
        
        public boolean removeValue(JElementPair pair) {
            return values.remove(pair);
        }
        
        @Override
        public void write(CodeWriter writer) {
            for (Iterator<JElementPair> iterator = values.iterator(); iterator.hasNext(); ) {
                JElementPair pair = iterator.next();
                writer.append("{} = {}", pair.getIdentifier(), pair.getValue());
                if (iterator.hasNext()) {
                    writer.append(", ");
                }
            }
        }
    
    }
    
    @Data
    class JSingleValue implements JAnnotationValue {
    
        private JElementValue value; 
        
        @Override
        public void write(CodeWriter writer) {
            writer.append(value);
        }
    
    }
    
    @Data
    class JElementPair implements JObject {
    
        private JIdentifier identifier;
        private JElementValue value;
        
        @Override
        public void write(CodeWriter writer) {
            writer.append("{} = {}", identifier, value);
        }
    
    }
}
