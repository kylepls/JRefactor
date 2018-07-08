package in.kyle.ast.util;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

import in.kyle.ast.code.JavaFile;
import in.kyle.ast.code.JavaFileType;
import in.kyle.ast.code.file.JavaField;
import in.kyle.ast.code.st.ExtendedStringRenderer;
import in.kyle.ast.code.st.JavaFieldAdapter;
import in.kyle.ast.code.st.JavaFileAdaptor;
import in.kyle.ast.code.st.JavaFileTypeAdaptor;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class StringTemplate {
    
    private static final STGroupDir groupDir;
    
    static {
        groupDir = new STGroupDir("string-template");
        groupDir.registerModelAdaptor(JavaFile.class, new JavaFileAdaptor());
        groupDir.registerModelAdaptor(JavaField.class, new JavaFieldAdapter());
        groupDir.registerModelAdaptor(JavaFileType.class, new JavaFileTypeAdaptor());
        groupDir.registerRenderer(String.class, new ExtendedStringRenderer());
    }
    
    public static ST get(String template) {
        return groupDir.getInstanceOf(template);
    }
    
    public static String render(String template, Object object) {
        ST st = groupDir.getInstanceOf(template);
        return render(st, object);
    }
    
    public static String render(ST st, Object object) {
        st.add("obj", object);
        return st.render();
    }
}
