package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.expression.JExpressionAssignment.JAssignmentOperator;

public class TestJAssignmentOperator {
    
    @Test
    public void test() {
        String test = "=";
        JAssignmentOperator operator = Parser.parse(test, JAssignmentOperator.class);
        Verify.that(operator).isEqual(JAssignmentOperator.EQUAL);
    }
}