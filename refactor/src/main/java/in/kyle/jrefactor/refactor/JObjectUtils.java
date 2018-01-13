package in.kyle.jrefactor.refactor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.kyle.api.utils.Try;
import in.kyle.jrefactor.tree.JObject;
import in.kyle.jrefactor.tree.JObjectList;
import in.kyle.jrefactor.tree.statement.JBlock;

public final class JObjectUtils {
    
    private static final Map<Class<? extends JObject>, Collection<Field>> fieldsCache =
            new HashMap<>();
    
    static <T extends JObject> T findField(JObject parent, T child) {
        for (JObject field : JObjectUtils.getDirectChildren(parent)) {
            if (field == child) {
                return (T) field;
            }
        }
        throw new RuntimeException("Field not found");
    }
    
    static boolean isChild(JObject parent, JObject child) {
        if (parent != null) {
            for (JObject jObject : JObjectUtils.getDirectChildren(parent)) {
                if (jObject == child) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static <T extends JObject> T clone(T object) {
        Class<?> clazz = object.getClass();
        if (!clazz.isEnum()) {
            return Try.to(()->cloneInternal(object));
        } else {
            return object;
        }
    }
    
    private static <T extends JObject> T cloneInternal(T object)
            throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (T) ois.readObject();
    }
    
    public static JBlock getFirstUpwardBlock(JObject root, JObject object) {
        JObject search = object;
        while (!(search instanceof JBlock)) {
            search = JObjectUtils.findParent(root, search);
        }
        return (JBlock) search;
    }
    
    
    public static JObject findParent(JObject search, JObject subject) {
        if (search != null) {
            if (isChild(search, subject)) {
                return search;
            } else {
                Collection<JObject> children = JObjectUtils.getDirectChildren(search);
                for (JObject child : children) {
                    JObject result = findParent(child, subject);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }
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
                if (!field.isEnumConstant() && !Modifier.isStatic(field.getModifiers())) {
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
