package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentWildcard;
import in.kyle.jrefactor.writer.Write;

import static in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentWildcard
        .JWildcardType.EXTENDS;

public class TestJTypeArgumentWildcard {
    @Test
    public void test() {
        JTypeArgumentWildcard wildcard = new JTypeArgumentWildcard();
        Verify.that(Write.object(wildcard)).isEqual("?");
    }
    
    @Test
    public void testType() {
        JTypeArgumentWildcard wildcard = new JTypeArgumentWildcard();
        wildcard.setType(EXTENDS);
        wildcard.setReference(new JTypeName("String"));
        Verify.that(Write.object(wildcard)).isEqual("? extends String");
    }
}
