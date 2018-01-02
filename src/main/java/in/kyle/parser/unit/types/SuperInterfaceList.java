package in.kyle.parser.unit.types;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import in.kyle.parser.unit.JTypeName;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class SuperInterfaceList {
    
    private Set<JTypeName> superInterfaces = new LinkedHashSet<>();
    
    public boolean addSuperInterface(JTypeName typeName) {
        return superInterfaces.add(typeName);
    }
    
    public boolean removeSuperInterface(JTypeName typeName) {
        return superInterfaces.remove(typeName);
    }
    
    public void write(CodeWriter writer) {
        if (!superInterfaces.isEmpty()) {
            writer.append("implements ");
            for (Iterator<JTypeName> iterator = superInterfaces.iterator(); iterator.hasNext(); ) {
                JTypeName superInterface = iterator.next();
                writer.append(superInterface);
                if (iterator.hasNext()) {
                    writer.append(", ");
                }
            }
        }
    }
}
