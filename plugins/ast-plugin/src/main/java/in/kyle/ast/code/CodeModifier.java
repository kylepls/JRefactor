package in.kyle.ast.code;

import org.antlr.v4.runtime.misc.OrderedHashSet;
import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.ST;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import in.kyle.api.utils.Try;
import in.kyle.ast.code.file.Field;
import in.kyle.ast.util.ResourceUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import static in.kyle.ast.code.JavaFileType.ABSTRACT_CLASS;
import static in.kyle.ast.code.JavaFileType.CLASS;
import static in.kyle.ast.code.JavaFileType.ENUM;

@Setter
@Getter(AccessLevel.PACKAGE)
public class CodeModifier {
    
    private final Map<String, String> fieldDefaults = new HashMap<>();
    private final Map<String, Set<String>> importMappings = new HashMap<>();
    private final Map<String, String> fieldMethods = new HashMap<>();
    private final Map<String, String> variables = new HashMap<>();
    private final List<String> enumMethods = new ArrayList<>();
    
    private final Map<String, String> oldFiles = new HashMap<>();
    
    private String packagePrefix = "";
    private String filePrefix = "";
    
    {
        addDefaultValues();
    }
    
    public void addVariable(String id, String value) {
        variables.put(id, value);
    }
    
    public void addFieldDefault(String type, String defaultValue) {
        fieldDefaults.put(type, defaultValue);
    }
    
    public void processFiles(FileSet fileSet) {
        List<JavaFile> files = getAllFiles(fileSet);
        files.forEach(this::preProcess);
        files.forEach(this::process);
        files.forEach(this::postProcess);
    }
    
    List<JavaFile> getAllFiles(FileSet files) {
        return files.getFiles()
                    .stream()
                    .map(this::getAllFiles)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
    }
    
    List<JavaFile> getAllFiles(JavaFile file) {
        List<JavaFile> result = new ArrayList<>();
        result.add(file);
        for (JavaFile inner : file.getInnerClasses()) {
            result.addAll(getAllFiles(inner));
        }
        return result;
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
        transposeVariables(file);
    }
    
    void postProcess(JavaFile file) {
        addConstructors(file);
        addSuperImports(file);
    }
    
    void addSuperImports(JavaFile file) {
        if (file.hasSuperType()) {
            addSuperImports(file.getSuperType());
            file.addImports(file.getSuperType().getImports());
        }
    }
    
    private void transposeVariables(JavaFile file) {
        Set<String> methods = new HashSet<>(file.getMethods());
        for (String method : methods) {
            if (variables.containsKey(method)) {
                file.getMethods().remove(method);
                String methodString = variables.get(method);
                ST st = new ST(methodString);
                st.add("name", file.getName());
                file.addMethod(st.render());
            }
        }
    }
    
    void addConstructors(JavaFile file) {
        if (file.typeIs(CLASS) || file.typeIs(ABSTRACT_CLASS) || file.typeIs(ENUM)) {
            List<Field> superParameters = getFields(file);
            removeDefaultFields(superParameters);
            List<Field> allParameters = new ArrayList<>(superParameters);
            List<Field> definedParameters = new ArrayList<>(file.getFields());
            definedParameters.addAll(file.computeEnumFields());
            removeDefaultFields(definedParameters);
            superParameters.removeAll(definedParameters);
            
            String parameters = allParameters.stream()
                                             .map(this::writeFieldParameter)
                                             .collect(Collectors.joining(", "));
            
            String superCall;
            if (file.typeIs(CLASS) || file.typeIs(ABSTRACT_CLASS)) {
                superCall = String.format("super(%s);",
                                          superParameters.stream()
                                                         .map(Field::getName)
                                                         .collect(Collectors.joining(", ")));
            } else {
                superCall = "";
            }
            
            
            String assignments = definedParameters.stream()
                                                  .map(f -> String.format("this.%s = %s;",
                                                                          f.getName(),
                                                                          f.getName()))
                                                  .collect(Collectors.joining("\n"));
            
            String modifier = file.typeIs(CLASS) || file.typeIs(ABSTRACT_CLASS) ? "public" : "private";
            String constructorString = String.format("%s %s(%s) {\n\t%s\n\t%s\n}",
                                                     modifier,
                                                     file.getName(),
                                                     parameters,
                                                     superCall,
                                                     assignments);
            file.addConstructor(constructorString);
            
            if (!allParameters.isEmpty() && file.getType() != JavaFileType.ENUM) {
                constructorString = String.format("public %s() { super(); }", file.getName());
                file.addConstructor(constructorString);
            }
        }
    }
    
    private String writeFieldParameter(Field field) {
        if (field.hasGeneric()) {
            return String.format("%s<%s> %s", field.getType(), field.getGeneric(), field.getName());
        } else {
            return String.format("%s %s", field.getType(), field.getName());
        }
    }
    
    private void removeDefaultFields(List<Field> fields) {
        fields.removeIf(Field::hasValue);
    }
    
    private List<Field> getFields(JavaFile file) {
        List<Field> fields = new ArrayList<>();
        if (file.hasSuperType()) {
            fields.addAll(getFields(file.getSuperType()));
        }
        fields.addAll(file.getFields());
        fields.addAll(file.computeEnumFields());
        // generics transposition
        for (int i = 0; i < fields.size(); i++) {
            Field typeParameter = fields.get(i);
            if (typeParameter.getType().length() == 1 && file.hasGenericSuper()) {
                Field field = new Field(file.getGenericSuper(),
                                        typeParameter.getGeneric(),
                                        typeParameter.getName(),
                                        typeParameter.getValue());
                fields.set(i, field);
            }
        }
        
        return fields;
    }
    
    void updatePackageName(JavaFile file) {
        if (!packagePrefix.isEmpty()) {
            if (file.hasPackage()) {
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
        Set<Field> fields = new OrderedHashSet<>();
        fields.addAll(file.getFields());
        for (Field field : fields) {
            String type = field.getType();
            if (fieldMethods.containsKey(type)) {
                String templateString = fieldMethods.get(type);
                ST temp = new ST(templateString);
                String name = field.getName();
                String singularName = name.substring(0, name.length() - 1);
                temp.add("singular_name", singularName);
                temp.add("singular_name_upper", StringUtils.capitalize(singularName));
                temp.add("generic_type", field.getGeneric());
                temp.add("field_name", name);
                temp.add("field_name_upper", StringUtils.capitalize(name));
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
            if (field.getGeneric() != null && !isInnerType(file, field.getGeneric())) {
                addImport(file, field.getGeneric());
            }
            if (!isInnerType(file, field.getType())) {
                addImport(file, field.getType());
            }
        }
        if (file.getSuperType() != null) {
            addImport(file, file.getSuperType().getName());
        }
        if (file.getGenericSuper() != null) {
            addImport(file, file.getGenericSuper());
        }
        if (file.hasInnerClasses()) {
            file.addImport("lombok.Getter");
        }
        file.getIsTypes().forEach(type -> addImport(file, type));
    }
    
    private boolean isInnerType(JavaFile file, String field) {
        return file.getInnerClasses().stream().anyMatch(f -> f.getName().equals(field));
    }
    
    private void addImport(JavaFile file, String type) {
        Set<String> imports = importMappings.get(type);
        if (imports != null) {
            file.getImports().addAll(imports);
        }
    }
    
    private void addDefaultValues() {
        importMappings.put("List",
                           new HashSet<>(Arrays.asList("java.util.List", "java.util.ArrayList")));
        importMappings.put("Set",
                           new HashSet<>(Arrays.asList("java.util.Set", "java.util.HashSet")));
        importMappings.put("Optional", Collections.singleton("java.util.Optional"));
        
        String collectionMethods =
                Try.to(() -> ResourceUtils.loadResource("string-template/collectionMethods.st"));
        fieldMethods.put("List", collectionMethods);
        fieldMethods.put("Set", collectionMethods);
    }
}
