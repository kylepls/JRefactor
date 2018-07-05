package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.identifiablestatement.JStatementBreak;

public class TestJStatementBreak {

    @Test
    public void test() {
        String test = "break;";
        JStatementBreak statement = Parser.parse(test, JStatementBreak.class);
        Verify.that(statement.getIdentifier()).isNotPresent();
    }
    
    @Test
    public void testIdentifier() {
        String test = "break test;";
        JStatementBreak statement = Parser.parse(test, JStatementBreak.class);
        Verify.that(statement.getIdentifier()).isPresent();        
    }
}