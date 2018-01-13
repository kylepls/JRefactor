package in.kyle.jrefactor.tree;

import java.util.ArrayList;
import java.util.Collection;

public class JObjectList<T extends JObject> extends ArrayList<T> implements JObject {
    
    public JObjectList() {
    }
    
    public JObjectList(Collection<? extends T> c) {
        super(c);
    }
}
