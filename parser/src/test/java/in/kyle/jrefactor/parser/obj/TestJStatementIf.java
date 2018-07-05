package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementIf;

public class TestJStatementIf {

    @Test
    public void test() {
        String test = "if (true) ;";
        JStatementIf statement = Parser.parse(test, JStatementIf.class);
        Verify.that(statement.getStatement()).isNotNull();
        Verify.that(statement.getExpression()).isNotNull();
        Verify.that(statement.getElseStatement()).isNotPresent();
    }
    
    @Test
    public void testElse() {
        String test = "if (true) ; else ;";
        JStatementIf statement = Parser.parse(test, JStatementIf.class);
        Verify.that(statement.getElseStatement()).isPresent();        
    }
}