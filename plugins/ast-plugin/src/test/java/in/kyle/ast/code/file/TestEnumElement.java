package in.kyle.ast.code.file;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.ast.util.StringTemplate;

public class TestEnumElement {
    
    @Test
    public void testEnumElementSimple() {
        JavaEnumElement enumElement = new JavaEnumElement("TEST");
        String result = render(enumElement);
        Verify.that(result).isEqual("TEST()");
    }
    
    @Test
    public void testEnumElementParts() {
        JavaEnumElement enumElement = new JavaEnumElement("TEST");
        enumElement.getValues().add("1");
        enumElement.getValues().add("2");
        enumElement.getValues().add("3");
        String result = render(enumElement);
        Verify.that(result).isEqual("TEST(1, 2, 3)");
    }
    
    private String render(JavaEnumElement element) {
        return StringTemplate.render("file/writeEnumElement", element);
    }
}
