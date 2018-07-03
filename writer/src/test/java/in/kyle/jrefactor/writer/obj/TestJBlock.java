package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JBlock;
import in.kyle.jrefactor.tree.obj.block.JStatementBlock;
import in.kyle.jrefactor.tree.obj.statement.JStatementEmpty;
import in.kyle.jrefactor.writer.Write;

public class TestJBlock {
    
    @Test
    public void test() {
        Verify.that(Write.object(new JStatementBlock())).isEqual("{}");
    }
    
    @Test
    public void testElement() {
        JBlock block = new JStatementBlock();
        block.addElement(new JStatementEmpty());
        Verify.that(Write.object(block)).isEqual("{\n    ;\n}");
    }
}
