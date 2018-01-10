package in.kyle.jrefactor.parser.expression;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.parser.antlr.gen.Java8Parser;
import in.kyle.jrefactor.parser.unit.JTypeName;

public class TestTypeReference {
    
    @Test
    public void testSimpleReference() {
        String javaString = "tEsT.class";
        JTypeReferenceExpression expression =
                Parser.parse(javaString, Java8Parser::primaryClassType);
        Verify.that(expression.getReference()).isNotNull();
        JTypeName reference = expression.getReference();
        Verify.that(reference.getName()).isEqual("tEsT");
    }
}
