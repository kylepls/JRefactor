package in.kyle.jrefactor.parser.unit.body;

import java.util.Iterator;

import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.expression.JExpression;
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
