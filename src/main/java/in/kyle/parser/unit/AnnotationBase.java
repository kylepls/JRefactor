package in.kyle.parser.unit;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import in.kyle.parser.RewriteableField;

abstract class AnnotationBase implements JAnnotatable {
    
    private final Set<RewriteableField<JAnnotation>> annotations = new LinkedHashSet<>();
    
    @Override
    public boolean addAnnotation(JAnnotation annotation) {
        return CollectionUtils.addValue(annotations, annotation);
    }
    
    @Override
    public boolean removeAnnotation(JAnnotation annotation) {
        return CollectionUtils.removeValue(annotations, annotation);
    }
    
    @Override
    public void setAnnotations(Set<JAnnotation> set) {
        CollectionUtils.overwrite(annotations, set);
    }
    
    @Override
    public Set<JAnnotation> getAnnotations() {
        return CollectionUtils.convertToJObjectSet(annotations);
    }
    
    protected Set<RewriteableField<JAnnotation>> getRewriteableAnnotations() {
        return Collections.unmodifiableSet(annotations);
    }
}
