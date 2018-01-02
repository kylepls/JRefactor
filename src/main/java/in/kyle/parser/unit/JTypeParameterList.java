package in.kyle.parser.unit;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class JTypeParameterList {
    
    private Set<JTypeParameter> typeParameters = new LinkedHashSet<>();
    @Getter
    @Setter
    private boolean showTypeParametersEmpty = false;
    
    public boolean addTypeParameter(JTypeParameter parameter) {
        return typeParameters.add(parameter);
    }
    
    public boolean removeTypeParameter(JTypeParameter parameter) {
        return typeParameters.remove(parameter);
    }
    
    public void writeTypeParameters(CodeWriter writer) {
        Set<JTypeParameter> typeParameters = getTypeParameters();
        if (!typeParameters.isEmpty()) {
            writer.append("<");
            for (Iterator<JTypeParameter> iterator =
                 typeParameters.iterator(); iterator.hasNext(); ) {
                JTypeParameter typeParameter = iterator.next();
                writer.append(typeParameter);
                if (iterator.hasNext()) {
                    writer.append(", ");
                }
            }
            writer.append(">");
        } else if (showTypeParametersEmpty) {
            writer.append("<>");
        }
    }
    
    public List<JObject> getChildren() {
        return CollectionUtils.createList(typeParameters);
    }
}
