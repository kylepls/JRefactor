package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.statement.JStatementEmpty;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.statementcontrolloop.statementfor
        .JStatementBasicFor;
import in.kyle.jrefactor.writer.Write;

public class TestJStatementBasicFor {
    @Test
    public void test() {
        JStatementBasicFor statement = new JStatementBasicFor() ;
        statement.setStatement(new JStatementEmpty());
        Verify.that(Write.object(statement)).isEqual("for (;;) ;");
    }
}
