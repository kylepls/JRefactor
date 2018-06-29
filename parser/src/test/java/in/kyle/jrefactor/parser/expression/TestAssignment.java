package in.kyle.jrefactor.parser.expression;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.JExpressionAssignment;

public class TestAssignment {
    
    @Test
    public void testEqualityAssignment() {
        String expressionString = "i = 1";
        JExpressionAssignment assignment = Parser.parse(expressionString, JExpressionAssignment.class);
        Verify.that(assignment.getOperator()).isEqual(JExpressionAssignment.JAssignmentOperator.EQUAL);
        Verify.that(assignment.getLeft()).isNotNull();
        Verify.that(assignment.getRight()).isNotNull();
    }
}
