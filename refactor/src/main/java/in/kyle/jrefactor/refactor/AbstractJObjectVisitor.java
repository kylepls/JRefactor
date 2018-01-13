package in.kyle.jrefactor.refactor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import in.kyle.api.utils.Try;
import in.kyle.jrefactor.tree.JObject;

class AbstractJObjectVisitor<T> {
    
    private static final String BASE_CLASS = "in.kyle.jrefactor.refactor.JavaBaseVisitor";
    
    private static final Map<Class<?>, Method> METHODS;
    
    static {
        Class<?> CLAZZ = Try.to(() -> Class.forName(BASE_CLASS));
        METHODS = Arrays.stream(CLAZZ.getDeclaredMethods())
                        .filter(method -> Modifier.isPublic(method.getModifiers()))
                        .filter(method -> method.getParameterCount() == 1)
                        .filter(method -> method.getName()
                                                .equals("visit" +
                                                        method.getParameterTypes()[0]
                                                                .getSimpleName()))
                        .collect(Collectors.toMap(method -> method.getParameterTypes()[0],
                                                  method -> method));
    }
    
    public T visit(JObject object) {
        if (object != null) {
            Method method = METHODS.get(object.getClass());
            if (method == null) {
                throw new RuntimeException("No such visitor for " + object);
            } else {
                return (T) Try.to(() -> method.invoke(this, object));
            }
        } else {
            return null;
        }
    }
    
    public T visitChildren(JObject object) {
        if (object != null) {
            for (JObject child : JObjectUtils.getDirectChildren(object)) {
                visit(child);
            }
        }
        return null;
    }
}
