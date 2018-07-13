package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.variabledefinition.JVariable;

public class TestJVariable {

    @Test
    public void test() {
        String test = "i";
        JVariable variable = Parser.parse(test, JVariable.class);
        Verify.that(variable.getInitializer()).isNotPresent();
        Verify.that(variable.getIdentifier()).isNotNull();
    }
    
    @Test
    public void testInitializer() {
        String test = "i = 0";
        JVariable variable = Parser.parse(test, JVariable.class);
        Verify.that(variable.getInitializer()).isPresent();
        Verify.that(variable.getIdentifier()).isNotNull();        
    }
}