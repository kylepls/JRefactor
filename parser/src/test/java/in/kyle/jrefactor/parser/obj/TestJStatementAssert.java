package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.statement.JStatementAssert;

public class TestJStatementAssert {

    @Test
    public void test() {
        String test = "assert true;";
        JStatementAssert statement = Parser.parse(test, JStatementAssert.class);
        Verify.that(statement.getAssertion()).isNotNull();
        Verify.that(statement.getMessage()).isNotPresent();
    }
    
    @Test
    public void testMessage() {
        String test = "assert true : \"tru\";";
        JStatementAssert statement = Parser.parse(test, JStatementAssert.class);
        Verify.that(statement.getAssertion()).isNotNull();
        Verify.that(statement.getMessage()).isPresent();        
    }
}