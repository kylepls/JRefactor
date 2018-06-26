import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TestGen {
    
    public static final File OUTPUT_FILE = new File("output.properties");
            
    public static Map<String, Object> getParameters() {
        Map<String, Object> testParameters = new HashMap<>();
        testParameters.put("key1", "value1");
        testParameters.put("inner", new Inner());
        return testParameters;
    }
    
    private static class Inner {
        private final String key2 = "value2";
        
        public String getKey2() {
            return key2;
        }
    }
}