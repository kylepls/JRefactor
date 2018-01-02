package in.kyle.parser.unit;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public abstract class Modifiable extends AnnotationBase {
   
    private Set<JModifier> modifiers = new LinkedHashSet<>();
    
    public boolean addModifier(JModifier modifier) {
        return modifiers.add(modifier);
    }
    
    public boolean removeModifier(JModifier modifier) {
        return modifiers.remove(modifier);
    }
    
    public void writeModifiers(CodeWriter writer) {
        for (JModifier modifier : modifiers) {
            writer.append(modifier).append(" ");
        }
    }
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(super.getChildren(), modifiers);
    }
}
