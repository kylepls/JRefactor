package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JParameter;
import in.kyle.jrefactor.tree.obj.statement.JStatementEmpty;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.statementcontrolloop.statementfor.JStatementEnhancedFor;
import in.kyle.jrefactor.writer.Write;

import static in.kyle.jrefactor.tree.JModifier.FINAL;

public class TestJStatementEnhancedFor {
    
    @Test
    public void test() {
        JStatementEnhancedFor statement = new JStatementEnhancedFor();
        statement.setVariable(new JParameter(new JTypeName("int"), new JIdentifier("i")));
        statement.setExpression(new JExpressionName(new JIdentifier("ints")));
        statement.setStatement(new JStatementEmpty());
        Verify.that(Write.object(statement)).isEqual("for (int i : ints) ;");
    }
    
    @Test
    public void testVariableModifier() {
        JStatementEnhancedFor statement = new JStatementEnhancedFor();
        statement.setVariable(new JParameter(new JTypeName("int"), new JIdentifier("i")));
        statement.setExpression(new JExpressionName(new JIdentifier("ints")));
        statement.setStatement(new JStatementEmpty());
        statement.addVariableModifier(FINAL);
        Verify.that(Write.object(statement)).isEqual("for (final int i : ints) ;");        
    }
}
