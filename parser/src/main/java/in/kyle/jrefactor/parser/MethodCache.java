package in.kyle.jrefactor.parser;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MethodCache {
    
    private final Map<Class<?>, Method> mappings;
    
    public MethodCache(Class<?> targetClass, Predicate<Method>... filters) {
        Collector<Method, ?, Map<Class<?>, Method>> collector =
                Collectors.toMap(method -> method.getParameterTypes()[0], method -> method);
        this.mappings = calcuateMappings(targetClass, collector, filters);
    }
    
    public MethodCache(Class<?> targetClass,
                       Collector<Method, ?, Map<Class<?>, Method>> collector,
                       Predicate<Method>... filters) {
        this.mappings = calcuateMappings(targetClass, collector, filters);
    }
    
    public Method get(Class<?> clazz) {
        Method method = mappings.get(clazz);
        if (method != null) {
            return method;
        } else {
            throw new RuntimeException("No such method for " + clazz.getName());
        }
    }
    
    private Map<Class<?>, Method> calcuateMappings(Class<?> clazz,
                                                   Collector<Method, ?, Map<Class<?>, Method>> 
                                                           collector,
                                                   Predicate<Method>[] filters) {
        Stream<Method> stream = Arrays.stream(clazz.getDeclaredMethods());
        for (Predicate<Method> filter : filters) {
            stream = stream.filter(filter);
        }
        return stream.collect(collector);
    }
}
