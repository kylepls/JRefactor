package in.kyle.jrefactor.parser.obj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.JModifier;
import lombok.AllArgsConstructor;

@RunWith(Parameterized.class)
@AllArgsConstructor
public class TestJModifier {
    
    private final JModifier modifier;
    
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.stream(JModifier.values())
                     .map(m -> new Object[]{m})
                     .collect(Collectors.toList());
    }
    
    @Test
    public void test() {
        String string = modifier.getJavaString();
        Verify.that(Parser.parse(string, JModifier.class)).isEqual(modifier);
    }
}