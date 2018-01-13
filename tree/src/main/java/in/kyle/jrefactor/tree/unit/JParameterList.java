package in.kyle.jrefactor.tree.unit;

import in.kyle.jrefactor.tree.JObjectList;
import in.kyle.jrefactor.tree.expression.lambda.JLambdaParameters;
import in.kyle.jrefactor.tree.unit.body.JParameter;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
public class JParameterList implements JLambdaParameters {
    
    @Delegate
    private JObjectList<JParameter> parameters = new JObjectList<>();
    
}
