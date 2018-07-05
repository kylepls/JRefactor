package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.switchcase.JSwitchCaseDefault;

public class TestJSwitchCaseDefault {

    @Test
    public void test() {
        String test = "default: ;";
        JSwitchCaseDefault switchCase = Parser.parse(test, JSwitchCaseDefault.class);
        Verify.that(switchCase.getStatements()).sizeIs(1);
    }
}