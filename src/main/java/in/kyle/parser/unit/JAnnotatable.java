package in.kyle.parser.unit;

import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;

public interface JAnnotatable extends JObject {
    
    boolean addAnnotation(JAnnotation annotation);
    
    boolean removeAnnotation(JAnnotation annotation);
    
    void setAnnotations(Set<JAnnotation> annotations);
    
    Set<JAnnotation> getAnnotations();
    
    default void writeAnnotations(CodeWriter writer) {
        for (JAnnotation annotation : getAnnotations()) {
            writer.append("{} ", annotation);
        }
    }
}
