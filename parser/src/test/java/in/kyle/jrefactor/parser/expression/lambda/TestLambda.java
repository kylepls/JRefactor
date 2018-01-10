package in.kyle.jrefactor.parser.expression.lambda;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.parser.antlr.gen.Java8Parser;
import in.kyle.jrefactor.parser.expression.literal.JBooleanLiteral;
import in.kyle.jrefactor.parser.statement.JBlock;
import in.kyle.jrefactor.parser.unit.JParameterList;

public class TestLambda {
    
    @Test
    public void testLambdaNoParameters() {
        String lambda = "()->{}";
        JLambdaExpression expression = Parser.parse(lambda, Java8Parser::lambdaExpression);
        Verify.that(expression.getParameters()).isNotNull();
        Verify.that(expression.getParameters()).isInstanceOf(JParameterList.class);
        Verify.that(expression.getBody()).isNotNull();
        Verify.that(expression.getBody()).isInstanceOf(JBlock.class);
    }
    
    @Test
    public void testLambdaIdentifier() {
        String lambda = "a->{}";
        JLambdaExpression expression = Parser.parse(lambda, Java8Parser::lambdaExpression);
        Verify.that(expression.getParameters()).isNotNull();
        Verify.that(expression.getParameters()).isInstanceOf(JIdentifierParameter.class);
        JIdentifierParameter parameter = (JIdentifierParameter) expression.getParameters();
        Verify.that(parameter.getName()).isEqual("a");
        Verify.that(expression.getBody()).isNotNull();
        Verify.that(expression.getBody()).isInstanceOf(JBlock.class);
    }
    
    @Test
    public void testLambdaInferredParameters() {
        String lambda = "(a, b, c)->{}";
        JLambdaExpression expression = Parser.parse(lambda, Java8Parser::lambdaExpression);
        Verify.that(expression.getParameters()).isNotNull();
        Verify.that(expression.getParameters()).isInstanceOf(JInferredParameters.class);
        JInferredParameters parameters = (JInferredParameters) expression.getParameters();
        Verify.that(parameters).sizeIs(3);
        Verify.that(parameters.get(0).getName()).isEqual("a");
        Verify.that(parameters.get(1).getName()).isEqual("b");
        Verify.that(expression.getBody()).isNotNull();
        Verify.that(expression.getBody()).isInstanceOf(JBlock.class);
    }
    
    @Test
    public void testLambdaExpressionBlock() {
        String lambda = "()->true";
        JLambdaExpression expression = Parser.parse(lambda, Java8Parser::lambdaExpression);
        Verify.that(expression.getParameters()).isNotNull();
        Verify.that(expression.getParameters()).isInstanceOf(JParameterList.class);
        Verify.that(expression.getBody()).isNotNull();
        Verify.that(expression.getBody()).isInstanceOf(JBooleanLiteral.class);
    }
}
