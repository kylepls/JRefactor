package in.kyle.jrefactor.refactor.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import in.kyle.api.utils.Conditions;
import in.kyle.api.utils.Try;
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.JBlock;

public final class JObjUtils {
    
    static boolean isChild(JObj parent, JObj child) {
        for (JObj jObject : JObjUtils.getAllChildren(parent)) {
            if (jObject == child) {
                return true;
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
    
    public static JBlock getFirstUpwardBlock(JObj root, JObj object) {
        Conditions.notNull(root);
        Conditions.notNull(object);
        JObj search = object;
        while (!(search instanceof JBlock) && search != null) {
            search = JObjUtils.findParent(search, root);
        }
        if (search != null) {
            return (JBlock) search;
        } else {
            throw new RuntimeException("No upward block found for " + object);
        }
    }
    
    public static JObj findParent(JObj child, JObj tree) {
        if (isChild(tree, child)) {
            return tree;
        } else {
            List<JObj> allChildren = getAllChildren(tree);
            Optional<JObj> parent = allChildren.stream()
                                               .map(sub -> findParent(child, sub))
                                               .filter(Objects::nonNull)
                                               .findAny();
            if (parent.isPresent()) {
                return parent.get();
            }
        }
        return null;
    }
    
    public static List<JObj> getAllChildren(JObj obj) {
        return JObjUtilsStreams.getAllChildren(obj).collect(Collectors.toList());
    }
    
    public static List<JObj> getDirectChildren(JObj obj) {
        return JObjUtilsStreams.getDirectChildren(obj).collect(Collectors.toList());
    }
    
    public static List<JObj> getAllElements(JObj obj) {
        return JObjUtilsStreams.getAllElements(obj).collect(Collectors.toList());
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
}
