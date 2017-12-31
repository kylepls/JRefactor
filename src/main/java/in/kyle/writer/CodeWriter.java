package in.kyle.writer;

public interface CodeWriter {
    
    CodeWriter indent();
    
    CodeWriter dedent();
    
    CodeWriter appendLine();
    
    CodeWriter appendLine(Object object);
    
    CodeWriter append(Object object);
    
    CodeWriter append(String format, Object... args);
    
    CodeWriter appendLine(String format, Object... args);
    
    void clear();
}
