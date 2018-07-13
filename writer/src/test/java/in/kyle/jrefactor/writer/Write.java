package in.kyle.jrefactor.writer;

import in.kyle.jrefactor.tree.JObj;

public final class Write {
    private Write() {
    }
    
    public static String object(JObj obj) {
        return new EzWriter().write(obj).replace("\r", "");
    }
}
