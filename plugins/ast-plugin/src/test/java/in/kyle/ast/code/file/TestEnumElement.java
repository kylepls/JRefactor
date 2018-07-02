package in.kyle.ast.code.file;

import org.junit.Test;

import in.kyle.api.verify.Verify;

public class TestEnumElement {
    
    @Test
    public void testEnumElementSimple() {
        EnumElement enumElement = new EnumElement("TEST");
        String result = enumElement.write();
        Verify.that(result).isEqual("TEST()");
    }
    
    @Test
    public void testEnumElementParts() {
        EnumElement enumElement = new EnumElement("TEST");
        enumElement.getValues().add("1");
        enumElement.getValues().add("2");
        enumElement.getValues().add("3");
        String result = enumElement.write();
        Verify.that(result).isEqual("TEST(1, 2, 3)");
    }
}
