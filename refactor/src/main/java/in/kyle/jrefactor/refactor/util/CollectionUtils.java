package in.kyle.jrefactor.refactor.util;

import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectionUtils {
    
    public static <T> Collector<T, ?, Optional<T>> toSingleton() {
        return Collectors.reducing((t, t2) -> {
            throw new IllegalArgumentException();
        });
    }
}
