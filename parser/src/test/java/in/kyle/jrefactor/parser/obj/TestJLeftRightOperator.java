package in.kyle.jrefactor.parser.obj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight.JLeftRightOperator;
import lombok.AllArgsConstructor;

@RunWith(Parameterized.class)
@AllArgsConstructor
public class TestJLeftRightOperator {
    
    private final JLeftRightOperator operator;
    
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.stream(JLeftRightOperator.values())
                     .map(a -> new Object[]{a})
                     .collect(Collectors.toList());
    }
    
    @Test
    public void test() {
        String string = operator.getJavaString();
        Verify.that(Parser.parse(string, JLeftRightOperator.class)).isNotNull();
    }
}