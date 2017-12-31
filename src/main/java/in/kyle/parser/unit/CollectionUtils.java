package in.kyle.parser.unit;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import in.kyle.parser.JObject;
import in.kyle.parser.RewriteableField;

final class CollectionUtils {
    
    private CollectionUtils() {
    }
    
    static <T extends JObject> boolean removeValue(Collection<RewriteableField<T>> collection,
                                                   T value) {
        return collection.removeIf(t -> value.equals(t.getValue()));
    }
    
    static <T extends JObject> Set<RewriteableField<T>> convertToRewriteableSet(Collection<T> 
                                                                                        collection) {
        return collection.stream()
                         .map(RewriteableField::new)
                         .collect(Collectors.toCollection(LinkedHashSet::new));
    }
    
    static <T extends JObject> Set<T> convertToJObjectSet(Collection<RewriteableField<T>> 
                                                                  collection) {
        LinkedHashSet<T> collect = collection.stream()
                                             .map(RewriteableField::getValue)
                                             .collect(Collectors.toCollection(LinkedHashSet::new));
        return Collections.unmodifiableSet(collect);
    }
    
    static <T extends JObject> boolean addValue(Collection<RewriteableField<T>> collection,
                                                T value) {
        return collection.add(new RewriteableField<>(value));
    }
    
    static <T extends JObject> void overwrite(Collection<RewriteableField<T>> collection,
                                              Collection<T> newValue) {
        collection.clear();
        collection.addAll(convertToRewriteableSet(newValue));
    }
}
