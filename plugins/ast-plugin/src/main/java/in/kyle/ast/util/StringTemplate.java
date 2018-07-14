package in.kyle.ast.util;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

import in.kyle.api.utils.Conditions;
import in.kyle.ast.code.file.JavaField;
import in.kyle.ast.code.file.JavaFile;
import in.kyle.ast.code.file.JavaFileHeader;
import in.kyle.ast.code.file.JavaFileType;
import in.kyle.ast.util.st.ExtendedStringRenderer;
import in.kyle.ast.util.st.JavaFieldAdapter;
import in.kyle.ast.util.st.JavaFileAdaptor;
import in.kyle.ast.util.st.JavaFileTypeAdaptor;
import in.kyle.ast.util.st.JavaFileHeaderAdaptor;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class StringTemplate {
    
    private static final STGroupDir groupDir;
    
    static {
        groupDir = new STGroupDir("string-template");
        groupDir.registerModelAdaptor(JavaFile.class, new JavaFileAdaptor());
        groupDir.registerModelAdaptor(JavaField.class, new JavaFieldAdapter());
        groupDir.registerModelAdaptor(JavaFileType.class, new JavaFileTypeAdaptor());
        groupDir.registerModelAdaptor(JavaFileHeader.class, new JavaFileHeaderAdaptor());
        groupDir.registerRenderer(String.class, new ExtendedStringRenderer());
    }
    
    public static ST get(String template) {
        ST st = groupDir.getInstanceOf("/" + template);
        Conditions.notNull(st, "Template {} not found", template);
        return st;
    }
    
    public static String render(String template, Object object) {
        ST st = get(template);
        return render(st, object);
    }
    
    public static String render(ST st, Object object) {
        st.add("obj", object);
        return st.render().replace("\r", "");
    }
}
