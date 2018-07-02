package in.kyle.jrefactor.writer;

import in.kyle.jrefactor.tree.JObj;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Write {
    public static String object(JObj obj) {
        return new EzWriter().write(obj).replace("\r", "");
    }
}
