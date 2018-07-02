package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralInteger;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.identifiablestatement.JStatementBreak;
import in.kyle.jrefactor.tree.obj.switchcase.JSwitchCaseExpression;
import in.kyle.jrefactor.writer.Write;

public class TestJSwitchCaseExpression {
    
    @Test
    public void test() {
        JSwitchCaseExpression expression = new JSwitchCaseExpression();
        expression.setCondition(new JLiteralInteger(1));
        Verify.that(Write.object(expression)).isEqual("case 1:");
    }
    
    @Test
    public void testStatements() {
        JSwitchCaseExpression expression = new JSwitchCaseExpression();
        expression.setCondition(new JLiteralInteger(1));
        expression.addStatement(new JStatementBreak());
        Verify.that(Write.object(expression)).isEqual("case 1:\n    break;");
    }
}
