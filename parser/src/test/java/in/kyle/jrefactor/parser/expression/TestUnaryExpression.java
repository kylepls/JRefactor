package in.kyle.jrefactor.parser.expression;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.parser.antlr.gen.Java8Parser;
import lombok.AllArgsConstructor;

import static in.kyle.jrefactor.parser.expression.JUnaryExpression.Operator;

@AllArgsConstructor
@RunWith(Parameterized.class)
public class TestUnaryExpression {
    
    private final Operator operator;
    
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        for (Operator operator : Operator.values()) {
            data.add(testCase(operator));
        }
        return data;
    }
    
    private static Object[] testCase(Object... args) {
        return args;
    }
    
    @Test
    public void testUnary() {
        String javaString;
        if (operator.isBeforeOperator()) {
            javaString = operator.getJavaString() + "i";
        } else {
            javaString = "i" + operator.getJavaString();
        }
        
        
        System.out.println(javaString);
        JUnaryExpression expression = Parser.parse(javaString, Java8Parser::expression);
        Verify.that(expression).isNotNull();
        Verify.that(expression.getExpression()).isNotNull();
        Verify.that(expression.getOperator()).isEqual(operator);
    }
}
