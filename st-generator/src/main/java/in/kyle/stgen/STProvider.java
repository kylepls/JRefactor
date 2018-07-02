package in.kyle.stgen;

import java.io.File;
import java.util.Map;

public interface STProvider {
    
    Map<String, Object> getParameters();
    
    File getOutputFile();
}
