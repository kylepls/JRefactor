package in.kyle.jrefactor.parser.expression;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

import in.kyle.api.utils.StringUtils;
import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import lombok.AllArgsConstructor;

import static in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight.JLeftRightOperator;

@AllArgsConstructor
@RunWith(Parameterized.class)
public class TestLeftRightExpression {
    
    private final JLeftRightOperator operator;
    private final Object value;
    
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        data.add(testCase(JLeftRightOperator.ADD, 1));
        data.add(testCase(JLeftRightOperator.SUBTRACT, 1));
        data.add(testCase(JLeftRightOperator.MULTIPLY, 1));
        data.add(testCase(JLeftRightOperator.DIVIDE, 1));
        data.add(testCase(JLeftRightOperator.MODULUS, 1));
        data.add(testCase(JLeftRightOperator.CONDITIONAL_AND, true));
        data.add(testCase(JLeftRightOperator.CONDITIONAL_OR, true));
        data.add(testCase(JLeftRightOperator.CONDITIONAL_LESS_THAN, 1));
        data.add(testCase(JLeftRightOperator.CONDITIONAL_GREATER_THAN, 1));
        data.add(testCase(JLeftRightOperator.CONDITIONAL_LESS_THAN_EQUAL, 1));
        data.add(testCase(JLeftRightOperator.CONDITIONAL_GREATER_EQUAL, 1));
        data.add(testCase(JLeftRightOperator.INSTANCE_OF, "var2"));
        data.add(testCase(JLeftRightOperator.EQUAL, 1));
        data.add(testCase(JLeftRightOperator.NOT_EQUAL, 1));
        data.add(testCase(JLeftRightOperator.BINARY_SHIFT_LEFT, 1));
        data.add(testCase(JLeftRightOperator.BINARY_SHIFT_RIGHT, 1));
        data.add(testCase(JLeftRightOperator.BINARY_ALLIGN_RIGHT, 1));
        data.add(testCase(JLeftRightOperator.BINARY_INCLUSIVE_OR, 1));
        data.add(testCase(JLeftRightOperator.BINARY_EXCLUSIVE_OR, 1));
        data.add(testCase(JLeftRightOperator.BINARY_AND, 1));
        return data;
    }
    
    private static Object[] testCase(Object... args) {
        return args;
    }
    
    @Test
    public void testExpression() {
        String javaString =
                StringUtils.replaceVariables("var {} {}", operator.getJavaString(), value);
        JExpressionLeftRight expression = Parser.parse(javaString, JExpressionLeftRight.class);
        Verify.that(expression.getLeft()).isInstanceOf(JExpressionName.class);
        Verify.that(expression.getRight()).isNotNull();
    }
}
