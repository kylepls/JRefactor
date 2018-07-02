package in.kyle.jrefactor.refactor;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLiteral;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralInteger;
import lombok.Data;
import lombok.ToString;

public class TestJObjUtils {
    
    @Test
    public void testGetFieldListValues() {
        @Data
        class A implements JObj {
            private List<JExpressionLiteral> list = new ArrayList<>();
        }
        A a = new A();
        a.list.add(new JLiteralInteger(1));
        
        Collection<JObj> directChildren = JObjUtils.getDirectChildren(a);
        Verify.that(directChildren).sizeIs(1);
        Verify.that(directChildren.iterator().next()).isInstanceOf(JLiteralInteger.class);
    }
    
    @Test
    public void testGetObjectValues() {
        @ToString
        class A implements JObj {
            private JObj test = new JLiteralInteger(1);
        }
        A testObject = new A();
        Collection<JObj> values = JObjUtils.getDirectChildren(testObject);
        Verify.that(values).sizeIs(1);
        Verify.that(values.iterator().next()).isInstanceOf(JLiteralInteger.class);
    }
}
