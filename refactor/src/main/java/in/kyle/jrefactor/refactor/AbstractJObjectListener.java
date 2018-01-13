package in.kyle.jrefactor.refactor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.kyle.api.utils.Try;
import in.kyle.jrefactor.tree.JObject;
import lombok.Data;

class AbstractJObjectListener {
    private static final String BASE_CLASS = "in.kyle.jrefactor.refactor.JavaBaseListener";
    
    private static final Map<Class<?>, MethodPair> METHODS;
    
    private static Class<?> CLAZZ;
    
    static {
        CLAZZ = Try.to(() -> Class.forName(BASE_CLASS));
        METHODS = new HashMap<>();
        List<Method> methods = new ArrayList<>(Arrays.asList(CLAZZ.getDeclaredMethods()));
        methods.removeIf(method -> !isGeneratedMethod(method));
        
        for (Method method : methods) {
            if (method.getName().startsWith("enter") &&
                method.getName().length() > "enter".length()) {
                String base = method.getName().substring("enter".length());
                Method exit = getExitMethod(method, base);
                MethodPair pair = new MethodPair(method, exit);
                METHODS.put(method.getParameterTypes()[0], pair);
            }
        }
    }
    
    private static boolean isGeneratedMethod(Method method) {
        try {
            AbstractJObjectListener.class.getDeclaredMethod(method.getName(), method.getParameterTypes());
            return false;
        } catch (NoSuchMethodException e) {
            return true;
        }
    }
    
    private static Method getExitMethod(Method method, String base) {
        return Try.to(() -> CLAZZ.getDeclaredMethod("exit" + base, method.getParameterTypes()));
    }
    
    public void enter(JObject object) {
        if (object != null) {
            MethodPair pair = METHODS.get(object.getClass());
            if (pair == null) {
                throw new RuntimeException("No such listener for " + object);
            } else {
                invoke(pair.getEnter(), object);
                invoke(pair.getExit(), object);
            }
        }
    }
    
    private void invoke(Method method, Object... args) {
        Try.to(() -> method.invoke(AbstractJObjectListener.this, args));
    }
    
    // probably need a better method name
    public void enterChildren(JObject object) {
        if (object != null) {
            for (JObject child : JObjectUtils.getDirectChildren(object)) {
                enter(child);
            }
        }
    }
    
    @Data
    private static class MethodPair {
        private final Method enter;
        private final Method exit;
    }
}
