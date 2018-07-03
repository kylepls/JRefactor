package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.block.JStatementBlock;
import in.kyle.jrefactor.tree.obj.modifiable.JCatchClause;
import in.kyle.jrefactor.writer.Write;

public class TestJCatchClause {
    @Test
    public void test() {
        JCatchClause catchClause = new JCatchClause(new JIdentifier("e"), new JStatementBlock());
        catchClause.addCatchType(new JTypeName("RuntimeException"));
        catchClause.addCatchType(new JTypeName("Exception"));
        Verify.that(Write.object(catchClause)).isEqual("catch(RuntimeException | Exception e) {}");
    }
}
