package in.kyle.jrefactor.refactor.util;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    
    public static JBlock getFirstUpwardBlock(JObj root, JObj object) {
        JObj search = object;
        while (!(search instanceof JBlock) && search != null) {
            search = JObjUtils.findParent(root, search);
        }
        if (search != null) {
            return (JBlock) search;
        } else {
            return null;
        }
    }
    
    public static JObj findParent(JObj tree, JObj child) {
        if (isChild(tree, child)) {
            return tree;
        } else {
            return JObjUtilsStreams.getAllChildren(tree)
                    .map(sub -> findParent(sub, child))
                    .filter(Objects::nonNull)
                    .findAny()
                    .orElse(null);
        }
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
    
    public static String getStringTree(Object obj) {
        if (obj instanceof JObj) {
            String collect = ((JObj) obj).getAllChildren()
                    .stream()
                    .map(JObjUtils::getStringTree)
                    .collect(Collectors.joining("\n"));
            return String.format("%s:\n%s", obj.getClass().getSimpleName(), indent(collect));
        } else {
            return Objects.toString(obj);
        }
    }
    
    private static String indent(String string) {
        return string.replaceAll("(?m)^", "\t");
    }
}
