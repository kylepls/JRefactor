package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.enummember.classmember
        .classinitializer.JClassStaticInitializer;

public class TestJClassStaticInitializer {
    
    @Test
    public void test() {
        String test = "static {}";
        JClassStaticInitializer initializer = Parser.parse(test, JClassStaticInitializer.class);
        Verify.that(initializer.getBlock()).isNotNull();
    }
}