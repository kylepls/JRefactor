package in.kyle.ast.code;

import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import in.kyle.ast.code.file.EnumElement;
import in.kyle.ast.code.file.Field;
import in.kyle.ast.code.file.JavaFileHeader;
import in.kyle.ast.code.file.WritableElement;
import in.kyle.ast.util.Formatter;
import in.kyle.ast.util.Formatter.KV;
import lombok.Data;
import lombok.experimental.Delegate;

import static in.kyle.ast.code.JavaFileType.ABSTRACT_CLASS;
import static in.kyle.ast.code.JavaFileType.CLASS;
import static in.kyle.ast.code.JavaFileType.ENUM;

@Data
public class JavaFile implements WritableElement {
    
    @Delegate(excludes = WritableElement.class)
    private final JavaFileHeader header;
    private final Set<String> imports = new HashSet<>();
    private final Set<Field> fields = new OrderedHashSet<>();
    private final Set<JavaFile> innerClasses = new OrderedHashSet<>();
    private final Set<EnumElement> enumElements = new OrderedHashSet<>();
    private final Set<String> enumVariables = new OrderedHashSet<>();
    private final Set<String> methods = new HashSet<>();
    private final List<String> constructors = new ArrayList<>();
    private String packageName;
    private boolean concrete;
    private boolean innerClass;
    
    public JavaFile(String name, JavaFileType type) {
        this.header = new JavaFileHeader(name, type);
    }
    
    public void addImport(String importString) {
        imports.add(importString);
    }
    
    public void addEnumVariable(String variable) {
        enumVariables.add(variable);
    }
    
    public void addInnerClass(JavaFile file) {
        innerClasses.add(file);
        file.setInnerClass(true);
    }
    
    public void addField(Field field) {
        fields.add(field);
    }
    
    public void addConstructor(String constructor) {
        constructors.add(constructor);
    }
    
    public void addImports(Set<String> imports) {
        this.imports.addAll(imports);
    }
    
    public void addMethod(String method) {
        this.methods.add(method);
    }
    
    public boolean hasPackage() {
        return packageName != null;
    }
    
    public boolean hasGenericSuper() {
        return getGenericSuper() != null;
    }
    
    public void addEnumElement(EnumElement element) {
        enumElements.add(element);
    }
    
    @Override
    public String write() {
        List<String> stringFields =
                fields.stream().map(WritableElement::write).collect(Collectors.toList());
        if (getType() == JavaFileType.ENUM) {
            stringFields.addAll(computeEnumFieldStrings());
        }
        
        innerClasses.forEach(inner -> imports.addAll(inner.getImports()));
        
        List<KV<String, Object>> kvs = new ArrayList<>();
        kvs.add(KV.of("isEnum", typeIs(ENUM)));
        kvs.add(KV.of("isClass", typeIs(CLASS) || typeIs(ABSTRACT_CLASS)));
        kvs.add(KV.of("stringFields", stringFields));
        kvs.add(KV.of("enumStrings", computeEnumStrings()));
        kvs.add(KV.of("innerClassStrings", computeInnerClassStrings()));
        return Formatter.fromTemplate("file", this, kvs);
    }
    
    public List<Field> computeEnumFields() {
        List<Field> fields = new ArrayList<>();
        for (String variableName : enumVariables) {
            Field field = new Field("String", variableName);
            fields.add(field);
        }
        return fields;
    }
    
    public List<String> computeEnumFieldStrings() {
        return computeEnumFields().stream().map(Field::write).collect(Collectors.toList());
    }
    
    public Map<Consumer<String>, String> getRewritableTypes() {
        Map<Consumer<String>, String> rewritable = new HashMap<>();
        addIfNonNull(rewritable, header::setGenericSuper, getGenericSuper());
        for (Field field : fields) {
            addIfNonNull(rewritable, field::setType, field.getType());
            addIfNonNull(rewritable, field::setGeneric, field.getGeneric());
        }
        for (String implementing : getIsTypes()) {
            addIfNonNull(rewritable, s -> {
                removeIsType(implementing);
                addIsType(s);
            }, implementing);
        }
        return rewritable;
    }
    
    private <K, V> void addIfNonNull(Map<K, V> map, K k, V v) {
        if (k != null) {
            map.put(k, v);
        }
    }
    
    private List<String> computeEnumStrings() {
        return enumElements.stream().map(WritableElement::write).collect(Collectors.toList());
    }
    
    private List<String> computeInnerClassStrings() {
        return innerClasses.stream().map(WritableElement::write).collect(Collectors.toList());
    }
    
    public boolean hasInnerClasses() {
        return !getInnerClasses().isEmpty();
    }
}
