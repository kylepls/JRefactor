package in.kyle.ast.code;

import org.stringtemplate.v4.ST;

import java.util.*;
import java.util.function.Consumer;

import in.kyle.api.utils.Try;
import in.kyle.ast.code.file.Field;
import in.kyle.ast.util.ResourceUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter(AccessLevel.PACKAGE)
public class CodeModifier {
    
    private final Map<String, String> fieldDefaults = new HashMap<>();
    private final Map<String, Set<String>> importMappings = new HashMap<>();
    private final Map<String, String> fieldMethods = new HashMap<>();
    private final List<String> enumMethods = new ArrayList<>();
    
    private final Map<String, String> oldFiles = new HashMap<>();
    
    private String packagePrefix = "";
    private String filePrefix = "";
    
    {
        addDefaultValues();
    }
    
    public void addFieldDefault(String type, String defaultValue) {
        fieldDefaults.put(type, defaultValue);
    }
    
    public void processFiles(FileSet files) {
        files.getFiles().forEach(this::preProcess);
        files.getFiles().forEach(this::process);
    }
    
    void preProcess(JavaFile file) {
        updatePackageName(file);
        updateFileName(file);
        importMappings.put(file.getName(), createFileImport(file));
    }
    
    void process(JavaFile file) {
        updateTypeNames(file);
        addImports(file);
        setFieldDefaults(file);
        addFieldMethods(file);
        addEnumMethods(file);
    }
    
    void updatePackageName(JavaFile file) {
        if (!packagePrefix.isEmpty()) {
            if (file.getPackageName() != null) {
                file.setPackageName(packagePrefix + "." + file.getPackageName());
            } else {
                file.setPackageName(packagePrefix);
            }
        }
    }
    
    void updateFileName(JavaFile file) {
        String oldName = file.getName();
        file.setName(filePrefix + file.getName());
        oldFiles.put(oldName, file.getName());
    }
    
    void updateTypeNames(JavaFile file) {
        Map<Consumer<String>, String> rewritableTypes = file.getRewritableTypes();
        for (Map.Entry<Consumer<String>, String> rewritableType : rewritableTypes.entrySet()) {
            String type = rewritableType.getValue();
            if (oldFiles.containsKey(type)) {
                Consumer<String> processor = rewritableType.getKey();
                processor.accept(oldFiles.get(type));
            }
        }
    }
    
    private Set<String> createFileImport(JavaFile file) {
        String importString = "";
        if (file.getPackageName() != null) {
            importString += file.getPackageName() + ".";
        }
        importString += file.getName();
        return Collections.singleton(importString);
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
        Set<String> imports = importMappings.get(type);
        if (imports != null) {
            file.getImports().addAll(imports);
        }
    }
    
    private void addDefaultValues() {
        importMappings.put("List", new HashSet<>(Arrays.asList("java.util.List", "java.util.ArrayList")));
        importMappings.put("Set", new HashSet<>(Arrays.asList("java.util.Set", "java.util.HashSet")));
        importMappings.put("Optional", Collections.singleton("java.util.Optional"));
        
        String collectionMethods =
                Try.to(() -> ResourceUtils.loadResource("string-template/collectionMethods.st"));
        fieldMethods.put("List", collectionMethods);
        fieldMethods.put("Set", collectionMethods);
    }
}
