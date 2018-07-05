package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementThrow;

public class TestJStatementThrow {

    @Test
    public void test() {
        String test = "throw e;";
        JStatementThrow statement = Parser.parse(test, JStatementThrow.class);
        Verify.that(statement.getExpression()).isNotNull();
    }
}