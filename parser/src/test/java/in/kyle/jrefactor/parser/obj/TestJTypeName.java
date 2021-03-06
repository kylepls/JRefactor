package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.JTypeName;

public class TestJTypeName {
    
    @Test
    public void test() {
        String test = "Test";
        JTypeName typeName = Parser.parse(test, JTypeName.class);
        Verify.that(typeName.getType()).isNotNull();
    }
    
    @Test
    public void testPackageName() {
        String test = "com.test.Test;";
        JTypeName typeName = Parser.parse(test, JTypeName.class);
        Verify.that(typeName.getType()).isEqual("Test");
        Verify.that(typeName.getArea().get())
                .isEqual(JPropertyLookup.builder().addAreas("com", "test").build());
    }
}