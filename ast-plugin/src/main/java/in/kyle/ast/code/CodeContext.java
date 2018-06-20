package in.kyle.ast.code;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CodeContext {
    
    private final Map<String, String> importMappings = new HashMap<>();
    private final Map<String, String> fieldMethods = new HashMap<>();
    private final List<String> enumMethods = new ArrayList<>();
    
    public void addEnumMethod(String method) {
        enumMethods.add(method);
    }
    
    public String getEnumMethods() {
        return enumMethods.stream().collect(Collectors.joining("\n"));
    }
    
    public void addImport(String type, String path) {
        importMappings.put(type, "import " + path + ";\n");
    }
    
    public void addFieldMethod(String field, String method) {
        fieldMethods.put(field, method);
    }
    
    public String getImport(String field) {
        return importMappings.get(field);
    }
    
    public String getImports(Collection<String> types) {
        StringBuilder builder = new StringBuilder();
        for (String type : types) {
            String anImport = getImport(type);
            if (anImport != null) {
                builder.append(anImport);
            }
        }
        return builder.toString();
    }
    
    public String getMethodForField(String type) {
        return fieldMethods.get(type);
    }
}
