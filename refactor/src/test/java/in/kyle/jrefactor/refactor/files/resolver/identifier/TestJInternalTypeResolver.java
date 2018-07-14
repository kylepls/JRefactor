package in.kyle.jrefactor.refactor.files.resolver.identifier;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.refactor.files.resolver.type.JInternalTypeResolver;
import in.kyle.jrefactor.refactor.util.obj.JPropertyLookupUtils;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.block.typebody.JClassBody;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.superinterfacetype
        .typeparametertype.JClass;

public class TestJInternalTypeResolver {
    
    @Test
    public void test() {
        JCompilationUnit unit = new JCompilationUnit();
        JClass last = null;
        for (int i = 0; i < 3; i++) {
            JClass aClass = JClass.builder()
                    .identifier(new JIdentifier("Test" + (i + 1)))
                    .body(new JClassBody())
                    .build();
            if (last != null) {
                last.getBody().addElement(aClass);
            } else {
                unit.addType(aClass);
            }
            last = aClass;
        }
        
        JPropertyLookup lookup = JPropertyLookupUtils.fromStrings("Test1", "Test2", "Test3");
        JType start = unit.getTypes().get(0);
        Optional<JType> resolve = new JInternalTypeResolver(start).resolve(lookup);
        Verify.that(resolve).isPresent();
    }
}
