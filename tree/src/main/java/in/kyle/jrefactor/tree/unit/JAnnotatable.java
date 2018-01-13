package in.kyle.jrefactor.tree.unit;

import in.kyle.jrefactor.tree.JObject;

public interface JAnnotatable extends JObject {
    
    JAnnotationList getAnnotations();
    
    void setAnnotations(JAnnotationList list);
    
}
