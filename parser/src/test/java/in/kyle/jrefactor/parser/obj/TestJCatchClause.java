package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.modifiable.JCatchClause;

public class TestJCatchClause {

    @Test
    public void test() {
        String test = "catch (Exception e) {}";
        JCatchClause catchClause = Parser.parse(test, JCatchClause.class);
        Verify.that(catchClause.getBlock()).isNotNull();
        Verify.that(catchClause.getCatchTypes()).sizeIs(1);
        Verify.that(catchClause.getVariable()).isNotNull();
    }
}