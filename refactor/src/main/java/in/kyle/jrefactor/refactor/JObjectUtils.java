package in.kyle.jrefactor.refactor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.kyle.api.utils.Try;
import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.JObjectList;

final class JObjectUtils {
    
    private static final Map<Class<? extends JObject>, Collection<Field>> fieldsCache =
            new HashMap<>();
    
    static Collection<JObject> getDirectChildren(JObject object) {
        Collection<Field> fields =
                fieldsCache.computeIfAbsent(object.getClass(), clazz -> getJObjectFields(object));
        
        if (object instanceof JObjectList) {
            return ((JObjectList<JObject>) object);
        } else {
            List<JObject> values = new ArrayList<>();
            for (Field field : fields) {
                JObject value = (JObject) Try.to(() -> field.get(object));
                values.add(value);
            }
            return values;
        }
    }
    
    protected static <T extends JObject> Collection<Field> getJObjectFields(T object) {
        List<Field> fields = new ArrayList<>();
        Class<?> clazz = object.getClass();
        while (clazz.getSuperclass() != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.isEnumConstant()) {
                    if (JObject.class.isAssignableFrom(field.getType())) {
                        field.setAccessible(true);
                        fields.add(field);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
