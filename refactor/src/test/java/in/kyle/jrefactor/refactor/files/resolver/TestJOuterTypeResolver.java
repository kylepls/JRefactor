package in.kyle.jrefactor.refactor.files.resolver;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.files.SourceFile;
import in.kyle.jrefactor.refactor.files.resolver.type.JOuterTypeResolver;
import in.kyle.jrefactor.refactor.util.obj.JPropertyLookupUtils;
import in.kyle.jrefactor.refactor.util.obj.JTypeNameUtils;
import in.kyle.jrefactor.refactor.util.obj.JTypeNames;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.block.typebody.JClassBody;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JField;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.type.superinterfacetype.typeparametertype.JClass;
import in.kyle.jrefactor.tree.obj.variabledefinition.JVariable;

public class TestJOuterTypeResolver {
    
    @Test
    public void testInternalElement() {
        // @formatter:off
        JVariable variable = JVariable.builder().identifier(new JIdentifier("VAR")).build();
        JField field = JField.builder().type(JTypeNames.BOOLEAN).addVariables(variable).build();
        JClass aClass = JClass.builder()
                .identifier(new JIdentifier("Test"))
                .body(JClassBody.builder().addElements(field).build())
                .build();
        
        JCompilationUnit unit = JCompilationUnit.builder().addTypes(aClass).build();
        // @formatter:on
    
        SourceContainer container = new SourceFile(unit);
        JTypeName resolve = new JOuterTypeResolver(container).resolve(variable);
        Verify.that(JTypeNameUtils.toPropertyLookup(resolve))
                .isEqual(JPropertyLookupUtils.fromStrings("Test"));
    }
}
