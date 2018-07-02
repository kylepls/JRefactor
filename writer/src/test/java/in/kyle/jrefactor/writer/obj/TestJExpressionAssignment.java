package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.expression.JExpressionAssignment;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import in.kyle.jrefactor.writer.Write;

public class TestJExpressionAssignment {
    
    @Test
    public void testAssignmentOperator() {
        Verify.that(Write.object(JExpressionAssignment.JAssignmentOperator.ADD)).isEqual("+=");
    }
    
    @Test
    public void test() {
        JExpressionAssignment assignment = new JExpressionAssignment();
        assignment.setOperator(JExpressionAssignment.JAssignmentOperator.ADD);
        assignment.setLeft(new JExpressionName(new JIdentifier("test")));
        assignment.setRight(new JExpressionName(new JIdentifier("test")));
        Verify.that(Write.object(assignment)).isEqual("test += test");
    }
}
