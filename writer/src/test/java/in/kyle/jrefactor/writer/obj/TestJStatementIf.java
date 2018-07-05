package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralBoolean;
import in.kyle.jrefactor.tree.obj.statement.JStatementEmpty;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementIf;
import in.kyle.jrefactor.writer.Write;

public class TestJStatementIf {
    
    @Test
    public void test() {
        JStatementIf statement = new JStatementIf();
        statement.setExpression(new JLiteralBoolean(true));
        statement.setStatement(new JStatementEmpty());
        Verify.that(Write.object(statement)).isEqual("if (true)\n    ;");
    }
    
    @Test
    public void testElse() {
        JStatementIf statement = new JStatementIf();
        statement.setExpression(new JLiteralBoolean(true));
        statement.setStatement(new JStatementEmpty());
        statement.setElseStatement(Optional.of(new JStatementEmpty()));
        Verify.that(Write.object(statement)).isEqual("if (true)\n    ;\nelse\n    ;");
    }
}
