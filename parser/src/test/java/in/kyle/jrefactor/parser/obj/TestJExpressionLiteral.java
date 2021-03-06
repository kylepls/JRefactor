package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLiteral;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralBoolean;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralCharacter;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralString;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralInteger;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.literalfloating
        .JLiteralDouble;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.literalfloating
        .JLiteralFloat;

public class TestJExpressionLiteral {
    
    @Test
    public void testBooleanLiteral() {
        String literalString = "false";
        JLiteralBoolean literal = Parser.parse(literalString, JLiteralBoolean.class);
        Verify.that(literal.getValue()).isEqual(Boolean.parseBoolean(literalString));
    }
    
    @Test
    public void testCharacterLiteral() {
        String literalString = "'a'";
        JLiteralCharacter literal = Parser.parse(literalString, JLiteralCharacter.class);
        Verify.that(literal.getValue()).isEqual('a');
    }
    
    @Test
    public void testDoubleLiteral() {
        String literalString = "100.0D";
        JLiteralDouble literal = Parser.parse(literalString, JLiteralDouble.class);
        Verify.that(literal.getValue()).isEqual(Double.parseDouble(literalString));
    }
    
    @Test
    public void testFloatLiteral() {
        String literalString = "1.0F";
        JLiteralFloat literal = Parser.parse(literalString, JLiteralFloat.class);
        Verify.that(literal.getValue()).isEqual(Float.parseFloat(literalString));
    }
    
    @Test
    public void testIntegerLiteral() {
        String literalString = "100";
        JLiteralInteger literal = Parser.parse(literalString, JLiteralInteger.class);
        Verify.that(literal.getValue()).isEqual(Integer.parseInt(literalString));
    }
    
    @Test
    public void testLongLiteral() {
        String literalString = "1L";
        JExpressionLiteral literal = Parser.parse(literalString, JExpressionLiteral.class);
        Verify.that(literal.getValue())
              .isEqual(Long.parseLong(literalString.substring(0, literalString.length() - 1)));
    }
    
    @Test
    public void testStringLiteral() {
        String literalString = "\"asdf\"";
        JLiteralString literal = Parser.parse(literalString, JLiteralString.class);
        Verify.that(literal.getValue()).isEqual("asdf");
    }
}
