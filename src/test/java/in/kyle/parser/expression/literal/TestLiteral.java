package in.kyle.parser.expression.literal;

import org.junit.Test;

import java.io.IOException;

import in.kyle.antlr.gen.Java8Parser;
import in.kyle.api.verify.Verify;
import in.kyle.parser.Parser;

public class TestLiteral {
    
    @Test
    public void testBooleanLiteral() throws IOException {
        String literalString = "false";
        JBooleanLiteral literal = Parser.parse(literalString, Java8Parser::literal);
        Verify.that(literal.getValue()).isEqual(Boolean.parseBoolean(literalString));
    }
    
    @Test
    public void testLongLiteral() throws IOException {
        String literalString = "1L";
        JLiteral literal = Parser.parse(literalString, Java8Parser::literal);
        Verify.that(literal.getValue())
              .isEqual(Long.parseLong(literalString.substring(0, literalString.length() - 1)));
    }
    
    @Test
    public void testIntegerLiteral() throws IOException {
        String literalString = "100";
        JIntegerLiteral literal = Parser.parse(literalString, Java8Parser::literal);
        Verify.that(literal.getValue()).isEqual(Integer.parseInt(literalString));
    }
    
    @Test
    public void testDoubleLiteral() throws IOException {
        String literalString = "100.0D";
        JDoubleLiteral literal = Parser.parse(literalString, Java8Parser::literal);
        Verify.that(literal.getValue()).isEqual(Double.parseDouble(literalString));
    }
    
    @Test
    public void testCharacterLiteral() throws IOException {
        String literalString = "'a'";
        JCharacterLiteral literal = Parser.parse(literalString, Java8Parser::literal);
        Verify.that(literal.getValue()).isEqual('a');
    }
    
    @Test
    public void testStringLiteral() throws IOException {
        String literalString = "\"asdf\"";
        JStringLiteral literal = Parser.parse(literalString, Java8Parser::literal);
        Verify.that(literal.getValue()).isEqual("asdf");
    }
    
    @Test
    public void testFloatLiteral() throws IOException {
        String literalString = "1.0F";
        JFloatLiteral literal = Parser.parse(literalString, Java8Parser::literal);
        Verify.that(literal.getValue()).isEqual(Float.parseFloat(literalString));
    }
}
