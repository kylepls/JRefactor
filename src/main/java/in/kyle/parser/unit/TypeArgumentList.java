package in.kyle.parser.unit;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class TypeArgumentList {
    
    private final Set<JTypeArgument> typeArguments = new LinkedHashSet<>();
    
    public boolean addTypeArgument(JTypeArgument argument) {
        return typeArguments.add(argument);
    }
    
    public boolean removeTypeArguement(JTypeArgument argument) {
        return typeArguments.remove(argument);
    }
    
    public List<JObject> getChildren() {
        return CollectionUtils.createList(typeArguments);
    }
    
    public void writeTypeArguments(CodeWriter writer) {
        for (Iterator<JTypeArgument> iterator = typeArguments.iterator(); iterator.hasNext(); ) {
            JTypeArgument argument = iterator.next();
            writer.append(argument);
            if (iterator.hasNext()) {
                writer.append(", ");
            }
        }
    }
}
