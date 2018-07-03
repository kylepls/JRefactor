package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JField;

public class TestJField {
    
    @Test
    public void test() {
        String text = "int i;";
        JField field = Parser.parse(text, JField.class);
        Verify.that(field.getVariables()).sizeIs(1);
        Verify.that(field.getType()).isNotNull();
    }
    
    @Test
    public void testVariables() {
        String text = "int i, j, k;";
        JField field = Parser.parse(text, JField.class);
        Verify.that(field.getVariables()).sizeIs(3);
    }
}