package in.kyle.jrefactor.parser.obj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.JLeftRightOperator;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight.JLeftRightOperatorBinary;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight.JLeftRightOperatorConditional;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight.JLeftRightOperatorMath;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RunWith(Parameterized.class)
public class TestJExpressionLeftRight {
    
    private final JLeftRightOperator operator;
    private final Object value;
    
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        data.add(testCase(JLeftRightOperatorMath.ADD, 1));
        data.add(testCase(JLeftRightOperatorMath.SUBTRACT, 1));
        data.add(testCase(JLeftRightOperatorMath.MULTIPLY, 1));
        data.add(testCase(JLeftRightOperatorMath.DIVIDE, 1));
        data.add(testCase(JLeftRightOperatorMath.MODULUS, 1));
        data.add(testCase(JLeftRightOperatorConditional.CONDITIONAL_AND, true));
        data.add(testCase(JLeftRightOperatorConditional.CONDITIONAL_OR, true));
        data.add(testCase(JLeftRightOperatorConditional.CONDITIONAL_LESS_THAN, 1));
        data.add(testCase(JLeftRightOperatorConditional.CONDITIONAL_GREATER_THAN, 1));
        data.add(testCase(JLeftRightOperatorConditional.CONDITIONAL_LESS_THAN_EQUAL, 1));
        data.add(testCase(JLeftRightOperatorConditional.CONDITIONAL_GREATER_EQUAL, 1));
        data.add(testCase(JLeftRightOperatorConditional.INSTANCE_OF, "var2"));
        data.add(testCase(JLeftRightOperatorConditional.EQUAL, 1));
        data.add(testCase(JLeftRightOperatorConditional.NOT_EQUAL, 1));
        data.add(testCase(JLeftRightOperatorBinary.BINARY_SHIFT_LEFT, 1));
        data.add(testCase(JLeftRightOperatorBinary.BINARY_SHIFT_RIGHT, 1));
        data.add(testCase(JLeftRightOperatorBinary.BINARY_ALLIGN_RIGHT, 1));
        data.add(testCase(JLeftRightOperatorBinary.BINARY_INCLUSIVE_OR, 1));
        data.add(testCase(JLeftRightOperatorBinary.BINARY_EXCLUSIVE_OR, 1));
        data.add(testCase(JLeftRightOperatorBinary.BINARY_AND, 1));
        return data;
    }
    
    private static Object[] testCase(Object... args) {
        return args;
    }
    
    @Test
    public void testExpression() {
        String javaString = String.format("var %s %s", operator.getJavaString(), value);
        JExpressionLeftRight expression = Parser.parse(javaString, JExpressionLeftRight.class);
        Verify.that(expression.getLeft()).isInstanceOf(JExpressionName.class);
        Verify.that(expression.getRight()).isNotNull();
    }
}