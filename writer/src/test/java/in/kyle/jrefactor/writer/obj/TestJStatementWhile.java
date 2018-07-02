package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralBoolean;
import in.kyle.jrefactor.tree.obj.statement.JStatementEmpty;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.statementcontrolloop.JStatementWhile;
import in.kyle.jrefactor.writer.Write;

public class TestJStatementWhile {
    
    @Test
    public void test() {
        JStatementWhile statement = new JStatementWhile();
        statement.setExpression(new JLiteralBoolean(true));
        statement.setStatement(new JStatementEmpty());
        Verify.that(Write.object(statement)).isEqual("while (true) ;");
    }
}
