package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.block.JStatementBlock;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.enummember.classmember
        .classinitializer.JClassStaticInitializer;
import in.kyle.jrefactor.writer.Write;

public class TestJClassStaticInitializer {
    
    @Test
    public void test() {
        JClassStaticInitializer initializer = new JClassStaticInitializer(new JStatementBlock());
        Verify.that(Write.object(initializer)).isEqual("static {}");
    }
}
