package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import in.kyle.jrefactor.tree.obj.expression.expressionunary.JUnaryCast;
import in.kyle.jrefactor.writer.Write;

public class TestJUnaryCast {
    
    @Test
    public void test() {
        JUnaryCast cast = new JUnaryCast(new JExpressionName(new JIdentifier("test")));
        cast.addBound(new JTypeName("int"));
        Verify.that(Write.object(cast)).isEqual("(int) test");
    }
    
    @Test
    public void testBounds() {
        JUnaryCast cast = new JUnaryCast(new JExpressionName(new JIdentifier("test")));
        cast.addBound(new JTypeName("int"));
        cast.addBound(new JTypeName("long"));
        Verify.that(Write.object(cast)).isEqual("(int & long) test");        
    }
}
