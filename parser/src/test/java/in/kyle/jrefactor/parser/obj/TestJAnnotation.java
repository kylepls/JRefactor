package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.JAnnotation;

public class TestJAnnotation {
    
    @Test
    public void test() {
        String test = "@Data";
        JAnnotation annotation = Parser.parse(test, JAnnotation.class);
        Verify.that(annotation.getType().getType()).isEqual("Data");
        Verify.that(annotation.getValues()).isEmpty();
    }
    
    @Test
    public void testValue() {
        String test = "@Data(\"test\")";
        JAnnotation annotation = Parser.parse(test, JAnnotation.class);
        Verify.that(annotation.getValues()).sizeIs(1);
    }
}