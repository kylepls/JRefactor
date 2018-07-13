package in.kyle.jrefactor.refactor.util.obj;

import in.kyle.jrefactor.tree.obj.JTypeName;
import lombok.experimental.UtilityClass;

import static in.kyle.jrefactor.refactor.util.obj.JTypeNameUtils.getType;

@UtilityClass
public class JTypeNames {
    
    public static final JTypeName DOUBLE = getType(double.class);
    public static final JTypeName LONG = getType(long.class);
    public static final JTypeName INT = getType(int.class);
    public static final JTypeName FLOAT = getType(float.class);
    public static final JTypeName CHAR = getType(char.class);
    public static final JTypeName SHORT = getType(short.class);
    public static final JTypeName BYTE = getType(byte.class);
    public static final JTypeName BOOLEAN = getType(boolean.class);
    public static final JTypeName STRING = getType(String.class);
    public static final JTypeName OBJECT = getType(Object.class);
    
    public static final JTypeName BOXED_DOUBLE = getType(Double.class);
    public static final JTypeName BOXED_LONG = getType(Long.class);
    public static final JTypeName BOXED_INT = getType(Integer.class);
    public static final JTypeName BOXED_FLOAT = getType(Float.class);
    public static final JTypeName BOXED_CHAR = getType(Character.class);
    public static final JTypeName BOXED_SHORT = getType(Short.class);
    public static final JTypeName BOXED_BYTE = getType(Byte.class);
    public static final JTypeName BOXED_BOOLEAN = getType(Boolean.class);
        
}
