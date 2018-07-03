package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JParameter;

public class TestJParameter {

    @Test
    public void test() {
        String test = "String string";
        JParameter parameter = Parser.parse(test, JParameter.class);
        Verify.that(parameter.getName()).isNotNull();
        Verify.that(parameter.getType()).isNotNull();
    }
}