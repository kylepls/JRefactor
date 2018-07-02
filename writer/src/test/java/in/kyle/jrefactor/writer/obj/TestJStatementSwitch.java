package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralInteger;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementSwitch;
import in.kyle.jrefactor.tree.obj.switchcase.JSwitchCaseDefault;
import in.kyle.jrefactor.tree.obj.switchcase.JSwitchCaseExpression;
import in.kyle.jrefactor.writer.Write;

public class TestJStatementSwitch {
    
    @Test
    public void test() {
        JStatementSwitch statement = new JStatementSwitch();
        statement.setExpression(new JLiteralInteger(1));
        Verify.that(Write.object(statement)).isEqual("switch (1) {}");
    }
    
    @Test
    public void testElement() {
        JStatementSwitch statement = new JStatementSwitch();
        statement.setExpression(new JLiteralInteger(1));
        statement.addCaseElement(new JSwitchCaseExpression(new JLiteralInteger(1)));
        statement.addCaseElement(new JSwitchCaseDefault());
        Verify.that(Write.object(statement)).isEqual("switch (1) {\n    case 1:\n    default:\n}");
    }
}
