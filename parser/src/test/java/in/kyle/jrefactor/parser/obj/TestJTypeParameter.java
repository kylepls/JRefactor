package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JTypeParameter;

public class TestJTypeParameter {

    @Test
    public void test() {
        String test = "T";
        JTypeParameter parameter = Parser.parse(test, JTypeParameter.class);
        Verify.that(parameter.getBounds()).isEmpty();
        Verify.that(parameter.getIdentifier()).isNotNull();
    }
    
    @Test
    public void testBounds() {
        String test = "T extends A & B";
        JTypeParameter parameter = Parser.parse(test, JTypeParameter.class);
        Verify.that(parameter.getBounds()).sizeIs(2);
    }
}