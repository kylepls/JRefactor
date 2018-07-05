package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.statement.JStatementEmpty;

public class TestJStatementEmpty {

    @Test
    public void test() {
        String test = ";";
        JStatementEmpty statement = Parser.parse(test, JStatementEmpty.class);
        Verify.that(statement).isNotNull();
    }
}