package in.kyle.jrefactor.parser.obj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.JLeftRightOperator;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight;
import lombok.AllArgsConstructor;

@RunWith(Parameterized.class)
@AllArgsConstructor
public class TestJLeftRightOperatorBinary {
    
    private final JLeftRightOperator operator;
    
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        List<JLeftRightOperator> collect = new ArrayList<>();
        collect.addAll(Arrays.asList(JExpressionLeftRight.JLeftRightOperatorBinary.values()));
        
        return collect.stream().map(a -> new Object[]{a}).collect(Collectors.toList());
        
    }
    
    @Test
    public void test() {
        String string = operator.getJavaString();
        Verify.that(Parser.parse(string, JLeftRightOperator.class)).isNotNull();
    }
}
