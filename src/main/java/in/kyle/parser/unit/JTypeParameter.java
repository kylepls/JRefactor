package in.kyle.parser.unit;

import java.util.ArrayList;
import java.util.List;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JTypeParameter extends JTypeName implements JObject {
    
    private List<JTypeName> bounds = new ArrayList<>(0);
    
    public JTypeParameter(String name) {
        super(name);
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
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(bounds);
    }
}
