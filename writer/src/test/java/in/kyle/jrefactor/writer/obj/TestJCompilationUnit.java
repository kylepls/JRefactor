package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JImport;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.block.typebody.JClassBody;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.superinterfacetype
        .typeparametertype.JClass;
import in.kyle.jrefactor.writer.Write;

public class TestJCompilationUnit {
    
    @Test
    public void testJustPackage() {
        JCompilationUnit unit = new JCompilationUnit();
        unit.setPackageName(Optional.of("pack.age"));
        Verify.that(Write.object(unit)).isEqual("package pack.age;");
    }
    
    @Test
    public void testJustImports() {
        JCompilationUnit unit = new JCompilationUnit();
        JImport import1 = new JImport();
        import1.setName(new JTypeName("Import1"));
        JImport import2 = new JImport();
        import2.setName(new JTypeName("Import2"));
        unit.addImports(import1);
        unit.addImports(import2);
        Verify.that(Write.object(unit)).isEqual("import Import1;\nimport Import2;");
    }
    
    @Test
    public void testTypes() {
        JCompilationUnit unit = new JCompilationUnit();
        JClass clazz = new JClass(new JIdentifier("Test"), new JClassBody());
        unit.addType(clazz);
        Verify.that(Write.object(unit)).isEqual("class Test {}");
    }
}
