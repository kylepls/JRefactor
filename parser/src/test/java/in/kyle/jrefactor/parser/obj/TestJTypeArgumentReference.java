package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.reference.typeargument.JTypeArgumentReference;

public class TestJTypeArgumentReference {

    @Test
    public void test() {
        String test = "String";
        JTypeArgumentReference reference = Parser.parse(test, JTypeArgumentReference.class);
        Verify.that(reference.getReference()).isNotNull();
    }
}