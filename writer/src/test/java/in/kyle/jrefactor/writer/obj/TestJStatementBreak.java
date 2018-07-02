package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.identifiablestatement.JStatementBreak;
import in.kyle.jrefactor.writer.Write;

public class TestJStatementBreak {
    @Test
    public void test() {
        JStatementBreak statement = new JStatementBreak();
        Verify.that(Write.object(statement)).isEqual("break;");
    }
    
    @Test
    public void testBreakLabel() {
        JStatementBreak statement = new JStatementBreak();
        statement.setIdentifier(Optional.of(new JIdentifier("area")));
        Verify.that(Write.object(statement)).isEqual("break area;");        
    }
}
