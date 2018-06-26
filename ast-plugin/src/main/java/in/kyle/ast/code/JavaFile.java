package in.kyle.ast.code;

import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.util.*;
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

@Data
public class JavaFile implements WritableElement {
    
    @Delegate(excludes = WritableElement.class)
    private final JavaFileHeader header;
    private final Set<String> imports = new HashSet<>();
    private final Set<Field> fields = new OrderedHashSet<>();
    private final Set<JavaFile> innerClasses = new OrderedHashSet<>();
    private final Set<EnumElement> enumElements = new OrderedHashSet<>();
    private final Set<String> methods = new OrderedHashSet<>();
    private String packageName;
    private boolean innerClass;
    
    public JavaFile(String name, JavaFileType type) {
        this.header = new JavaFileHeader(name, type);
    }
    
    public void addInnerClass(JavaFile file) {
        innerClasses.add(file);
        file.setInnerClass(true);
    }
    
    public void addField(Field field) {
        fields.add(field);
    }
    
    @Override
    public String write() {
        List<String> stringFields =
                fields.stream().map(WritableElement::write).collect(Collectors.toList());
        if (getType() == JavaFileType.ENUM) {
            stringFields.addAll(computeEnumFields());
        }
        
        List<KV<String, Object>> kvs = new ArrayList<>();
        kvs.add(KV.of("isEnum", getType() == JavaFileType.ENUM));
        kvs.add(KV.of("isClass", getType() == JavaFileType.CLASS));
        kvs.add(KV.of("stringFields", stringFields));
        kvs.add(KV.of("enumStrings", computeEnumStrings()));
        kvs.add(KV.of("innerClassStrings", computeInnerClassStrings()));
        return Formatter.fromTemplate("file", this, kvs);
    }
    
    private List<String> computeEnumFields() {
        List<String> fields = new ArrayList<>();
        if (enumElements.size() > 0) {
            int size = enumElements.iterator().next().getValues().size();
            for (int j = 0; j < size; j++) {
                Field field = new Field("final String", null, "value" + (j + 1), null);
                fields.add(field.write());
            }
        }
        return fields;
    }
    
    public Map<Consumer<String>, String> getRewritableTypes() {
        Map<Consumer<String>, String> rewritable = new HashMap<>();
        addIfNonNull(rewritable, header::setGenericSuper, getGenericSuper());
        for (Field field : fields) {
            addIfNonNull(rewritable, field::setType, field.getType());
            addIfNonNull(rewritable, field::setGeneric, field.getGeneric());
        }
        for (String implementing : getImplementingTypes()) {
            addIfNonNull(rewritable, s -> {
                getImplementingTypes().remove(implementing);
                getImplementingTypes().add(s);
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
}
