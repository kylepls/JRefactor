package in.kyle.jrefactor.tree.expression;

import in.kyle.jrefactor.tree.expression.lambda.JLambdaBody;
import in.kyle.jrefactor.tree.unit.body.JVariableInitializer;
import in.kyle.jrefactor.tree.unit.types.annotationtype.JElementValue;

public interface JExpression extends JVariableInitializer, JElementValue, JLambdaBody {
}
