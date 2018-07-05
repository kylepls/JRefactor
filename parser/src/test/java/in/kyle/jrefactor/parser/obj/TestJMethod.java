package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.enummember.classmember.JMethod;

public class TestJMethod {

    @Test
    public void test() {
        String text = "void test() {}";
        JMethod method = Parser.parse(text, JMethod.class);
        Verify.that(method.getHeader()).isNotNull();
        Verify.that(method.getBody()).isNotNull();
    }
}