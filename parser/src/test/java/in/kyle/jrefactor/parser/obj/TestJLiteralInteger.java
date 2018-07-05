package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralInteger;

public class TestJLiteralInteger {
    
    @Test
    public void test() {
        String test = "10";
        JLiteralInteger literal = Parser.parse(test, JLiteralInteger.class);
        Verify.that(literal.getValue()).isEqual(10);
    }
    
    @Test
    public void testHex() {
        String test = "0x1a";
        JLiteralInteger literal = Parser.parse(test, JLiteralInteger.class);
        Verify.that(literal.getValue()).isEqual(0x1a);        
    }
    
    @Test
    public void testBinary() {
        String test = "0b11";
        JLiteralInteger literal = Parser.parse(test, JLiteralInteger.class);
        Verify.that(literal.getValue()).isEqual(0b11);                
    }
    
    @Test
    public void testUnderscore() {
        String test = "1_0";
        JLiteralInteger literal = Parser.parse(test, JLiteralInteger.class);
        Verify.that(literal.getValue()).isEqual(1_0);                        
    }
}
