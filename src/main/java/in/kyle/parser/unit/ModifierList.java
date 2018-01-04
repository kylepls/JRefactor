package in.kyle.parser.unit;

import in.kyle.JObjectList;
import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class ModifierList implements JObject {
    
    private JObjectList<JModifier> modifiers = new JObjectList<>();
    
    public boolean addModifier(JModifier modifier) {
        return modifiers.add(modifier);
    }
    
    public boolean removeModifier(JModifier modifier) {
        return modifiers.remove(modifier);
    }
    
    @Override
    public void write(CodeWriter writer) {
        for (JModifier modifier : modifiers) {
            writer.append(modifier).append(" ");
        }
    }
}
