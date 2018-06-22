package in.kyle.ast.util;

import org.junit.Test;

import in.kyle.api.verify.Verify;

public class FormatterTest {
    
    @Test
    public void testFormatObject() {
        AnObject object = new AnObject();
        String result = Formatter.format("{value}", object);
        Verify.that(result).isEqual("Hello World");
    }
    
    class AnObject {
        private String value = "Hello World";
    }
}
