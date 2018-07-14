package in.kyle.jrefactor.refactor.files.resolver.type;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.files.SourceFile;
import in.kyle.jrefactor.refactor.util.obj.JPropertyLookupUtils;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.block.typebody.JClassBody;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.superinterfacetype
        .typeparametertype.JClass;

public class TestJTypeResolver {
    
    @Test
    public void testSingle() {
        JClass aClass =
                JClass.builder().identifier(new JIdentifier("Test")).body(new JClassBody()).build();
        JCompilationUnit unit = JCompilationUnit.builder()
                .packageName(JPropertyLookupUtils.fromStrings("test"))
                .addTypes(aClass)
                .build();
        SourceContainer context = new SourceFile(unit);
        
        JPropertyLookup lookup = JPropertyLookupUtils.fromStrings("test", "Test");
        Optional<JType> resolve = new JTypeResolver(context).resolve(lookup);
        Verify.that(resolve).isPresent();
        Verify.that(resolve.get()).isSame(aClass);
    }
}
