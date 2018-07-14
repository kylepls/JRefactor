package in.kyle.ast.util.st;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

import in.kyle.ast.code.file.JavaFileHeader;

public class JavaFileHeaderAdaptor extends ObjectModelAdaptor {
    
    @Override
    public synchronized Object getProperty(Interpreter interp,
                                           ST self,
                                           Object o,
                                           Object property,
                                           String propertyName) throws STNoSuchPropertyException {
        if ("genericDefine".equals(propertyName)) {
            JavaFileHeader header = (JavaFileHeader) o;
            String genericDefine = header.getGenericDefine();
            if (genericDefine != null && genericDefine.length() != 1) {
                return "T extends " + header.getGenericDefine();
            }
        }
        return super.getProperty(interp, self, o, property, propertyName);
    }
}
