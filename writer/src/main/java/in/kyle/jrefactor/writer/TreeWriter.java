package in.kyle.jrefactor.writer;

public class TreeWriter extends BasicWriter {
    
    @Override
    protected String objectToString(Object o) {
        return o.toString();
    }
}
