package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObject;

public interface JModifiable extends JObject {
    
    JModifierList getModifiers();
    
    void setModifiers(JModifierList list);
}
