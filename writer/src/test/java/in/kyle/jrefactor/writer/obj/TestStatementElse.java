package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JStatementElse;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralBoolean;
import in.kyle.jrefactor.tree.obj.statement.JStatementEmpty;
import in.kyle.jrefactor.writer.Write;

public class TestStatementElse {
    
    @Test
    public void test() {
        JStatementElse elseStatement = new JStatementElse(new JStatementEmpty());
        Verify.that(Write.object(elseStatement)).isEqual("else\n    ;");
    }
    
    @Test
    public void testExpression() {
        JStatementElse elseStatement = new JStatementElse(new JStatementEmpty());
        elseStatement.setCondition(Optional.of(new JLiteralBoolean(false)));
        String object = Write.object(elseStatement);
        System.out.println(object);
        Verify.that(object).isEqual("else if(false)\n    ;");
    }
}
