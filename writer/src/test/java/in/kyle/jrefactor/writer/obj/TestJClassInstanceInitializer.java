package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.block.JStatementBlock;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.enummember.classmember
        .classinitializer.JClassInstanceInitializer;
import in.kyle.jrefactor.writer.Write;

public class TestJClassInstanceInitializer {
    
    @Test
    public void test() {
        JClassInstanceInitializer initializer = new JClassInstanceInitializer(new JStatementBlock());
        Verify.that(Write.object(initializer)).isEqual("{}");
    }
}
