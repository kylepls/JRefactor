package in.kyle.parser.unit;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import in.kyle.writer.CodeWriter;

abstract class JModifiable extends AnnotationBase {
    
    private final Set<JModifier> modifiers = new LinkedHashSet<>();
    
    public boolean addModifier(JModifier modifier) {
        return modifiers.add(modifier);
    }
    
    public boolean removeModifier(JModifier modifier) {
        return modifiers.remove(modifier);
    }
    
    public Set<JModifier> getModifiers() {
        return Collections.unmodifiableSet(modifiers);
    }
    
    public void setModifiers(Set<JModifier> set) {
        modifiers.clear();
        modifiers.addAll(set);
    }
    
    public void writeModifiers(CodeWriter writer) {
        for (JModifier modifier : modifiers) {
            writer.append(modifier).append(" ");
        }
    }
}
