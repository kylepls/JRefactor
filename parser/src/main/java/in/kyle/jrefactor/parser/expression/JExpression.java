package in.kyle.jrefactor.parser.expression;

import in.kyle.jrefactor.parser.expression.lambda.JLambdaBody;
import in.kyle.jrefactor.parser.unit.body.JVariableInitializer;
import in.kyle.jrefactor.parser.unit.types.annotationtype.JElementValue;

public interface JExpression extends JVariableInitializer, JElementValue, JLambdaBody {
}
