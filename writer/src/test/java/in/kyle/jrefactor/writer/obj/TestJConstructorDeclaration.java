package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.JModifier;
import in.kyle.jrefactor.tree.obj.JAnnotation;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.block.JStatementBlock;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JConstructorDeclaration;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JParameter;
import in.kyle.jrefactor.writer.Write;

public class TestJConstructorDeclaration {
    
    @Test
    public void testModifiers() {
        JConstructorDeclaration declaration = new JConstructorDeclaration();
        declaration.setIdentifier(new JTypeName("Test"));
        declaration.setBody(new JStatementBlock());
        declaration.addModifier(JModifier.PUBLIC);
        Verify.that(Write.object(declaration)).isEqual("public Test() {}");
    }
    
    @Test
    public void testAnnotations() {
        JConstructorDeclaration declaration = new JConstructorDeclaration();
        declaration.setIdentifier(new JTypeName("Test"));
        declaration.setBody(new JStatementBlock());
        declaration.addAnnotation(new JAnnotation(new JTypeName("Data")));
        Verify.that(Write.object(declaration)).isEqual("@Data\nTest() {}");
    }
    
    @Test
    public void test() {
        JConstructorDeclaration declaration = new JConstructorDeclaration();
        declaration.setIdentifier(new JTypeName("Test"));
        declaration.setBody(new JStatementBlock());
        Verify.that(Write.object(declaration)).isEqual("Test() {}");
    }
    
    @Test
    public void testParameter() {
        JConstructorDeclaration declaration = new JConstructorDeclaration();
        declaration.setIdentifier(new JTypeName("Test"));
        declaration.setBody(new JStatementBlock());
        declaration.addParameter(new JParameter(new JTypeName("String"), new JIdentifier("str")));
        Verify.that(Write.object(declaration)).isEqual("Test(String str) {}");
    }
    
    @Test
    public void testThrows() {
        JConstructorDeclaration declaration = new JConstructorDeclaration();
        declaration.setIdentifier(new JTypeName("Test"));
        declaration.setBody(new JStatementBlock());
        declaration.addThrowsType(new JTypeName("Exception"));
        Verify.that(Write.object(declaration)).isEqual("Test() throws Exception {}");    }
}
