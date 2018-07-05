package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementTry;

public class TestJStatementTry {

    @Test
    public void test() {
        String test = "try {} catch (Exception e) {}";
        JStatementTry statement = Parser.parse(test, JStatementTry.class);
        Verify.that(statement.getBlock()).isNotNull();
        Verify.that(statement.getCatchClauses()).sizeIs(1);
        Verify.that(statement.getFinallyBlock()).isNotPresent();
    }
    
    @Test
    public void testFinally() {
        String test = "try {} finally {}";
        JStatementTry statement = Parser.parse(test, JStatementTry.class);
        Verify.that(statement.getFinallyBlock()).isPresent();        
    }
}