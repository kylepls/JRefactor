package in.kyle.jrefactor.parser.unit;

import java.util.Iterator;

import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.JObjectList;
import lombok.Data;

@Data
public class JTypeParameterList implements JObject {
    
    private JObjectList<JTypeParameter> typeParameters = new JObjectList<>();
    private boolean showTypeParametersEmpty = false;
    
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
