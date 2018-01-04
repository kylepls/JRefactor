package in.kyle.parser.unit.body;

import java.util.Iterator;

import in.kyle.JObjectList;
import in.kyle.parser.JObject;
import in.kyle.parser.expression.JExpression;
import in.kyle.writer.CodeWriter;
import lombok.Data;

@Data
public class JArgumentList implements JObject {
    
    private JObjectList<JExpression> arguments = new JObjectList<>();
    
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
    
}
