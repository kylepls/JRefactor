package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.statement.JStatementEmpty;
import in.kyle.jrefactor.tree.obj.statement.JStatementLabeled;
import in.kyle.jrefactor.writer.Write;

public class TestJStatementLabeled {
    
    @Test
    public void test() {
        JStatementLabeled statement = new JStatementLabeled();
        statement.setIdentifier(new JIdentifier("Test"));
        statement.setStatement(new JStatementEmpty());
    
        Verify.that(Write.object(statement)).isEqual("Test:\n    ;");
    }
}
