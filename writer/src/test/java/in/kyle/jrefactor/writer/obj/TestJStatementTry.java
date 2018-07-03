package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.block.JStatementBlock;
import in.kyle.jrefactor.tree.obj.modifiable.JCatchClause;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementTry;
import in.kyle.jrefactor.writer.Write;

public class TestJStatementTry {
    @Test
    public void test() {
        JStatementTry statement = new JStatementTry(new JStatementBlock());
        Verify.that(Write.object(statement)).isEqual("try {}");
    }
    
    @Test
    public void testCatch() {
        JStatementTry statement = new JStatementTry(new JStatementBlock());
        JCatchClause catchClause = new JCatchClause(new JIdentifier("e"), new JStatementBlock());
        catchClause.addCatchType(new JTypeName("Exception"));
        statement.addCatchClause(catchClause);
        Verify.that(Write.object(statement)).isEqual("try {} catch(Exception e) {}");
    }
    
    @Test
    public void testFinally() {
        JStatementTry statement = new JStatementTry(new JStatementBlock());
        statement.setFinallyBlock(Optional.of(new JStatementBlock()));
        Verify.that(Write.object(statement)).isEqual("try {} finally {}");
    }
}
