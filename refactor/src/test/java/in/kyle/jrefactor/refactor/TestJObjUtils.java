package in.kyle.jrefactor.refactor;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.refactor.util.JObjUtils;
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
    
            @Override
            public List<Object> getDirectChildren() {
                return new ArrayList<>(list);
            }
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
    
            @Override
            public List<Object> getDirectChildren() {
                return Collections.singletonList(test);
            }
        }
        A testObject = new A();
        Collection<JObj> values = JObjUtils.getDirectChildren(testObject);
        Verify.that(values).sizeIs(1);
        Verify.that(values.iterator().next()).isInstanceOf(JLiteralInteger.class);
    }
    
    @Test
    public void testGetElementsRecursive() {
        class A implements JObj {
            class B implements JObj {
                class C implements JObj {
                }
                
                C c = new C();
    
                @Override
                public List<Object> getAllChildren() {
                    return Collections.singletonList(c);
                }
            }
            
            B b = new B();
            
            @Override
            public List<Object> getAllChildren() {
                return Collections.singletonList(b);
            }
        }
        
        A a = new A();
        List<JObj> children = JObjUtils.getAllElements(a);
        Verify.that(children).sizeIs(3);
    }
}
