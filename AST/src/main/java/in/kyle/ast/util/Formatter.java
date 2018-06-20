package in.kyle.ast.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Formatter {
    
    public static String format(String string, Object... kv) {
        for (int i = 0; i < kv.length; i += 2) {
            String key = "{" + kv[i] + "}";
            Object value = kv[i + 1];
            string = string.replace(key, value.toString());
        }
        return string;
    }
    
    public static void append(StringBuilder builder, String format, String... parameters) {
        builder.append(String.format(format, (Object[]) parameters));
    }
    
    public static String indent(String string) {
        return string.replaceAll("^", "    ");
    }
}
