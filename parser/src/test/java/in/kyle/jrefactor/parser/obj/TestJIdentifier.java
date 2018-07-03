package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.JIdentifier;

public class TestJIdentifier {

    @Test
    public void test() {
        String test = "id";
        JIdentifier identifier = Parser.parse(test, JIdentifier.class);
        Verify.that(identifier.getName()).isNotNull();
    }
}