package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.block.JStatementBlock;

public class TestJStatementBlock {

    @Test
    public void test() {
        String test = "{}";
        JStatementBlock block = Parser.parse(test, JStatementBlock.class);
        Verify.that(block.getElements()).isEmpty();
    }
    
    @Test
    public void testMore() {
        String test = "{;;;}";
        JStatementBlock block = Parser.parse(test, JStatementBlock.class);
        Verify.that(block.getElements()).sizeIs(3);
    }
}