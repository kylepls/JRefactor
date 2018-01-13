package in.kyle.jrefactor.writer.expression;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.expression.JAssignment;
import in.kyle.jrefactor.writer.CodeWriter;
import in.kyle.jrefactor.writer.SimpleWriter;

public class TestJAssignment {
    
    @Test
    public void testSimple() {
        String testString = "a = b";
        JAssignment testCase = Parser.parse(testString, JAssignment.class);
        CodeWriter writer = new SimpleWriter();
        writer.write(testCase);
        Verify.that(testString).isEqual(writer.toString());
    }
}
