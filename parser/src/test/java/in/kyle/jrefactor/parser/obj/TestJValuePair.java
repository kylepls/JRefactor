package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.annotationvalue.JValuePair;

public class TestJValuePair {

    @Test
    public void test() {
        String test = "test = 1";
        JValuePair valuePair = Parser.parse(test, JValuePair.class);
        Verify.that(valuePair.getIdentifier()).isNotNull();
        Verify.that(valuePair.getValue()).isNotNull();
    }
}