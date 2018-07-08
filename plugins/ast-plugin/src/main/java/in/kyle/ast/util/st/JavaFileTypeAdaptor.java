package in.kyle.ast.util.st;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

import in.kyle.ast.code.file.JavaFileType;

public class JavaFileTypeAdaptor extends ObjectModelAdaptor {
    @Override
    public synchronized Object getProperty(Interpreter interp,
                                           ST self,
                                           Object o,
                                           Object property,
                                           String propertyName) throws STNoSuchPropertyException {
        JavaFileType type = (JavaFileType) o;
        if ("isEnum".equals(propertyName)) {
            return type == JavaFileType.ENUM;
        } else if ("isClass".equals(propertyName)) {
            return type == JavaFileType.CLASS;
        } else if ("isAbstractClass".equals(propertyName)) {
            return type == JavaFileType.ABSTRACT_CLASS;
        } else if ("isInterface".equals(propertyName)) {
            return type == JavaFileType.INTERFACE;
        } else {
            return super.getProperty(interp, self, o, property, propertyName);
        }
    }
}
