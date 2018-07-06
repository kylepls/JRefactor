package in.kyle.ast.util;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.StringRenderer;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;

import in.kyle.api.utils.Try;
import in.kyle.ast.code.file.WritableElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class Formatter {
    
    public static String fromTemplate(String template, Object object) {
        return fromTemplate(template, object, Collections.emptySet());
    }
    
    public static String fromTemplate(String template,
                                      Object object,
                                      Collection<KV<String, Object>> kvs) {
        String templateString =
                Try.to(() -> ResourceUtils.loadResource("string-template/" + template + ".st"));
        ST instance = new ST(templateString);
        instance.groupThatCreatedThisInstance.registerRenderer(String.class, new StringRenderer());
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
        for (KV<String, Object> kv : kvs) {
            instance.add(kv.getKey(), kv.getValue());
        }
        return instance.render();
    }
    
    private static Object getFieldValue(Object instance, Field field) {
        field.setAccessible(true);
        return Try.to(() -> field.get(instance));
    }
    
    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class KV<K, V> {
        private final K key;
        private final V value;
        
        public static <K, V> KV<K, V> of(K k, V v) {
            return new KV<>(k, v);
        }
    }
}
