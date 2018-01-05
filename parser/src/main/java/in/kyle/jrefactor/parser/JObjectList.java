package in.kyle.jrefactor.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import in.kyle.jrefactor.CodeWriter;

public class JObjectList<T extends JObject> extends ArrayList<T> implements JObject {
    
    public JObjectList(int initialCapacity) {
        super(initialCapacity);
    }
    
    public JObjectList() {
    }
    
    public JObjectList(Collection<? extends T> c) {
        super(c);
    }
    
    @Override
    public void write(CodeWriter writer) {
        for (Iterator<T> iterator = this.iterator(); iterator.hasNext(); ) {
            T t = iterator.next();
            writer.append(t);
            if (iterator.hasNext()) {
                writer.append(", ");
            }
        }
    }
}
