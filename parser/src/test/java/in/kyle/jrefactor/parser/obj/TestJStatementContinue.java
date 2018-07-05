package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.identifiablestatement.JStatementContinue;

public class TestJStatementContinue {

    @Test
    public void test() {
        String test = "continue;";
        JStatementContinue statement = Parser.parse(test, JStatementContinue.class);
        Verify.that(statement.getIdentifier()).isNotPresent();
    }
    
    @Test
    public void testIdentifier() {
        String test = "continue test;";
        JStatementContinue statement = Parser.parse(test, JStatementContinue.class);
        Verify.that(statement.getIdentifier()).isPresent();        
    }
}