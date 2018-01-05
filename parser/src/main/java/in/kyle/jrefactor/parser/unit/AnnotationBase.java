package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObjectList;
import lombok.Data;

@Data
public abstract class AnnotationBase implements JAnnotatable {
    
    private JObjectList<JAnnotation> annotations = new JObjectList<>();
    
    @Override
    public boolean addAnnotation(JAnnotation annotation) {
        return annotations.add(annotation);
    }
    
    @Override
    public boolean removeAnnotation(JAnnotation annotation) {
        return annotations.remove(annotation);
    }
    
}
