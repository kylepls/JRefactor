package in.kyle.jrefactor.writer;

public class TreeWriter extends AbstractWriter {
    
    @Override
    protected String objectToString(Object o) {
        return o.toString();
    }
}
