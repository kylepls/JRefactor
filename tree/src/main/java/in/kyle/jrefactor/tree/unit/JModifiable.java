package in.kyle.jrefactor.tree.unit;

import in.kyle.jrefactor.tree.JObject;

public interface JModifiable extends JObject {
    
    JModifierList getModifiers();
    
    void setModifiers(JModifierList list);
}
