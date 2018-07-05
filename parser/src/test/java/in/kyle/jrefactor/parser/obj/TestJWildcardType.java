package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentWildcard.JWildcardType;

import static in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentWildcard
        .JWildcardType.EXTENDS;
import static in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentWildcard
        .JWildcardType.SUPER;

public class TestJWildcardType {

    @Test
    public void testExtends() {
        String test = "extends";
        JWildcardType type = Parser.parse(test, JWildcardType.class);
        Verify.that(type).isEqual(EXTENDS);
    }
    
    @Test
    public void testSuper() {
        String test = "super";
        JWildcardType type = Parser.parse(test, JWildcardType.class);
        Verify.that(type).isEqual(SUPER);        
    }
}