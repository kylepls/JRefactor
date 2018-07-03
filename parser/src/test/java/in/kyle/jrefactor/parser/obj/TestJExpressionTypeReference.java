package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.reference.JExpressionTypeReference;

public class TestJExpressionTypeReference {
    
    @Test
    public void test() {
        // TODO: 7/3/2018 What does this class even do? 
        String javaString = "Test";
        JExpressionTypeReference expression =
                Parser.parse(javaString, JExpressionTypeReference.class);
        Verify.that(expression.getReference()).isNotNull();
        JTypeName reference = expression.getReference();
        Verify.that(reference.getType()).isEqual("tEsT");
    }
}