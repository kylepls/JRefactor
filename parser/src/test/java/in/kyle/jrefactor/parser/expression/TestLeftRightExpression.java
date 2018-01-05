package in.kyle.jrefactor.parser.expression;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import in.kyle.api.utils.StringUtils;
import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.parser.antlr.gen.Java8Parser;
import in.kyle.jrefactor.parser.expression.JLeftRightExpression.Operation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RunWith(Parameterized.class)
public class TestLeftRightExpression {
    
    private final Operation operator;
    private final Object value;
    
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        data.add(testCase(Operation.ADD, 1));
        data.add(testCase(Operation.SUBTRACT, 1));
        data.add(testCase(Operation.MULTIPLY, 1));
        data.add(testCase(Operation.DIVIDE, 1));
        data.add(testCase(Operation.MODULUS, 1));
        data.add(testCase(Operation.CONDITIONAL_AND, true));
        data.add(testCase(Operation.CONDITIONAL_OR, true));
        data.add(testCase(Operation.CONDITIONAL_LESS_THAN, 1));
        data.add(testCase(Operation.CONDITIONAL_GREATER_THAN, 1));
        data.add(testCase(Operation.CONDITIONAL_LESS_THAN_EQUAL, 1));
        data.add(testCase(Operation.CONDITIONAL_GREATER_EQUAL, 1));
        data.add(testCase(Operation.INSTANCE_OF, "var2"));
        data.add(testCase(Operation.EQUAL, 1));
        data.add(testCase(Operation.NOT_EQUAL, 1));
        data.add(testCase(Operation.BINARY_SHIFT_LEFT, 1));
        data.add(testCase(Operation.BINARY_SHIFT_RIGHT, 1));
        data.add(testCase(Operation.BINARY_ALLIGN_RIGHT, 1));
        data.add(testCase(Operation.BINARY_INCLUSIVE_OR, 1));
        data.add(testCase(Operation.BINARY_EXCLUSIVE_OR, 1));
        data.add(testCase(Operation.BINARY_AND, 1));
        return data;
    }
    
    private static Object[] testCase(Object... args) {
        return args;
    }
    
    @Test
    public void testExpression() throws IOException {
        String javaString = StringUtils.replaceVariables("var {} {}", operator.getJavaString(), value);
        System.out.println(javaString);
        JLeftRightExpression expression = Parser.parse(javaString, Java8Parser::expression);
        Verify.that(expression.getLeft()).isInstanceOf(JExpressionName.class);
        Verify.that(expression.getRight()).isNotNull();
    }
}
