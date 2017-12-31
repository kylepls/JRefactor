package in.kyle.writer;

public class TreeWriter extends BasicWriter {
    
    @Override
    protected String objectToString(Object o) {
        return o.toString();
    }
}
