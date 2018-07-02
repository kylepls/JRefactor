package in.kyle.jrefactor.writer.adaptors;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

import java.util.Optional;

public class OptionalAdaptor extends ObjectModelAdaptor {
    @Override
    public synchronized Object getProperty(Interpreter interp,
                                           ST self,
                                           Object o,
                                           Object property,
                                           String propertyName)
            throws STNoSuchPropertyException {
        Optional optional = (Optional) o;
        if (propertyName.equals("get")) {
            return optional.get();
        } else if (propertyName.equals("present")) {
            return optional.isPresent();
        } else {
            return super.getProperty(interp, self, o, property, propertyName);
        }
    }
}
