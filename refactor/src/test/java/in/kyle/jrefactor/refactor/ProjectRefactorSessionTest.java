package in.kyle.jrefactor.refactor;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.refactor.session.FileRefactorSession;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.block.JStatementBlock;
import in.kyle.jrefactor.tree.obj.statement.JStatementLocalVariableDeclaration;
import in.kyle.jrefactor.tree.obj.variabledefinition.JVariable;

public class ProjectRefactorSessionTest {
    
    @Test
    public void testRename() {
        // @formatter:off
        JVariable var = JVariable.builder().identifier(JIdentifier.of("string")).build();
        JStatementLocalVariableDeclaration variable = JStatementLocalVariableDeclaration.builder()
            .type(JTypeName.of("String"))
            .addVariables(var)
            .build();
        JStatementBlock block = JStatementBlock.builder()
            .addElements(variable)
            .build();        
        // @formatter:on
        
        FileRefactorSession session = new FileRefactorSession(block);
        session.rename(var.getIdentifier(), "Test.java");
        JIdentifier name = variable.getVariables().get(0).getIdentifier();
        Verify.that(name.getName()).isEqual("Test.java");
    }
    
    class TestClass {
        
        private String a = "a";
        private String b = "b";
    }
}
