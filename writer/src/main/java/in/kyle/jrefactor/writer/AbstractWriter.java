package in.kyle.jrefactor.writer;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import in.kyle.api.utils.Try;
import in.kyle.jrefactor.tree.JObject;

public abstract class AbstractWriter extends CodeWriter {
    
    private static final String BASE_CLASS = "in.kyle.jrefactor.writer.CodeWriter";
    
    private static final Map<Class<?>, Method> METHODS;
    
    static {
        Class<?> CLAZZ = Try.to(() -> Class.forName(BASE_CLASS));
        METHODS = Arrays.stream(CLAZZ.getDeclaredMethods())
                        .filter(method -> Modifier.isProtected(method.getModifiers()))
                        .filter(method -> method.getParameterCount() == 1)
                        .collect(Collectors.toMap(method -> method.getParameterTypes()[0],
                                                  method -> method));
    }
    
    private final StringBuilder buffer = new StringBuilder();
    private final StringBuilder currentIndent = new StringBuilder();
    private String indentString = "    ";
    private boolean newLine = false;
    
    @Override
    public void write(JObject object) {
        beginWrite();
        if (object != null) {
            Method method = METHODS.get(object.getClass());
            if (method == null) {
                throw new RuntimeException("No such writer for " + object);
            } else {
                Try.to(() -> method.invoke(this, object));
            }
        }
    }
    
    protected void writeString(String string) {
        beginWrite();
        buffer.append(string);
    }
    
    private void beginWrite() {
        if (newLine) {
            appendIndent();
            newLine = false;
        }
    }
    
    
    protected void indent() {
        currentIndent.append(indentString);
    }
    
    protected void dedent() {
        currentIndent.setLength(currentIndent.length() - indentString.length());
    }
    
    protected void newLine() {
        buffer.append("\n");
        newLine = true;
    }
    
    private void appendIndent() {
        buffer.append(currentIndent.toString());
    }
    
    public void clear() {
        buffer.setLength(0);
    }
    
    @Override
    public String toString() {
        return buffer.toString();
    }
}
