package in.kyle.parser.unit;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.body.JParameter;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JParameterList implements JObject {
    
    private Set<JParameter> parameters = new LinkedHashSet<>();
    
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
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(parameters);
    }
}
