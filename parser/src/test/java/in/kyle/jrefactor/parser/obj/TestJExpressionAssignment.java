package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.JExpressionAssignment;
import in.kyle.jrefactor.tree.obj.expression.JExpressionAssignment.JAssignmentOperator;

public class TestJExpressionAssignment {
    
    @Test
    public void test() {
        String expressionString = "i = 1";
        JExpressionAssignment assignment =
                Parser.parse(expressionString, JExpressionAssignment.class);
        Verify.that(assignment.getOperator()).isEqual(JAssignmentOperator.EQUAL);
        Verify.that(assignment.getLeft()).isNotNull();
        Verify.that(assignment.getRight()).isNotNull();
    }
}