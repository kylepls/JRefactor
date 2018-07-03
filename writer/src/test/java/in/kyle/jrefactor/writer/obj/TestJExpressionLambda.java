package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.block.JStatementBlock;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLambda;
import in.kyle.jrefactor.writer.Write;

public class TestJExpressionLambda {
    
    @Test
    public void test() {
        JExpressionLambda lambda = new JExpressionLambda();
        lambda.setBody(new JStatementBlock());
        Verify.that(Write.object(lambda)).isEqual("()->{}");
    }
    
    @Test
    public void testInferredParameters() {
        JExpressionLambda lambda = new JExpressionLambda();
        lambda.addParameter(new JIdentifier("a"));
        lambda.setBody(new JStatementBlock());
        Verify.that(Write.object(lambda)).isEqual("(a)->{}");
    }
}
