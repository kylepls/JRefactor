package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralBoolean;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralString;
import in.kyle.jrefactor.tree.obj.statement.JStatementAssert;
import in.kyle.jrefactor.writer.Write;

public class TestJStatementAssert {
    
    @Test
    public void test() {
        JStatementAssert assertStatement = new JStatementAssert();
        assertStatement.setAssertion(new JLiteralBoolean(true));
        Verify.that(Write.object(assertStatement)).isEqual("assert true;");
    }
    
    @Test
    public void testMessage() {
        JStatementAssert assertStatement = new JStatementAssert();
        assertStatement.setAssertion(new JLiteralBoolean(true));
        assertStatement.setMessage(Optional.of(new JLiteralString("error")));
        Verify.that(Write.object(assertStatement)).isEqual("assert true : \"error\";");
    }
}
