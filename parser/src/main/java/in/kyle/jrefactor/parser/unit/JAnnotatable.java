package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.JObjectList;

public interface JAnnotatable extends JObject {
    
    boolean addAnnotation(JAnnotation annotation);
    
    boolean removeAnnotation(JAnnotation annotation);
    
    void setAnnotations(JObjectList<JAnnotation> annotations);
    
    JObjectList<JAnnotation> getAnnotations();
    
    default void writeAnnotations(CodeWriter writer) {
        for (JAnnotation annotation : getAnnotations()) {
            writer.append("{} ", annotation);
        }
    }
}
