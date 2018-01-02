package in.kyle.parser.unit;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import lombok.Data;

@Data
public abstract class AnnotationBase implements JAnnotatable {
    
    private Set<JAnnotation> annotations = new LinkedHashSet<>();
    
    @Override
    public boolean addAnnotation(JAnnotation annotation) {
        return annotations.add(annotation);
    }
    
    @Override
    public boolean removeAnnotation(JAnnotation annotation) {
        return annotations.remove(annotation);
    }
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(annotations);
    }
}
