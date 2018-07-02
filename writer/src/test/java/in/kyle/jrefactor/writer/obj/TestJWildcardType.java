package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.writer.Write;

import static in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentWildcard.JWildcardType.EXTENDS;
import static in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentWildcard.JWildcardType.SUPER;

public class TestJWildcardType {
    
    @Test
    public void test() {
        Verify.that(Write.object(EXTENDS)).isEqual("extends");
        Verify.that(Write.object(SUPER)).isEqual("super");
    }
}
