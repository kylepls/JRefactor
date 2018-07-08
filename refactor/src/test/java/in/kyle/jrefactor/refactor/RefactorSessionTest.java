package in.kyle.jrefactor.refactor;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.JVariable;
import in.kyle.jrefactor.tree.obj.block.JStatementBlock;
import in.kyle.jrefactor.tree.obj.statement.JStatementLocalVariableDeclaration;

public class RefactorSessionTest {
    
    @Test
    public void testReplace() throws IllegalAccessException {
        TestClass test = new TestClass();
        test.a = "hello";
        RefactorSession.replaceField(test, test.a, "test");
        Verify.that(test.a).isEqual("test");
        Verify.that(test.b).isEqual("b");
    }
    
    @Test
    public void testRename() {
        // @formatter:off
        JVariable var = JVariable.builder().name(JIdentifier.of("string")).build();
        JStatementLocalVariableDeclaration variable = JStatementLocalVariableDeclaration.builder()
            .type(JTypeName.of("String"))
            .addVariables(var)
            .build();
        JStatementBlock block = JStatementBlock.builder()
            .addElements(variable)
            .build();        
        // @formatter:on
        
        RefactorSession session = new RefactorSession(block);
        session.rename(var.getName(), "test");
        JIdentifier name = variable.getVariables().get(0).getName();
        Verify.that(name.getName()).isEqual("test");
    }
    
    class TestClass {
        
        private String a = "a";
        private String b = "b";
    }
}
