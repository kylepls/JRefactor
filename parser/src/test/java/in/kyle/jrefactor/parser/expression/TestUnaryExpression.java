package in.kyle.jrefactor.parser.expression;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.JExpressionUnary;
import in.kyle.jrefactor.tree.obj.expression.JExpressionUnary.JOperator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RunWith(Parameterized.class)
public class TestUnaryExpression {
    
    private final JOperator operator;
    
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        for (JOperator operator : JOperator.values()) {
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
        
        System.out.println(javaString);
        JExpressionUnary expression = Parser.parse(javaString, JExpressionUnary.class);
        Verify.that(expression).isNotNull();
        Verify.that(expression.getExpression()).isNotNull();
        Verify.that(expression.getOperator()).isEqual(operator);
    }
}
