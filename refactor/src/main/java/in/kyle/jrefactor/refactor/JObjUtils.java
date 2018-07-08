package in.kyle.jrefactor.refactor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
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
    
    public static Collection<JObj> getDirectChildren(JObj obj) {
        return obj.getDirectChildren()
                  .stream()
                  .filter(o -> o instanceof JObj)
                  .map(o -> (JObj) o)
                  .collect(Collectors.toList());
    }
    
    public static List<JObj> getAllChildren(JObj obj) {
        return obj.getAllChildren()
                  .stream()
                  .filter(o -> o instanceof JObj)
                  .map(o -> (JObj) o)
                  .collect(Collectors.toList());
    }
    
    public static List<JObj> getAllElements(JObj obj) {
        List<JObj> objs = new ArrayList<>();
        objs.addAll(getAllChildren(obj));
        getAllChildren(obj).stream()
                           .map(JObjUtils::getAllElements)
                           .flatMap(List::stream)
                           .forEach(objs::add);
        return objs;
    }
}
