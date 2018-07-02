import java.io.File;
import java.util.HashMap;
import java.util.Map;

import in.kyle.stgen.STProvider;

public class TestGen implements STProvider {
    
    @Override
    public Map<String, Object> getParameters() {
        Map<String, Object> testParameters = new HashMap<>();
        testParameters.put("key1", "value1");
        testParameters.put("inner", new Inner());
        return testParameters;
    }
    
    @Override
    public File getOutputFile() {
        return new File("output.properties");
    }
    
    private static class Inner {
        private final String key2 = "value2";
    }
}