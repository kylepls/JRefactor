package in.kyle.jrefactor.parser.expression.literal;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.expression.literal.*;

public class TestLiteral {
    
    @Test
    public void testBooleanLiteral() {
        String literalString = "false";
        JBooleanLiteral literal = Parser.parse(literalString, JBooleanLiteral.class);
        Verify.that(literal.getValue()).isEqual(Boolean.parseBoolean(literalString));
    }
    
    @Test
    public void testLongLiteral() {
        String literalString = "1L";
        JLiteral literal = Parser.parse(literalString, JLiteral.class);
        Verify.that(literal.getValue())
              .isEqual(Long.parseLong(literalString.substring(0, literalString.length() - 1)));
    }
    
    @Test
    public void testIntegerLiteral() {
        String literalString = "100";
        JIntegerLiteral literal = Parser.parse(literalString, JIntegerLiteral.class);
        Verify.that(literal.getValue()).isEqual(Integer.parseInt(literalString));
    }
    
    @Test
    public void testDoubleLiteral() {
        String literalString = "100.0D";
        JDoubleLiteral literal = Parser.parse(literalString, JDoubleLiteral.class);
        Verify.that(literal.getValue()).isEqual(Double.parseDouble(literalString));
    }
    
    @Test
    public void testCharacterLiteral() {
        String literalString = "'a'";
        JCharacterLiteral literal = Parser.parse(literalString, JCharacterLiteral.class);
        Verify.that(literal.getValue()).isEqual('a');
    }
    
    @Test
    public void testStringLiteral() {
        String literalString = "\"asdf\"";
        JStringLiteral literal = Parser.parse(literalString, JStringLiteral.class);
        Verify.that(literal.getValue()).isEqual("asdf");
    }
    
    @Test
    public void testFloatLiteral() {
        String literalString = "1.0F";
        JFloatLiteral literal = Parser.parse(literalString, JFloatLiteral.class);
        Verify.that(literal.getValue()).isEqual(Float.parseFloat(literalString));
    }
}
