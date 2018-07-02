package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.expression.JExpressionTernary;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralBoolean;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralInteger;
import in.kyle.jrefactor.writer.Write;

public class TestJExpressionTernary {
    
    @Test
    public void test() {
        JExpressionTernary ternary = new JExpressionTernary(new JLiteralInteger(1),
                                                            new JLiteralBoolean(true),
                                                            new JLiteralInteger(2));
        Verify.that(Write.object(ternary)).isEqual("true ? 1 : 2");
    }
}
