package in.kyle.jrefactor.parser.expression;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.expression.JExpressionTypeReference;

public class TestTypeReference {
    
    @Test
    public void testSimpleReference() {
        String javaString = "tEsT.class";
        JExpressionTypeReference expression =
                Parser.parse(javaString, JExpressionTypeReference.class);
        Verify.that(expression.getReference()).isNotNull();
        JTypeName reference = expression.getReference();
        Verify.that(reference.getType()).isEqual("tEsT");
    }
}
