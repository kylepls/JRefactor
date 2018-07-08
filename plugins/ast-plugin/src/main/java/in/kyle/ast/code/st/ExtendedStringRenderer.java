package in.kyle.ast.code.st;

import org.stringtemplate.v4.StringRenderer;

import java.util.Locale;

public class ExtendedStringRenderer extends StringRenderer {
    
    @Override
    public String toString(Object o, String formatString, Locale locale) {
        String string = (String) o;
        if ("singular".equals(formatString)) {
            return string.substring(1);
        } else if (formatString != null && formatString.contains("|")) { // | acts as pipe
            String[] split = formatString.split("\\|");
            String temp = string;
            for (String s : split) {
                temp = toString(temp, s, locale);
            }
            return temp;
        } else {
            return super.toString(o, formatString, locale);
        }
    }
}
