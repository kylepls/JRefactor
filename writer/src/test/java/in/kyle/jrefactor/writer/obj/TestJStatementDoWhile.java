package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralBoolean;
import in.kyle.jrefactor.tree.obj.statement.JStatementEmpty;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.statementcontrolloop.statementwhile
        .JStatementDoWhile;
import in.kyle.jrefactor.writer.Write;

public class TestJStatementDoWhile {
    @Test
    public void test() {
        JStatementDoWhile statement = new JStatementDoWhile();
        statement.setStatement(new JStatementEmpty());
        statement.setExpression(new JLiteralBoolean(true));
        Verify.that(Write.object(statement)).isEqual("do ;\nwhile(true);");
    }
}
