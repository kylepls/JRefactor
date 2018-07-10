package in.kyle.jrefactor.refactor;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.refactor.files.ProjectRefactorSession;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.JVariable;
import in.kyle.jrefactor.tree.obj.block.JStatementBlock;
import in.kyle.jrefactor.tree.obj.statement.JStatementLocalVariableDeclaration;

public class ProjectRefactorSessionTest {
    
    @Test
    public void testReplace() throws IllegalAccessException {
        TestClass test = new TestClass();
        test.a = "hello";
        ProjectRefactorSession.replaceField(test, test.a, "Test.java");
        Verify.that(test.a).isEqual("Test.java");
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
        
        ProjectRefactorSession session = new ProjectRefactorSession(block);
        session.rename(var.getName(), "Test.java");
        JIdentifier name = variable.getVariables().get(0).getName();
        Verify.that(name.getName()).isEqual("Test.java");
    }
    
    class TestClass {
        
        private String a = "a";
        private String b = "b";
    }
}
