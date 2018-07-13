package in.kyle.jrefactor.writer.obj;

import org.junit.Before;
import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.JField;
import in.kyle.jrefactor.tree.obj.variabledefinition.JVariable;
import in.kyle.jrefactor.writer.Write;

public class TestJField {
    
    private JField field;
    
    @Before
    public void setup() {
        field = new JField();
        field.setType(new JTypeName("String"));
        JVariable string = new JVariable(new JIdentifier("string"));
        field.addVariable(string);
    }
    
    @Test
    public void test() {
        Verify.that(Write.object(field)).isEqual("String string;");
    }
}
