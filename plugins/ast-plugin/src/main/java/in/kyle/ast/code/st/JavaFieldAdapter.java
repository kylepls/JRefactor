package in.kyle.ast.code.st;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

import in.kyle.ast.code.file.JavaField;

public class JavaFieldAdapter extends ObjectModelAdaptor {
    
    @Override
    public synchronized Object getProperty(Interpreter interp,
                                           ST self,
                                           Object o,
                                           Object property,
                                           String propertyName) throws STNoSuchPropertyException {
        JavaField field = (JavaField) o;
        if ("isCollection".equals(propertyName)) {
            return field.getType().equals("Set") || field.getType().equals("List");
        } else if ("isOptional".equals(propertyName)) {
            return field.getType().equals("Optional");
        }
        return super.getProperty(interp, self, o, property, propertyName);
    }
}
