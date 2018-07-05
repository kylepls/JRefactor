package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementSynchronized;

public class TestJStatementSynchronized {

    @Test
    public void test() {
        String test = "synchronized (var) {}";
        JStatementSynchronized statement = Parser.parse(test, JStatementSynchronized.class);
        Verify.that(statement.getBlock()).isNotNull();
        Verify.that(statement.getExpression()).isNotNull();
    }
}