package in.kyle.jrefactor.parser.obj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.expressionunary.JUnaryPrePost;
import in.kyle.jrefactor.tree.obj.expression.expressionunary.JUnaryPrePost.JUnaryOperator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RunWith(Parameterized.class)
public class TestJUnaryPrePost {
    
    private final JUnaryOperator operator;
    
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        for (JUnaryOperator operator : JUnaryOperator.values()) {
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
        if (operator.name().contains("PRE")) {
            javaString = operator.getJavaString() + "i";
        } else {
            javaString = "i" + operator.getJavaString();
        }
        
        JUnaryPrePost expression = Parser.parse(javaString, JUnaryPrePost.class);
        Verify.that(expression).isNotNull();
        Verify.that(expression.getExpression()).isNotNull();
        Verify.that(expression.getOperator()).isEqual(operator);
    }
}