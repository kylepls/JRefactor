package in.kyle.scanner;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import in.kyle.api.utils.Try;
import in.kyle.parser.JObject;

class AbstractJObjectVisitor<T> {
    
    private static final String BASE_CLASS = "in.kyle.scanner.JavaBaseVisitor";
    
    private final Set<Method> SINGLE_PARAMETER_METHODS;
    
    {
        Class<?> CLAZZ = Try.to(() -> Class.forName(BASE_CLASS));
        SINGLE_PARAMETER_METHODS = Arrays.stream(CLAZZ.getDeclaredMethods())
                                         .filter(method -> Modifier.isPublic(method.getModifiers()))
                                         .filter(method -> method.getParameterCount() == 1)
                                         .collect(Collectors.toSet());
    }
    
    public T visit(JObject object) {
        if (object != null) {
            for (Method method : SINGLE_PARAMETER_METHODS) {
                Class<?> aClass = method.getParameterTypes()[0];
                if (aClass.equals(object.getClass())) {
                    return (T) Try.to(() -> method.invoke(this, object), e->new Error(e.getMessage()));
                }
            }
            throw new RuntimeException("No such visitor for " + object);
        } else {
            return null;
        }
    }
    
    public T visitChildren(JObject jObject) {
        if (jObject != null) {
            for (JObject object : JObjectUtils.getDirectChildren(jObject)) {
                visit(object);
            }
        }
        return null;
    }
}
