package in.kyle.ast.util.st;

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
            return field.getSimpleType().equals("Set") || field.getSimpleType().equals("List");
        } else if ("isOptional".equals(propertyName)) {
            return field.getSimpleType().equals("Optional");
        } else if ("isSet".equals(propertyName)) {
            return field.getSimpleType().equals("Set");
        } else if ("isList".equals(propertyName)) {
            return field.getSimpleType().equals("List");
        } else if ("isPrimitive".equals(propertyName)) {
            return Character.isLowerCase(field.getSimpleType().charAt(0));
        } else {
            return super.getProperty(interp, self, o, property, propertyName);
        }
    }
    
}
