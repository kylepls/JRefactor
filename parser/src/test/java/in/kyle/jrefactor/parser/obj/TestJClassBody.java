package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.block.typebody.JClassBody;

public class TestJClassBody {
    
    @Test
    public void testInitializer() {
        String test = "{}";
        JClassBody body = Parser.parse(test, JClassBody.class);
        Verify.that(body.getElements()).sizeIs(0);
    }
    
    @Test
    public void testInstanceInitializer() {
        String test = "{ {} }";
        JClassBody body = Parser.parse(test, JClassBody.class);
        Verify.that(body.getElements()).sizeIs(1);        
    }
}
