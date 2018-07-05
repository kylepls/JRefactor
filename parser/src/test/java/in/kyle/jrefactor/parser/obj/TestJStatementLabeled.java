package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.statement.JStatementLabeled;

public class TestJStatementLabeled {

    @Test
    public void test() {
        String test = "test: ;";
        JStatementLabeled labeled = Parser.parse(test, JStatementLabeled.class);
        Verify.that(labeled.getIdentifier()).isNotNull();
        Verify.that(labeled.getStatement()).isNotNull();
    }
}