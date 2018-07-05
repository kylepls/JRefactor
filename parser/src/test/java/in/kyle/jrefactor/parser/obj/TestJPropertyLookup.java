package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;

public class TestJPropertyLookup {

    @Test
    public void test() {
        String test = "test.a.b";
        JPropertyLookup lookup = Parser.parse(test, JPropertyLookup.class);
        Verify.that(lookup.getAreas()).sizeIs(3);
    }
}