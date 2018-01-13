package in.kyle.jrefactor.refactor;

import org.junit.Test;

import java.util.Collection;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.JObject;
import in.kyle.jrefactor.tree.JObjectList;
import in.kyle.jrefactor.tree.expression.literal.JIntegerLiteral;
import in.kyle.jrefactor.tree.expression.literal.JLiteral;
import lombok.Data;
import lombok.ToString;

public class TestJObjectUtils {
    
    @Test
    public void testGetFieldListValues() {
        @Data
        class A implements JObject {
            private JObjectList<JLiteral> list = new JObjectList<>();
        }
        A a = new A();
        a.list.add(new JIntegerLiteral(1));
    
        Collection<JObject> directChildren = JObjectUtils.getDirectChildren(a);
        Verify.that(directChildren).sizeIs(1);
        Verify.that(directChildren.iterator().next()).isInstanceOf(JObjectList.class);
    }
    
    @Test
    public void testGetObjectValues() {
        @ToString
        class A implements JObject {
            private JObject test = new JIntegerLiteral(1);
        }
        A testObject = new A();
        Collection<JObject> values = JObjectUtils.getDirectChildren(testObject);
        Verify.that(values).sizeIs(1);
        Verify.that(values.iterator().next()).isInstanceOf(JIntegerLiteral.class);
    }
    
    @Test
    public void testGetListValues() {
        @ToString
        class A implements JObject {
            private JObject value = new JIntegerLiteral(1);
        }
        JObjectList<JObject> list = new JObjectList<>();
        list.add(new A());
        Collection<JObject> listValues = JObjectUtils.getDirectChildren(list);
        Verify.that(listValues).sizeIs(1);
        Verify.that(listValues.iterator().next()).isInstanceOf(A.class);
    }
}
