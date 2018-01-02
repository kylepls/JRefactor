package in.kyle.parser.unit.body;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import in.kyle.parser.JObject;
import in.kyle.parser.expression.JExpression;
import in.kyle.parser.unit.CollectionUtils;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JArgumentList implements JObject {
    
    private Set<JExpression> arguments = new LinkedHashSet<>();
    
    public boolean addArgument(JExpression arg) {
        return arguments.add(arg);
    }
    
    public boolean removeArguement(JExpression arg) {
        return arguments.remove(arg);
    }
    
    public void write(CodeWriter writer) {
        for (Iterator<JExpression> iterator = arguments.iterator(); iterator.hasNext(); ) {
            JExpression argument = iterator.next();
            writer.append(argument);
            if (iterator.hasNext()) {
                writer.append(", ");
            }
        }
    }
    
    @Override
    public List<JObject> getChildren() {
        return CollectionUtils.createList(arguments);
    }
}
