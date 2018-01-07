package in.kyle.jrefactor.parser.unit;

import java.util.Iterator;

import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.expression.lambda.JLambdaParameters;
import in.kyle.jrefactor.parser.unit.body.JParameter;
import lombok.Data;

@Data
public class JParameterList implements JLambdaParameters {
    
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
