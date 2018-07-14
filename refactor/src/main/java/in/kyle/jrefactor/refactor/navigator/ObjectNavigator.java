package in.kyle.jrefactor.refactor.navigator;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import in.kyle.jrefactor.refactor.util.CollectionUtils;
import in.kyle.jrefactor.refactor.util.JObjUtils;
import in.kyle.jrefactor.refactor.util.JObjUtilsStreams;
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.JBlock;

import static in.kyle.jrefactor.refactor.util.JObjUtilsStreams.RECURSIVE;

public class ObjectNavigator {
    
    private final JObj root;
    private JObj currentLocation;
    
    public ObjectNavigator(JObj root, JObj currentLocation) {
        this.root = root;
        this.currentLocation = currentLocation;
    }
    
    public ObjectNavigator(JObj root) {
        this(root, root);
    }
    
    public ObjectNavigator parent() {
        currentLocation = JObjUtils.findParent(root, currentLocation);
        return this;
    }
    
    public <T extends JObj> T get() {
        return (T) currentLocation;
    }
    
    public ObjectNavigator findParentInstance(Class<?> clazz) {
        while (currentLocation != null && !(clazz.isAssignableFrom(currentLocation.getClass()))) {
            parent();
        }
        return this;
    }
    
    public ObjectNavigator findChild(Class<?> clazz) {
        currentLocation = JObjUtilsStreams.getAllChildren(currentLocation)
                .filter(child -> clazz.isAssignableFrom(child.getClass()))
                .findAny()
                .orElse(null);
        return this;
    }
    
    public <T extends JObj> ObjectNavigator findChildMatching(Class<T> clazz,
                                                              Predicate<T> matcher) {
        currentLocation = JObjUtilsStreams.getAllChildren(currentLocation)
                .filter(child -> clazz.isAssignableFrom(child.getClass()))
                .map(obj -> (T) obj)
                .filter(matcher)
                .collect(CollectionUtils.toSingleton())
                .orElse(null);
        return this;
    }
    
    public ObjectNavigator findParentBlock() {
        if (currentLocation instanceof JBlock) {
            parent();
        }
        findParentInstance(JBlock.class);
        return this;
    }
    
    public ObjectNavigator findNextMatch(Class<? extends JObj> clazz) {
        List<JObj> allElements = JObjUtilsStreams.getChildrenOfType(RECURSIVE, root, clazz)
                .collect(Collectors.toList());
        cycleList(allElements);
        return this;
    }
    
    public ObjectNavigator findNextMatch(JObj obj) {
        List<JObj> allElements = JObjUtils.getAllElements(root);
        cycleList(allElements);
        return this;
    }
    
    private void cycleList(List<JObj> elements) {
        int index = elements.indexOf(currentLocation);
        currentLocation = elements.get(index + 1);
    }
    
    public boolean exists() {
        return currentLocation != null;
    }
}
