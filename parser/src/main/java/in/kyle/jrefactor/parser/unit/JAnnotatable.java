package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObject;

public interface JAnnotatable extends JObject {
    
    JAnnotationList getAnnotations();
    
    void setAnnotations(JAnnotationList list);
    
}
