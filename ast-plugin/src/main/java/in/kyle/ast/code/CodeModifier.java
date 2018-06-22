package in.kyle.ast.code;

import org.stringtemplate.v4.ST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.kyle.api.utils.Try;
import in.kyle.ast.code.file.Field;
import in.kyle.ast.util.ResourceUtils;
import lombok.Data;

@Data
public class CodeModifier {
    
    private final Map<String, String> fieldDefaults = new HashMap<>();
    private final Map<String, String> importMappings = new HashMap<>();
    private final Map<String, String> fieldMethods = new HashMap<>();
    private final List<String> enumMethods = new ArrayList<>();
    
    {
        importMappings.put("List", "java.util.List");
        importMappings.put("Set", "java.util.Set");
        
        String collectionMethods =
                Try.to(() -> ResourceUtils.loadResource("string-template/collectionMethods.st"));
        fieldMethods.put("List", collectionMethods);
        fieldMethods.put("Set", collectionMethods);
    }
    
    void process(JavaFile file) {
        addImports(file);
        setFieldDefaults(file);
        addFieldMethods(file);
        addEnumMethods(file);
    }
    
    private void addEnumMethods(JavaFile file) {
        if (file.getType() == JavaFileType.ENUM) {
            file.getMethods().addAll(enumMethods);
        }
    }
    
    private void addFieldMethods(JavaFile file) {
        for (Field field : file.getFields()) {
            String type = field.getType();
            if (fieldMethods.containsKey(type)) {
                String templateString = fieldMethods.get(type);
                ST temp = new ST(templateString);
                String singularName = field.getName().substring(field.getName().length() - 1);
                String singularNameUpper =
                        singularName.substring(0, 1).toUpperCase() + singularName.substring(1);
                temp.add("singular_name", singularName);
                temp.add("singular_name_upper", singularNameUpper);
                temp.add("generic_type", field.getGeneric());
                temp.add("field_name", field.getName());
                String render = temp.render();
                file.getMethods().add(render);
            }
        }
    }
    
    private void setFieldDefaults(JavaFile file) {
        for (Field field : file.getFields()) {
            if (field.getValue() == null) {
                field.setValue(fieldDefaults.get(field.getType()));
            }
        }
    }
    
    private void addImports(JavaFile file) {
        for (Field field : file.getFields()) {
            if (field.getGeneric() != null) {
                addImport(file, field.getGeneric());
            }
            addImport(file, field.getType());
        }
        if (file.getSuperType() != null) {
            addImport(file, file.getSuperType().getName());
        }
        if (file.getGenericSuper() != null) {
            addImport(file, file.getGenericSuper());
        }
        file.getImplementingTypes().forEach(type -> addImport(file, type));
    }
    
    private void addImport(JavaFile file, String type) {
        String importName = importMappings.get(type);
        if (importName != null) {
            file.getImports().add(importName);
        }
    }
}
