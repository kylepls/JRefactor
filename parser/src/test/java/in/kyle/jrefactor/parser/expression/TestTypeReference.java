package in.kyle.jrefactor.parser.expression;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.expression.JTypeReferenceExpression;
import in.kyle.jrefactor.tree.unit.JTypeName;

public class TestTypeReference {
    
    @Test
    public void testSimpleReference() {
        String javaString = "tEsT.class";
        JTypeReferenceExpression expression =
                Parser.parse(javaString, JTypeReferenceExpression.class);
        Verify.that(expression.getReference()).isNotNull();
        JTypeName reference = expression.getReference();
        Verify.that(reference.getName()).isEqual("tEsT");
    }
}
