package in.kyle.jrefactor.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaStringUtils {
    
    private static final Map<Pattern, Function<String[], String>> processors = new HashMap<>();
    
    private static void addPattern(String pattern, Function<String[], String> replace) {
        processors.put(Pattern.compile(pattern), replace);
    }
    
    static {
        addPattern("\\\\b", simpleReplace("\b"));
        addPattern("\\f", simpleReplace("\f"));
        addPattern("\\n", simpleReplace("\n"));
        addPattern("\\r", simpleReplace("\r"));
        addPattern("\\\\t", simpleReplace("\t"));
        addPattern("\\\"", simpleReplace("\""));
        addPattern("\\'", simpleReplace("'"));
        addPattern("\\\\([0-3]?[0-9]{1,2})", JavaStringUtils::octalReplace);
        addPattern("\\\\u([0-9]{4})", JavaStringUtils::unicodeReplace);
    }
    
    public static String unescapeString(String java) {
        for (Map.Entry<Pattern, Function<String[], String>> entry : processors.entrySet()) {
            Matcher matcher = entry.getKey().matcher(java);
            while (matcher.find()) {
                
                String[] groups = getGroups(matcher);
                String replacement = entry.getValue().apply(groups);
                java = matcher.replaceAll(replacement);
            }
        }
        return java;
    }
    
    private static String[] getGroups(Matcher matcher) {
        String[] matches = new String[matcher.groupCount()];
        for (int i = 0; i < matches.length; i++) {
            matches[i] = matcher.group(i);
        }
        return matches;
    }
    
    private static String unicodeReplace(String[] groups) {
        return String.valueOf((char) Integer.parseInt(groups[0].substring(2), 16));
    }
    
    private static String octalReplace(String[] groups) {
        String value = groups[0].substring(1);
        char c = (char) Integer.parseInt(value, 8);
        return new String(new char[] {c});
    }
    
    private static Function<String[], String> simpleReplace(String replace) {
        return s -> replace;
    }
}
