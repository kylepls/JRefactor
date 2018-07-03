package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.enummember.classmember
        .classinitializer.JClassInstanceInitializer;

public class TestJClassInstanceInitializer {

    @Test
    public void test() {
        String test = "{}";
        JClassInstanceInitializer initializer = Parser.parse(test, JClassInstanceInitializer.class);
        Verify.that(initializer.getBlock()).isNotNull();
    }
}