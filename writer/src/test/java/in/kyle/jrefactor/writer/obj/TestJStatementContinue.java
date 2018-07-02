package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.identifiablestatement
        .JStatementContinue;
import in.kyle.jrefactor.writer.Write;

public class TestJStatementContinue {
    
    @Test
    public void test() {
        JStatementContinue statement = new JStatementContinue();
        Verify.that(Write.object(statement)).isEqual("continue;");
    }
    
    @Test
    public void testLabeled() {
        JStatementContinue statement = new JStatementContinue();
        statement.setIdentifier(Optional.of(new JIdentifier("test")));
        Verify.that(Write.object(statement)).isEqual("continue test;");
    }
}
