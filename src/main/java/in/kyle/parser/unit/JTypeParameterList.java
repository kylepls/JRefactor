package in.kyle.parser.unit;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.RewriteableField;
import in.kyle.writer.CodeWriter;
import lombok.Getter;
import lombok.Setter;

public class JTypeParameterList {
    
    private final Set<RewriteableField<JTypeParameter>> typeParameters = new LinkedHashSet<>();
    @Getter
    @Setter
    private boolean showTypeParametersEmpty = false;
    
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
    
    public List<RewriteableField> getChildren() {
        return CollectionUtils.createList(typeParameters);
    }
}
