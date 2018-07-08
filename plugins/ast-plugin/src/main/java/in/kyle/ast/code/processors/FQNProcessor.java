package in.kyle.ast.code.processors;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import in.kyle.ast.code.JavaFile;

public class FQNProcessor implements CodeProcessor {
    
    private final Map<String, String> importMappings = new HashMap<String, String>() {{
        put("List", "java.util.List");
        put("Set", "java.util.Set");
        put("Optional", "java.util.Optional");
    }};
    
    @Override
    public void preprocess(JavaFile file) {
        importMappings.put(file.getName(), createFQNForFile(file));
    }
    
    @Override
    public void process(JavaFile file) {
        file.getRewritableTypes().forEach(this::rewriteType);
    }
    
    private void rewriteType(Consumer<String> consumer, String type) {
        if (importMappings.containsKey(type)) {
            consumer.accept(importMappings.get(type));
        }
    }
    
    // FQN - Fully Qualified Name
    private String createFQNForFile(JavaFile file) {
        String importString = "";
        if (file.getPackageName() != null) {
            importString += file.getPackageName() + ".";
        }
        importString += file.getName();
        return importString;
    }
}
