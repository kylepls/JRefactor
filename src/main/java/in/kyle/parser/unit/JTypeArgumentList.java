package in.kyle.parser.unit;

import java.util.Iterator;

import in.kyle.JObjectList;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JTypeArgumentList {
    
    private JObjectList<JTypeArgument> typeArguments = new JObjectList<>();
    private boolean showTypeParametersEmpty = false;
    
    public boolean addTypeArgument(JTypeArgument argument) {
        return typeArguments.add(argument);
    }
    
    public boolean removeTypeArguement(JTypeArgument argument) {
        return typeArguments.remove(argument);
    }
    
    public void writeTypeArguments(CodeWriter writer) {
        if (!typeArguments.isEmpty()) {
            for (Iterator<JTypeArgument> iterator =
                 typeArguments.iterator(); iterator.hasNext(); ) {
                JTypeArgument argument = iterator.next();
                writer.append(argument);
                if (iterator.hasNext()) {
                    writer.append(", ");
                }
            }
        } else if (showTypeParametersEmpty) {
            writer.append("<>");
        }
    }
}
