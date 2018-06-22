package in.kyle.ast.util;

import org.stringtemplate.v4.ST;

import java.lang.reflect.Field;

import in.kyle.api.utils.Try;
import in.kyle.ast.code.file.WritableElement;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class Formatter {
    
    public static String format(String string, Object instance) {
        for (Field field : instance.getClass().getDeclaredFields()) {
            String key = "{" + field.getName() + "}";
            Object value = getFieldValue(instance, field);
            string = string.replace(key, value.toString());
        }
        return string;
    }
    
    public static String fromTemplate(String template, Object object, Object... kv) {
        String templateString =
                Try.to(() -> ResourceUtils.loadResource("string-template/" + template + ".st"));
        ST instance = new ST(templateString);
        for (Field field : object.getClass().getDeclaredFields()) {
            Object value = getFieldValue(object, field);
            if (value instanceof WritableElement) {
                value = ((WritableElement) value).write();
            }
            try {
                instance.add(field.getName(), value);
            } catch (IllegalArgumentException ignored) {
            }
        }
        for (int i = 0; i < kv.length; i += 2) {
            instance.add((String) kv[i], kv[i + 1]);
        }
        return instance.render();
    }
    
    private static Object getFieldValue(Object instance, Field field) {
        field.setAccessible(true);
        try {
            return field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String format(String string, Object... kv) {
        for (int i = 0; i < kv.length; i += 2) {
            String key = "{" + kv[i] + "}";
            Object value = kv[i + 1];
            string = string.replace(key, value.toString());
        }
        return string;
    }
    
    public static void append(StringBuilder builder, String format, String... parameters) {
        builder.append(String.format(format, (Object[]) parameters));
    }
    
    public static String indent(String string) {
        return string.replaceAll("^", "    ");
    }
}
