package in.kyle.jrefactor.writer;

public class Writer {
    private static final CodeWriter WRITER = new SimpleWriter();
    
    public static CodeWriter get() {
        return WRITER;
    }
}
