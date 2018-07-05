package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.statementcontrolloop.statementfor.JStatementEnhancedFor;

public class TestJStatementEnhancedFor {

    @Test
    public void test() {
        String test = "for (int i : ints) ;";
        JStatementEnhancedFor statement = Parser.parse(test, JStatementEnhancedFor.class);
        Verify.that(statement.getVariable()).isNotNull();
        Verify.that(statement.getExpression()).isNotNull();
        Verify.that(statement.getVariableModifiers()).isEmpty();
    }
    
    @Test
    public void testModifier() {
        String test = "for (final int i : ints) ;";
        JStatementEnhancedFor statement = Parser.parse(test, JStatementEnhancedFor.class);
        Verify.that(statement.getVariableModifiers()).sizeIs(1);        
    }
}