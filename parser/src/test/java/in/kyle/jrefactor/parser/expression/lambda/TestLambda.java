package in.kyle.jrefactor.parser.expression.lambda;

import org.junit.Test;

import java.util.List;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JLambdaParameter;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLambda;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralBoolean;
import in.kyle.jrefactor.tree.obj.statement.JBlock;

public class TestLambda {
    
    @Test
    public void testLambdaNoParameters() {
        String lambda = "()->{}";
        JExpressionLambda expression = Parser.parse(lambda, JExpressionLambda.class);
        Verify.that(expression.getParameters()).isNotNull();
        Verify.that(expression.getParameters()).isEmpty();
        Verify.that(expression.getBody()).isNotNull();
        Verify.that(expression.getBody()).isInstanceOf(JBlock.class);
    }
    
    @Test
    public void testLambdaIdentifier() {
        String lambda = "a->{}";
        JExpressionLambda expression = Parser.parse(lambda, JExpressionLambda.class);
        Verify.that(expression.getParameters()).isNotNull();
        JIdentifier parameter = (JIdentifier) expression.getParameters();
        Verify.that(parameter.getName()).isEqual("a");
        Verify.that(expression.getBody()).isNotNull();
        Verify.that(expression.getBody()).isInstanceOf(JBlock.class);
    }
    
    @Test
    public void testLambdaInferredParameters() {
        String lambda = "(a, b, c)->{}";
        JExpressionLambda expression = Parser.parse(lambda, JExpressionLambda.class);
        Verify.that(expression.getParameters()).isNotNull();
        List<JLambdaParameter> parameters = expression.getParameters();
        Verify.that(parameters).sizeIs(3);
        Verify.that(parameters.get(0)).isEqual(new JIdentifier("a"));
        Verify.that(parameters.get(1)).isEqual(new JIdentifier("b"));
        Verify.that(parameters.get(2)).isEqual(new JIdentifier("c"));
        Verify.that(expression.getBody()).isNotNull();
        Verify.that(expression.getBody()).isInstanceOf(JBlock.class);
    }
    
    @Test
    public void testLambdaExpressionBlock() {
        String lambda = "()->true";
        JExpressionLambda expression = Parser.parse(lambda, JExpressionLambda.class);
        Verify.that(expression.getParameters()).isNotNull();
        Verify.that(expression.getParameters()).sizeIs(0);
        Verify.that(expression.getBody()).isNotNull();
        Verify.that(expression.getBody()).isInstanceOf(JLiteralBoolean.class);
    }
}
