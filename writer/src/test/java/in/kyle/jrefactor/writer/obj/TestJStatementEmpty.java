package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.statement.JStatementEmpty;
import in.kyle.jrefactor.writer.Write;

public class TestJStatementEmpty {
    
    @Test
    public void test() {
        Verify.that(Write.object(new JStatementEmpty())).isEqual(";");
    }
}
