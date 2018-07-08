package in.kyle.ast.code.processors;

import java.util.HashMap;
import java.util.Map;

import in.kyle.ast.code.file.JavaFile;
import in.kyle.ast.code.file.JavaField;

public class FieldDefaultProcessor implements CodeProcessor {
    
    private final Map<String, String> fieldDefaults = new HashMap<>();
    
    public void registerFieldDefault(String type, String defaultValue) {
        fieldDefaults.put(type, defaultValue);
    }
    
    @Override
    public void process(JavaFile file) {
        for (JavaField field : file.getFields()) {
            if (field.getValue() == null) {
                field.setValue(fieldDefaults.get(field.getSimpleType()));
            }
        }
    }
}
