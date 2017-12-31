package in.kyle.parser.unit;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.RewriteableField;
import in.kyle.writer.CodeWriter;

public abstract class Modifiable extends AnnotationBase {
    
    private final Set<RewriteableField<JModifier>> modifiers = new LinkedHashSet<>();
    
    public boolean addModifier(JModifier modifier) {
        return CollectionUtils.addValue(modifiers, modifier);
    }
    
    public boolean removeModifier(JModifier modifier) {
        return CollectionUtils.removeValue(modifiers, modifier);
    }
    
    public Set<JModifier> getModifiers() {
        return CollectionUtils.convertToJObjectSet(modifiers);
    }
    
    public void setModifiers(Set<JModifier> set) {
        CollectionUtils.overwrite(modifiers, set);
    }
    
    public void writeModifiers(CodeWriter writer) {
        for (RewriteableField<JModifier> modifier : modifiers) {
            writer.append(modifier).append(" ");
        }
    }
    
    @Override
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(super.getChildren(), modifiers);
    }
}
