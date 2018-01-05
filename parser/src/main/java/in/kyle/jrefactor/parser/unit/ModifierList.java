package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.JObjectList;
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
