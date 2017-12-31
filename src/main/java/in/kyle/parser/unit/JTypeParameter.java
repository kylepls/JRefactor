package in.kyle.parser.unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.parser.RewriteableField;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JTypeParameter extends JTypeName implements JObject {
    
    private final List<RewriteableField<JTypeName>> bounds = new ArrayList<>(0);
    
    public JTypeParameter(String name) {
        super(name);
    }
    
    public Set<JTypeName> getBounds() {
        return CollectionUtils.convertToJObjectSet(bounds);
    }
    
    public void setInitialBound(JTypeName name) {
        if (bounds.isEmpty()) {
            CollectionUtils.addValue(bounds, name);
        } else {
            bounds.set(0, new RewriteableField<>(name));
        }
    }
    
    public void setBounds(Set<JTypeName> set) {
        CollectionUtils.overwrite(bounds, set);
    }
    
    public boolean addBound(JTypeName bound) {
        return CollectionUtils.addValue(bounds, bound);
    }
    
    public boolean removeBound(JTypeName bound) {
        return CollectionUtils.removeValue(bounds, bound);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append(getName());
        int i = 0;
        for (RewriteableField<JTypeName> bound : bounds) {
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
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(bounds);
    }
}
