package in.kyle.parser.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import in.kyle.api.utils.Conditions;
import in.kyle.parser.JObject;
import in.kyle.parser.RewriteableField;

public final class CollectionUtils {
    
    private CollectionUtils() {
    }
    
    public static <T extends JObject> boolean removeValue(Collection<RewriteableField<T>> 
                                                                  collection,
                                                          T value) {
        return collection.removeIf(t -> value.equals(t.getValue()));
    }
    
    public static <T extends JObject> Set<RewriteableField<T>> convertToRewriteableSet
            (Collection<T> collection) {
        return collection.stream()
                         .map(RewriteableField::new)
                         .collect(Collectors.toCollection(LinkedHashSet::new));
    }
    
    public static <T extends JObject> Set<T> convertToJObjectSet(Collection<RewriteableField<T>> 
                                                                         collection) {
        LinkedHashSet<T> collect = collection.stream()
                                             .map(RewriteableField::getValue)
                                             .collect(Collectors.toCollection(LinkedHashSet::new));
        return Collections.unmodifiableSet(collect);
    }
    
    public static <T extends JObject> boolean addValue(Collection<RewriteableField<T>> collection,
                                                       T value) {
        Conditions.notNull(value);
        return collection.add(new RewriteableField<>(value));
    }
    
    public static <T extends JObject> void overwrite(Collection<RewriteableField<T>> collection,
                                                     Collection<T> newValue) {
        collection.clear();
        collection.addAll(convertToRewriteableSet(newValue));
    }
    
    public static <T> List<T> createList(Object... args) {
        List<T> list = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof Collection) {
                list.addAll((Collection) arg);
            } else {
                list.add((T) arg);
            }
        }
        return Collections.unmodifiableList(list);
    }
}
