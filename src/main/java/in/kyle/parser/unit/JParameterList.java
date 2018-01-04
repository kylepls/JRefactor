package in.kyle.parser.unit;

import java.util.Iterator;

import in.kyle.JObjectList;
import in.kyle.parser.JObject;
import in.kyle.parser.unit.body.JParameter;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JParameterList implements JObject {
    
    private JObjectList<JParameter> parameters = new JObjectList<>();
    
    public boolean addParameter(JParameter parameter) {
        return parameters.add(parameter);
    }
    
    public boolean removeParameter(JParameter parameter) {
        return parameters.remove(parameter);
    }
    
    @Override
    public void write(CodeWriter writer) {
        writer.append("(");
        for (Iterator<JParameter> iterator = parameters.iterator(); iterator.hasNext(); ) {
            JParameter parameter = iterator.next();
            writer.append(parameter);
            if (iterator.hasNext()) {
                writer.append(", ");
            }
        }
        writer.append(")");
    }
    
}
