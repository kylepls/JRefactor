package in.kyle.scanner;

import org.junit.Test;

import in.kyle.api.verify.Verify;

public class RefactorSessionTest {
    
    @Test
    public void testReplace() throws IllegalAccessException {
        TestClass test = new TestClass();
        test.a = "hello";
        RefactorSession.replaceField(test, test.a, "test");
        Verify.that(test.a).isEqual("test");
        Verify.that(test.b).isEqual("b");
    }
    
    class TestClass {
        
        private String a = "a";
        private String b = "b";
    }
}
