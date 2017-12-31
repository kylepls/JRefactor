package in.kyle.parser.unit;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import in.kyle.parser.RewriteableField;
import in.kyle.writer.CodeWriter;

abstract class JTypeable extends JModifiable {
    
    private final Set<RewriteableField<JTypeParameter>> typeParameters = new LinkedHashSet<>();
    
    public Set<JTypeParameter> getTypeParameters() {
        return CollectionUtils.convertToJObjectSet(typeParameters);
    }
    
    public void setTypeParameters(Set<JTypeParameter> parameters) {
        CollectionUtils.overwrite(typeParameters, parameters);
    }
    
    public boolean addTypeParameter(JTypeParameter parameter) {
        return CollectionUtils.addValue(typeParameters, parameter);
    }
    
    public boolean removeTypeParameter(JTypeParameter parameter) {
        return CollectionUtils.removeValue(typeParameters, parameter);
    }
    
    void writeTypeParameters(CodeWriter writer) {
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
        }
    }
}
