package in.kyle.jrefactor.writer.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.annotationvalue.JValueSingle;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralString;
import in.kyle.jrefactor.writer.Write;

public class TestJValueSingle {
    
    @Test
    public void test() {
        JValueSingle single = new JValueSingle(new JLiteralString("value"));
        Verify.that(Write.object(single)).isEqual("\"value\"");
    }
}
