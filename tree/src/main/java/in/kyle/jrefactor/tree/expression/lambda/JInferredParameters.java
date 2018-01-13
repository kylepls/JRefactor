package in.kyle.jrefactor.tree.expression.lambda;

import in.kyle.jrefactor.tree.JObjectList;
import in.kyle.jrefactor.tree.unit.JIdentifier;
import lombok.Data;

@Data
public class JInferredParameters extends JObjectList<JIdentifier> implements JLambdaParameters {

}
