package in.kyle.parser.unit;

import java.util.Iterator;

import in.kyle.JObjectList;
import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JTypeParameterList implements JObject {
    
    private JObjectList<JTypeParameter> typeParameters = new JObjectList<>();
    private boolean showTypeParametersEmpty = false;
    
    public boolean addTypeParameter(JTypeParameter parameter) {
        return typeParameters.add(parameter);
    }
    
    public boolean removeTypeParameter(JTypeParameter parameter) {
        return typeParameters.remove(parameter);
    }
    
    @Override
    public void write(CodeWriter writer) {
        JObjectList<JTypeParameter> typeParameters = getTypeParameters();
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
}
