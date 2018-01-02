package in.kyle.scanner;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import in.kyle.api.utils.Try;
import in.kyle.parser.JObject;

class AbstractJObjectVisitor<T> {
    
    private static final String BASE_CLASS = "in.kyle.scanner.JavaBaseVisitor";
    private static final Class<?> CLAZZ = Try.to(() -> Class.forName(BASE_CLASS));
    
    public T visit(JObject object) {
        if (object != null) {
            for (Method method : CLAZZ.getDeclaredMethods()) {
                if (Modifier.isPublic(method.getModifiers())) {
                    if (method.getParameterCount() == 1) {
                        Class<?> aClass = method.getParameterTypes()[0];
                        if (aClass.equals(object.getClass())) {
                            return (T) Try.to(() -> method.invoke(this, object));
                        }
                    }
                }
            }
            throw new RuntimeException("No such visitor for " + object);
        } else {
            return null;
        }
    }
    
    public T visitChildren(JObject jObject) {
        if (jObject != null) {
            for (JObject object : jObject.getChildren()) {
                visit(object);
            }
        }
        return null;
    }
}
