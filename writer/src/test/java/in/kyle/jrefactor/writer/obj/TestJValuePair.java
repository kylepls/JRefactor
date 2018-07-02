package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.annotationvalue.JValuePair;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralInteger;
import in.kyle.jrefactor.writer.Write;

public class TestJValuePair {
    
    @Test
    public void test() {
        JValuePair pair = new JValuePair(new JIdentifier("value"), new JLiteralInteger(1));
        Verify.that(Write.object(pair)).isEqual("value = 1");
    }
}
