package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JImport;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.block.typebody.JClassBody;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.superinterfacetype
        .typeparametertype.JClass;
import in.kyle.jrefactor.writer.Write;

public class TestJCompilationUnit {
    
    @Test
    public void testJustPackage() {
        JCompilationUnit unit = new JCompilationUnit();
        unit.setPackageName(Optional.of(JPropertyLookup.builder().addAreas("pack", "age").build()));
        Verify.that(Write.object(unit)).isEqual("package pack.age;");
    }
    
    @Test
    public void testJustImports() {
        JCompilationUnit unit = new JCompilationUnit();
        JImport import1 = JImport.builder()
                .area(JPropertyLookup.builder().addAreas("Import1").build())
                .build();
        JImport import2 = JImport.builder()
                .area(JPropertyLookup.builder().addAreas("Import2").build())
                .build();
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
