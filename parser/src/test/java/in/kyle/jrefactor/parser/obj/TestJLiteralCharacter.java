package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralCharacter;

public class TestJLiteralCharacter {
    
    @Test
    public void test() {
        String test = "'a'";
        JLiteralCharacter character = Parser.parse(test, JLiteralCharacter.class);
        Verify.that(character.getValue()).isEqual('a');
    }
    
    @Test
    public void testEscape() {
        String test = "'\\0'";
        JLiteralCharacter character = Parser.parse(test, JLiteralCharacter.class);
        Verify.that(character.getValue()).isEqual('\0');        
    }
    
    @Test
    public void testUnicode() {
        String test = "'\\u2555'";
        JLiteralCharacter character = Parser.parse(test, JLiteralCharacter.class);
        Verify.that(character.getValue()).isEqual('\u2555');                
    }
}
