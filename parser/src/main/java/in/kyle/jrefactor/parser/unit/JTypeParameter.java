package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.JObjectList;
import lombok.Data;

@Data
public class JTypeParameter implements JObject {
    
    private JIdentifier name;
    private JObjectList<JTypeName> bounds = new JObjectList<>();
    
    public JTypeParameter(JIdentifier name) {
        this.name = name;
    }
    
    public void setInitialBound(JTypeName name) {
        if (bounds.isEmpty()) {
            bounds.add(name);
        } else {
            bounds.set(0, name);
        }
    }
    
    public boolean addBound(JTypeName bound) {
        return bounds.add(bound);
        
    }
    
    public boolean removeBound(JTypeName bound) {
        return bounds.remove(bound);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(getName());
        int i = 0;
        for (JTypeName bound : bounds) {
            if (i == 0) {
                writer.append(" extends ");
            } else {
                writer.append(" & ");
            }
            writer.append(bound);
            i++;
        }
    }
    
}
