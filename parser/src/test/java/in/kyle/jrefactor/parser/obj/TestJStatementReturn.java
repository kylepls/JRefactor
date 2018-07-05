package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementReturn;

public class TestJStatementReturn {

    @Test
    public void test() {
        String test = "return;";
        JStatementReturn statement = Parser.parse(test, JStatementReturn.class);
        Verify.that(statement.getExpression()).isNotPresent();
    }
    
    @Test
    public void testExpression() {
        String test = "return 2;";
        JStatementReturn statement = Parser.parse(test, JStatementReturn.class);
        Verify.that(statement.getExpression()).isPresent();        
    }
}