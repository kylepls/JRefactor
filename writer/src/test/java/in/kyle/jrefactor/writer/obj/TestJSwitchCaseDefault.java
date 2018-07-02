package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.switchcase.JSwitchCaseDefault;
import in.kyle.jrefactor.writer.Write;

public class TestJSwitchCaseDefault {
    
    @Test
    public void test() {
        JSwitchCaseDefault switchCase = new JSwitchCaseDefault();
        Verify.that(Write.object(switchCase)).isEqual("default:");
    }
}
