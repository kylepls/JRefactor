package in.kyle.ast.util;

import org.stringtemplate.v4.ST;

import java.lang.reflect.Field;

import in.kyle.api.utils.Try;
import in.kyle.ast.code.file.WritableElement;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class Formatter {
    
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
        return Try.to(()->field.get(instance));
    }
}
