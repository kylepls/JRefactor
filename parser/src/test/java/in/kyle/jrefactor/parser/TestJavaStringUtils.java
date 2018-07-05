package in.kyle.jrefactor.parser;

import org.junit.Test;

import in.kyle.api.verify.Verify;

public class TestJavaStringUtils {
    
    @Test
    public void testString() {
        String test = JavaStringUtils.unescapeString("test");
        Verify.that(test).isEqual("test");
    }
    
    @Test
    public void testTab() {
        String test = JavaStringUtils.unescapeString("\\t");
        Verify.that(test).isEqual("\t");
    }
    
    @Test
    public void testOctalSingle() {
        String test = JavaStringUtils.unescapeString("\\0");
        Verify.that(test).isEqual("\0");        
    }
    @Test
    public void testOctalDouble() {
        String test = JavaStringUtils.unescapeString("\\10");
        Verify.that(test).isEqual("\10");
    }
    @Test
    public void testOctalTriple() {
        String test = JavaStringUtils.unescapeString("\\100");
        Verify.that(test).isEqual("\100");
    }
    
    @Test
    public void testUnicode() {
        String test = JavaStringUtils.unescapeString("\\u2444");
        Verify.that(test).isEqual("\u2444");
    }
}
