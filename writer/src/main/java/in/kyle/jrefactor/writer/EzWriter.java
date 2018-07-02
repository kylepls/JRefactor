package in.kyle.jrefactor.writer;

import org.stringtemplate.v4.AttributeRenderer;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.util.Locale;
import java.util.Optional;

import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.writer.adaptors.OptionalAdaptor;

public class EzWriter {
    
    private final STGroupFile file = new STGroupFile("string-template/SimpleWriter.stg");
    
    public String write(JObj obj) {
        file.registerRenderer(JObj.class, this::renderJobj);
        file.registerRenderer(Optional.class, this::renderOptional);
        file.registerModelAdaptor(Optional.class, new OptionalAdaptor());
        ST st = file.getInstanceOf(obj.getClass().getSimpleName());
        st.add("obj", obj);
        return st.render();
    }
    
    private String renderJobj(Object o, String formatString, Locale locale) {
        String write = write((JObj) o);
        if (write.isEmpty()) {
            throw new RuntimeException("Could not write " + o + " writer returned empty string");
        }
        return write;
    }
    
    private String renderOptional(Object o, String formatString, Locale locale) {
        Optional optional = (Optional) o;
        if (optional.isPresent()) {
            return render(optional.get()).trim();
        } else {
            return "";
        }
    }
    
    private String render(Object o) {
        AttributeRenderer renderer = file.getAttributeRenderer(o.getClass());
        if (renderer != null) {
            return renderer.toString(o, null, Locale.getDefault()).trim();
        } else {
            return o.toString().trim();
        }
    }
}
