package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.block.typebody.JClassBody;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralInteger;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JEnumConstant;
import in.kyle.jrefactor.writer.Write;

public class TestJEnumConstant {
    
    @Test
    public void test() {
        JEnumConstant constant = new JEnumConstant();
        constant.setIdentifier(new JIdentifier("TEST"));
        Verify.that(Write.object(constant)).isEqual("TEST()");
    }
    
    @Test
    public void testArguments() {
        JEnumConstant constant = new JEnumConstant();
        constant.setIdentifier(new JIdentifier("TEST"));
        constant.addArgument(new JLiteralInteger(1));
        constant.addArgument(new JLiteralInteger(2));
        Verify.that(Write.object(constant)).isEqual("TEST(1, 2)");
    }
    
    @Test
    public void testBody() {
        JEnumConstant constant = new JEnumConstant();
        constant.setIdentifier(new JIdentifier("TEST"));
        constant.setBody(Optional.of(new JClassBody()));
        Verify.that(Write.object(constant)).isEqual("TEST() {}");
    }
}
