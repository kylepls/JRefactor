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
        file.registerRenderer(Integer.class, this::renderInt);
        file.registerModelAdaptor(Optional.class, new OptionalAdaptor());
        String templateName = obj.getClass().getSimpleName();
        ST st = file.getInstanceOf(templateName);
        if (st != null) {
            st.add("obj", obj);
            return st.render();
        } else {
            throw new RuntimeException("Failed to find template for " + templateName);
        }
    }
    
    private String renderInt(Object o, String format, Locale locale) {
        if ("array".equals(format)) {
            StringBuilder builder = new StringBuilder();
            int i = (int) o;
            for (int j = 0; j < i; j++) {
                builder.append("[]");
            }
            return builder.toString();
        } else {
            return o.toString();
        }
    }
    
    private String renderJobj(Object o, String formatString, Locale locale) {
        String write = write((JObj) o);
        if (write.isEmpty()) {
            throw new RuntimeException("Could not write " + o + " writer returned empty string");
        }
        if (formatString != null && formatString.contains("%")) {
            return String.format(formatString, write);
        } else {
            return write;
        }
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
