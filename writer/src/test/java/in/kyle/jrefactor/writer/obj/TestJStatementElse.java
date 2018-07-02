package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JStatementElse;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralBoolean;
import in.kyle.jrefactor.tree.obj.statement.JStatementEmpty;
import in.kyle.jrefactor.writer.Write;

public class TestJStatementElse {
    
    @Test
    public void test() {
        JStatementElse statement = new JStatementElse();
        statement.setStatement(new JStatementEmpty());
        Verify.that(Write.object(statement)).isEqual("else\n    ;");
    }
    
    @Test
    public void testCondition() {
        JStatementElse statement = new JStatementElse();
        statement.setStatement(new JStatementEmpty());
        statement.setCondition(Optional.of(new JLiteralBoolean(true)));
        Verify.that(Write.object(statement)).isEqual("else if(true)\n    ;");
    }
}
