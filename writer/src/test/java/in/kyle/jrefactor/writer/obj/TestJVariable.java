package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralInteger;
import in.kyle.jrefactor.tree.obj.variabledefinition.JVariable;
import in.kyle.jrefactor.writer.Write;

public class TestJVariable {
    
    @Test
    public void testVariable() {
        JVariable variable = new JVariable(new JIdentifier("test"));
        Verify.that(Write.object(variable)).isEqual("test");
    }
    
    @Test
    public void testInitializer() {
        JVariable variable = new JVariable(new JIdentifier("test"));
        variable.setInitializer(Optional.of(new JLiteralInteger(1)));
        Verify.that(Write.object(variable)).isEqual("test = 1");
    }
}
