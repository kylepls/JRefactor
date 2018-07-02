package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import in.kyle.jrefactor.tree.obj.statement.JBlock;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementSynchronized;
import in.kyle.jrefactor.writer.Write;

public class TestJStatementSynchronized {
    
    @Test
    public void test() {
        JStatementSynchronized statement = new JStatementSynchronized();
        statement.setExpression(new JExpressionName(new JIdentifier("test")));
        statement.setBlock(new JBlock());
        Verify.that(Write.object(statement)).isEqual("synchronized (test) {}");
    }
}
