package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.typename.JArrayTypeName;

public class TestJArrayTypeName {

    @Test
    public void test() {
        String test = "String[]";
        JArrayTypeName name = Parser.parse(test, JArrayTypeName.class);
        Verify.that(name.getDimensions()).isEqual(1);
        Verify.that(name.getType()).isEqual("String");
    }
}