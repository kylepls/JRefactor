package in.kyle;

import java.util.ArrayList;
import java.util.Collection;

import in.kyle.parser.JObject;
import in.kyle.writer.CodeWriter;

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
        throw new RuntimeException(
                "Cannot write " + this.getClass() + " to writer"); // TODO: 1/4/2018  
    }
}
