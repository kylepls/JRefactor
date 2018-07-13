package in.kyle.jrefactor.refactor.util.obj;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.JTypeName;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JTypeNameUtils {
    
    // @formatter::off
    public static final JTypeName[] NUMERIC_TYPES = new JTypeName[]{
            JTypeNames.DOUBLE,
            JTypeNames.FLOAT,
            JTypeNames.LONG,
            JTypeNames.INT,
            JTypeNames.CHAR,
            JTypeNames.SHORT,
            JTypeNames.BYTE,
    };
    
    private static final Map<JTypeName, JTypeName> BOX_MAP = new HashMap<JTypeName, JTypeName>() {
        {
            put(JTypeNames.DOUBLE, JTypeNames.BOXED_DOUBLE);
            put(JTypeNames.LONG, JTypeNames.BOXED_LONG);
            put(JTypeNames.FLOAT, JTypeNames.BOXED_FLOAT);
            put(JTypeNames.INT, JTypeNames.BOXED_INT);
            put(JTypeNames.CHAR, JTypeNames.BOXED_CHAR);
            put(JTypeNames.SHORT, JTypeNames.BOXED_SHORT);
            put(JTypeNames.BYTE, JTypeNames.BOXED_BYTE);
            put(JTypeNames.BOOLEAN, JTypeNames.BOXED_BOOLEAN);
        }
    };
    
    private static final Map<JTypeName, JTypeName> ARITHMETIC_CONVERSIONS = new HashMap<JTypeName, JTypeName>() {
        {
            put(JTypeNames.BYTE, JTypeNames.INT);
            put(JTypeNames.SHORT, JTypeNames.INT);
            put(JTypeNames.CHAR, JTypeNames.CHAR);
            
            put(JTypeNames.BOXED_DOUBLE, JTypeNames.DOUBLE);
            put(JTypeNames.BOXED_LONG, JTypeNames.LONG);
            put(JTypeNames.BOXED_FLOAT, JTypeNames.FLOAT);
            put(JTypeNames.BOXED_INT, JTypeNames.INT);
            put(JTypeNames.BOXED_CHAR, JTypeNames.CHAR);
            
            put(JTypeNames.BOXED_SHORT, JTypeNames.INT);
            put(JTypeNames.BOXED_BYTE, JTypeNames.INT);
            put(JTypeNames.BOXED_BOOLEAN, JTypeNames.INT);
        }
    };
    
    private static final Map<JTypeName, JTypeName> BOX_MAP_COMPLIMENT = new HashMap<JTypeName, JTypeName>() {
        {
            BOX_MAP.forEach((k, v)->put(v, k));
        }
    };
    // @formatter:on
    
    public static JTypeName boxType(JTypeName typeName) {
        return BOX_MAP.get(typeName);
    }
    
    public static JTypeName unboxType(JTypeName typeName) {
        return BOX_MAP_COMPLIMENT.get(typeName);
    }
    
    public static boolean isNumber(JTypeName typeName) {
        for (JTypeName numericType : NUMERIC_TYPES) {
            if (typeName.equals(numericType) || boxType(numericType).equals(typeName)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Given two types find what the result type would be if the two types were 
     * conjoined in an arithmetic operation such as +, -, *, /
     * 
     * Ex: double + int = double
     * Ex2: short + byte = int
     */
    public static JTypeName getArithmeticResultType(JTypeName a, JTypeName b) {
        a = getArithmeticConversion(a);
        b = getArithmeticConversion(b);
        
        int aPresedent = Arrays.asList(NUMERIC_TYPES).indexOf(a);
        int bPresedent = Arrays.asList(NUMERIC_TYPES).indexOf(b);
        
        return aPresedent < bPresedent ? a : b;
    }
    
    private static JTypeName getArithmeticConversion(JTypeName name) {
        // some types are automatically converted to another type for mathematical operations
        return ARITHMETIC_CONVERSIONS.getOrDefault(name, name);
    }
    
    public static JTypeName getType(Class<?> clazz) {
        JPropertyLookup lookup = new JPropertyLookup();
        if (clazz.getPackage() != null) {
            lookup.setAreas(Arrays.asList(clazz.getPackage().getName().split("\\.")));
        }
        return JTypeName.builder()
                .type(clazz.getSimpleName().replace("$", "."))
                .area(lookup)
                .build();
    }
    
    public static JPropertyLookup toPropertyLookup(JTypeName typeName) {
        JPropertyLookup pl = new JPropertyLookup();
        typeName.getArea().ifPresent(a->pl.setAreas(a.getAreas()));
        pl.addArea(typeName.getType());
        return pl;
    }
}
