package in.kyle.jrefactor.refactor.symbol;

import org.junit.Before;
import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.refactor.symbol.IdentifierListener.Declaration;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.block.JStatementBlock;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JParameter;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JMethodHeader;
import in.kyle.jrefactor.tree.obj.unit.bodymember.typemember.enummember.classmember.JMethod;

public class TestIdentifierListener {
    
    private IdentifierListener listener;
    
    @Before
    public void setup() {
        this.listener = new IdentifierListener();
    }
    
    @Test
    public void testMethodHeader() {
        // @formatter::off
        JMethod method = JMethod.builder()
           .header(JMethodHeader.builder()
                .returns(JTypeName.of("void"))
                .identifier(JIdentifier.of("Test.java"))
                .addParameters(JParameter.builder()
                     .type(JTypeName.of("String"))
                     .name(JIdentifier.of("string"))
                     .build())
                .build())
           .body(new JStatementBlock())
           .build();
        // @formatter:on
        
        listener.enter(method);
        Verify.that(listener.getDeclarations())
              .contains(new Declaration(method.getBody(), new JIdentifier("string")));
    }
}
