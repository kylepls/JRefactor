package in.kyle.parser.unit;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.body.annotationtype.JElementValue;
import in.kyle.writer.CodeWriter;
import lombok.Data;

public interface JAnnotationValue extends JObject {
    
    @Data
    class JPairCollection implements JAnnotationValue {
        
        private Set<JElementPair> values = new LinkedHashSet<>();
        
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
    
        @Override
        public List<JObject> getChildren() {
            return CollectionUtils.createList(values);
        }
    }
    
    @Data
    class JSingleValue implements JAnnotationValue {
    
        private JElementValue value; 
        
        @Override
        public void write(CodeWriter writer) {
            writer.append(value);
        }
    
        @Override
        public List<JObject> getChildren() {
            return CollectionUtils.createList(value);
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
    
        @Override
        public List<JObject> getChildren() {
            return CollectionUtils.createList(identifier, value);
        }
    }
}
