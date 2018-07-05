package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.statementcontrolloop.statementfor
        .JStatementBasicFor;

public class TestJStatementBasicFor {

    @Test
    public void test() {
        int i = 0;
        String test = "for (;;) ;";
        JStatementBasicFor statement = Parser.parse(test, JStatementBasicFor.class);
        Verify.that(statement.getInit()).sizeIs(0);
        Verify.that(statement.getCondition()).isNotPresent();
        Verify.that(statement.getUpdate()).sizeIs(0);
        Verify.that(statement.getStatement()).isNotNull();
    }
    
    @Test
    public void testInit() {
        String test = "for (int i = 0;;) ;";
        JStatementBasicFor statement = Parser.parse(test, JStatementBasicFor.class);
        Verify.that(statement.getInit()).sizeIs(1);
        Verify.that(statement.getStatement()).isNotNull();        
    }
    
    @Test
    public void testCondition() {
        String test = "for (;i>0;) ;";
        JStatementBasicFor statement = Parser.parse(test, JStatementBasicFor.class);
        Verify.that(statement.getCondition()).isPresent();
        Verify.that(statement.getStatement()).isNotNull();                
    }
    
    @Test
    public void testUpdate() {
        String test = "for (;;i++) ;";
        JStatementBasicFor statement = Parser.parse(test, JStatementBasicFor.class);
        Verify.that(statement.getUpdate()).sizeIs(1);
        Verify.that(statement.getStatement()).isNotNull();                        
    }
}