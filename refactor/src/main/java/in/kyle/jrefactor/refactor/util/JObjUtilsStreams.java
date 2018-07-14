package in.kyle.jrefactor.refactor.util;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

import in.kyle.jrefactor.tree.JObj;

public class JObjUtilsStreams {
    
    public static final Function<JObj, Stream<JObj>> DIRECT = JObjUtilsStreams::getDirectChildren;
    public static final Function<JObj, Stream<JObj>> ALL = JObjUtilsStreams::getAllChildren;
    public static final Function<JObj, Stream<JObj>> RECURSIVE = JObjUtilsStreams::getTree;
    
    public static Stream<JObj> getDirectChildren(JObj obj) {
        return getJObjChildren(obj, JObj::getDirectChildren);
    }
    
    public static Stream<JObj> getAllChildren(JObj obj) {
        return getJObjChildren(obj, JObj::getAllChildren);
    }
    
    public static Stream<JObj> getTree(JObj obj) {
        return Stream.concat(Stream.of(obj),
                             getAllChildren(obj).flatMap(JObjUtilsStreams::getTree));
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
                .filter(child -> clazz.isAssignableFrom(child.getClass()))
                .map(child -> (T) child);
    }
    
    public static Stream<JObj> getAllElements(JObj obj) {
        return getAllChildren(obj).flatMap(JObjUtilsStreams::getAllElements);
    }
}
