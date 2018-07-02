package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.JVariable;
import in.kyle.jrefactor.tree.obj.statement.JStatementLocalVariableDeclaration;
import in.kyle.jrefactor.writer.Write;

public class TestJStatementLocalVariableDeclaration {
    
    @Test
    public void test() {
        JStatementLocalVariableDeclaration declaration = new JStatementLocalVariableDeclaration();
        declaration.setType(new JTypeName("String"));
        declaration.addVariable(new JVariable(new JIdentifier("string")));
        Verify.that(Write.object(declaration)).isEqual("String string;");
    }
}
