package in.kyle.jrefactor.refactor.util;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

import in.kyle.jrefactor.tree.JObj;

public class JObjUtilsStreams {
    
    public static final Function<JObj, Stream<JObj>> DIRECT = JObjUtilsStreams::getDirectChildren;
    public static final Function<JObj, Stream<JObj>> ALL = JObjUtilsStreams::getDirectChildren;
    
    public static Stream<JObj> getDirectChildren(JObj obj) {
        return getJObjChildren(obj, JObj::getDirectChildren);
    }
    
    public static Stream<JObj> getAllChildren(JObj obj) {
        return getJObjChildren(obj, JObj::getAllChildren);
    }
    
    private static Stream<JObj> getJObjChildren(JObj obj,
                                                Function<JObj, Collection<Object>> identity) {
        return identity.apply(obj).stream().filter(o -> o instanceof JObj).map(o -> (JObj) o);
    }
    
    public static <T extends JObj> Stream<T> getChildrenOfType(Function<JObj, Stream<JObj>> 
                                                                        identity,
                                                                JObj obj,
                                                                Class<T> clazz) {
        return identity.apply(obj)
                .filter(child -> child.getClass().isAssignableFrom(clazz))
                .map(child -> (T) child);
    }
    
    public static Stream<JObj> getAllElements(JObj obj) {
        return getAllChildren(obj).flatMap(JObjUtilsStreams::getAllElements);
    }
}
