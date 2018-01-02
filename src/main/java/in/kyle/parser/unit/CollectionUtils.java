package in.kyle.parser.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class CollectionUtils {
    
    private CollectionUtils() {
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
