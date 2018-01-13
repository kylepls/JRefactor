package in.kyle.jrefactor.parser.expression;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.expression.JAssignment;

public class TestAssignment {
    
    @Test
    public void testEqualityAssignment() {
        String expressionString = "i = 1";
        JAssignment assignment = Parser.parse(expressionString, JAssignment.class);
        Verify.that(assignment.getOperator()).isEqual(JAssignment.JAssignmentOperator.EQUAL);
        Verify.that(assignment.getLeft()).isNotNull();
        Verify.that(assignment.getRight()).isNotNull();
    }
}
