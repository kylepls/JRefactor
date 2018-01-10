package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.expression.lambda.JLambdaParameters;
import in.kyle.jrefactor.parser.unit.body.JParameter;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
public class JParameterList implements JLambdaParameters {
    
    @Delegate
    private JObjectList<JParameter> parameters = new JObjectList<>();
    
}
