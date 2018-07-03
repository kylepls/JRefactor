package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLambda;

public class TestJExpressionLambda {
    
    @Test
    public void testLambdaNoParameters() {
        String lambda = "()->{}";
        JExpressionLambda expression = Parser.parse(lambda, JExpressionLambda.class);
        Verify.that(expression.getParameters()).isNotNull();
        Verify.that(expression.getParameters()).isEmpty();
        Verify.that(expression.getBody()).isNotNull();
    }
    
    @Test
    public void testLambdaIdentifier() {
        String lambda = "a->{}";
        JExpressionLambda expression = Parser.parse(lambda, JExpressionLambda.class);
        Verify.that(expression.getParameters()).sizeIs(1);
    }
    
    @Test
    public void testLambdaInferredParameters() {
        String lambda = "(a, b, c)->{}";
        JExpressionLambda expression = Parser.parse(lambda, JExpressionLambda.class);
        Verify.that(expression.getParameters()).sizeIs(3);
    }
    
    @Test
    public void testLambdaExpressionBlock() {
        String lambda = "()->true";
        JExpressionLambda expression = Parser.parse(lambda, JExpressionLambda.class);
        Verify.that(expression.getBody()).isNotNull();
    }
}