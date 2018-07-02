package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.statement.JBlock;
import in.kyle.jrefactor.tree.obj.statement.JStatementEmpty;
import in.kyle.jrefactor.writer.Write;

public class TestJBlock {
    
    @Test
    public void test() {
        Verify.that(Write.object(new JBlock())).isEqual("{}");
    }
    
    @Test
    public void testElement() {
        JBlock block = new JBlock();
        block.addStatement(new JStatementEmpty());
        Verify.that(Write.object(block)).isEqual("{\n    ;\n}");
    }
}
