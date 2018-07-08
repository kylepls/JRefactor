package in.kyle.ast.code.processors;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import in.kyle.ast.code.JavaFile;
import lombok.Setter;

public class TypeNameProcessor implements CodeProcessor {
    
    private final Map<String, String> oldFiles = new HashMap<>();
    @Setter
    private String filePrefix = "";
    
    @Override
    public void preprocess(JavaFile file) {
        String oldName = file.getName();
        file.setName(filePrefix + file.getName());
        oldFiles.put(oldName, file.getName());
    }
    
    @Override
    public void process(JavaFile file) {
        Map<Consumer<String>, String> rewritableTypes = file.getRewritableTypes();
        for (Map.Entry<Consumer<String>, String> rewritableType : rewritableTypes.entrySet()) {
            String type = rewritableType.getValue();
            if (oldFiles.containsKey(type)) {
                Consumer<String> processor = rewritableType.getKey();
                processor.accept(oldFiles.get(type));
            }
        }
    }
}
