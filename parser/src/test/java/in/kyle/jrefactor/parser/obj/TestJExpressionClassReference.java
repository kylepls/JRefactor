package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.JExpressionClassReference;

public class TestJExpressionClassReference {
    
    @Test
    public void test() {
        String test = "test.class";
        JExpressionClassReference reference = Parser.parse(test, JExpressionClassReference.class);
        Verify.that(reference.getType()).isNotNull();
    }
}
