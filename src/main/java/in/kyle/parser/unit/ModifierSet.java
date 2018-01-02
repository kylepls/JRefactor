package in.kyle.parser.unit;

import java.util.LinkedHashSet;
import java.util.Set;

import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class ModifierSet {
    
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
}
