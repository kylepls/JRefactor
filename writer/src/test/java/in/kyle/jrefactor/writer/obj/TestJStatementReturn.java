package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralBoolean;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementReturn;
import in.kyle.jrefactor.writer.Write;

public class TestJStatementReturn {
    
    @Test
    public void testReturn() {
        JStatementReturn statement = new JStatementReturn();
        Verify.that(Write.object(statement)).isEqual("return;");
    }
    
    @Test
    public void testExpression() {
        JStatementReturn statement = new JStatementReturn();
        statement.setExpression(Optional.of(new JLiteralBoolean(true)));
        Verify.that(Write.object(statement)).isEqual("return true;");        
    }
}
