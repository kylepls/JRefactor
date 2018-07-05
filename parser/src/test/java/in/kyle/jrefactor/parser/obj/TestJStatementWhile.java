package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.statementcontrolloop.JStatementWhile;

public class TestJStatementWhile {

    @Test
    public void test() {
        String test = "while (true) ;";
        JStatementWhile statement = Parser.parse(test, JStatementWhile.class);
        Verify.that(statement.getExpression()).isNotNull();
        Verify.that(statement.getStatement()).isNotNull();
    }
}