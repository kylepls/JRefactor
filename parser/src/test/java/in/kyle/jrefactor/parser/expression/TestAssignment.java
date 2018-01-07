package in.kyle.jrefactor.parser.expression;

import org.junit.Test;

import java.io.IOException;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.parser.antlr.gen.Java8Parser;

public class TestAssignment {
    
    @Test
    public void testEqualityAssignment() throws IOException {
        String expressionString = "i = 1";
        JAssignment assignment = Parser.parse(expressionString, Java8Parser::expression);
        Verify.that(assignment.getOperator()).isEqual(JAssignment.Operator.EQUAL);
        Verify.that(assignment.getLeft()).isNotNull();
        Verify.that(assignment.getRight()).isNotNull();
    }
}