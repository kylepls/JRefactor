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
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.statement.JBlock;

public final class JObjUtils {
    
    private static final Map<Class<? extends JObj>, Collection<Field>> fieldsCache =
            new HashMap<>();
    
    static <T extends JObj> T findField(JObj parent, T child) {
        for (JObj field : JObjUtils.getDirectChildren(parent)) {
            if (field == child) {
                return (T) field;
            }
        }
        throw new RuntimeException("Field not found");
    }
    
    static boolean isChild(JObj parent, JObj child) {
        if (parent != null) {
            for (JObj jObject : JObjUtils.getDirectChildren(parent)) {
                if (jObject == child) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static <T extends JObj> T clone(T object) {
        Class<?> clazz = object.getClass();
        if (!clazz.isEnum()) {
            return Try.to(() -> cloneInternal(object));
        } else {
            return object;
        }
    }
    
    private static <T extends JObj> T cloneInternal(T object)
            throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (T) ois.readObject();
    }
    
    public static JBlock getFirstUpwardBlock(JObj root, JObj object) {
        JObj search = object;
        while (!(search instanceof JBlock)) {
            search = JObjUtils.findParent(root, search);
        }
        return (JBlock) search;
    }
    
    
    public static JObj findParent(JObj search, JObj subject) {
        if (search != null) {
            if (isChild(search, subject)) {
                return search;
            } else {
                Collection<JObj> children = JObjUtils.getDirectChildren(search);
                for (JObj child : children) {
                    JObj result = findParent(child, subject);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }
    
    static Collection<JObj> getDirectChildren(JObj object) {
        Collection<Field> fields =
                fieldsCache.computeIfAbsent(object.getClass(), clazz -> getJObjFields(object));
        
        List<JObj> values = new ArrayList<>();
        for (Field field : fields) {
            Object value = Try.to(() -> field.get(object));
            if (value instanceof JObj) {
                values.add((JObj) value);
            } else if (value instanceof Collection) {
                values.addAll((Collection<? extends JObj>) value);
            }
        } return values;
    }
    
    protected static Collection<Field> getJObjFields(JObj object) {
        List<Field> fields = new ArrayList<>();
        Class<?> clazz = object.getClass();
        while (clazz.getSuperclass() != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.isEnumConstant() && !Modifier.isStatic(field.getModifiers())) {
                    if (JObj.class.isAssignableFrom(field.getType()) ||
                        Collection.class.isAssignableFrom(field.getType())) {
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
