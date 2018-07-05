package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.switchcase.JSwitchCaseExpression;

public class TestJSwitchCaseExpression {

    @Test
    public void test() {
        String test = "case 1: ;";
        JSwitchCaseExpression switchCase = Parser.parse(test, JSwitchCaseExpression.class);
        Verify.that(switchCase.getStatements()).sizeIs(1);
    }
}