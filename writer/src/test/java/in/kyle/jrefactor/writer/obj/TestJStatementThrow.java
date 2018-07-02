package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JExpression;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementThrow;
import in.kyle.jrefactor.writer.Write;

public class TestJStatementThrow {
    @Test
    public void test() {
        JStatementThrow statement = new JStatementThrow();
        JExpression expression = new JExpressionName(new JIdentifier("error"));
        statement.setExpression(expression);
        Verify.that(Write.object(statement)).isEqual("throw error;");
    }
}
