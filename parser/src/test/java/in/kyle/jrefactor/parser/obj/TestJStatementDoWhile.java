package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.statementcontrolloop.statementwhile
        .JStatementDoWhile;

public class TestJStatementDoWhile {

    @Test
    public void test() {
        String test = "do ; while (true);";
        JStatementDoWhile statement = Parser.parse(test, JStatementDoWhile.class);
        Verify.that(statement.getExpression()).isNotNull();
        Verify.that(statement.getStatement()).isNotNull();
    }
}