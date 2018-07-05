package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementSwitch;

public class TestJStatementSwitch {

    @Test
    public void test() {
        String test = "switch (1) {}";
        JStatementSwitch statement = Parser.parse(test, JStatementSwitch.class);
        Verify.that(statement.getCaseElements()).sizeIs(0);
        Verify.that(statement.getExpression()).isNotNull();
    }
    
    @Test
    public void testCase() {
        String test = "switch (1) { case 1: }";
        JStatementSwitch statement = Parser.parse(test, JStatementSwitch.class);
        Verify.that(statement.getCaseElements()).sizeIs(1);
    }
}