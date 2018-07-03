package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JEnumConstant;

public class TestJEnumConstant {

    @Test
    public void test() {
        String test = "TEST()";
        JEnumConstant constant = Parser.parse(test, JEnumConstant.class);
        Verify.that(constant.getArguments()).isEmpty();
        Verify.that(constant.getBody()).isNotPresent();
        Verify.that(constant.getIdentifier()).isNotNull();
    }
    
    @Test
    public void testArg() {
        String test = "TEST(1, 2, 3)";
        JEnumConstant constant = Parser.parse(test, JEnumConstant.class);
        Verify.that(constant.getArguments()).sizeIs(3);
    }
    
    @Test
    public void testBody() {
        String test = "TEST() {}";
        JEnumConstant constant = Parser.parse(test, JEnumConstant.class);
        Verify.that(constant.getBody()).isPresent();
    }
}