package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JStatementElse;
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
        JStatementElse else1 = new JStatementElse(new JStatementEmpty());
        statement.addElseStatement(else1);
        Verify.that(Write.object(statement)).isEqual("if (true)\n    ;\nelse\n    ;");
    }
}
