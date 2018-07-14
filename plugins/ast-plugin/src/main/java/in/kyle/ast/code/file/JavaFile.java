package in.kyle.ast.code.file;

import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import in.kyle.ast.util.StringTemplate;
import lombok.Data;
import lombok.experimental.Delegate;

@Data
public class JavaFile {
    
    @Delegate
    private final JavaFileHeader header;
    private final Set<JavaField> fields = new OrderedHashSet<>();
    private final Set<JavaFile> innerClasses = new OrderedHashSet<>();
    private final Set<JavaEnumElement> enumElements = new OrderedHashSet<>();
    private final Set<String> enumVariables = new OrderedHashSet<>();
    private final List<String> bodyElements = new ArrayList<>();
    private String packageName;
    private boolean innerClass;
    
    public JavaFile(String name, JavaFileType type) {
        this.header = new JavaFileHeader(name, type);
    }
    
    public void addBodyElement(String string) {
        bodyElements.add(string);
    }
    
    public void removeBodyElement(String string) {
        bodyElements.remove(string);
    }
    
    public void addEnumVariable(String variable) {
        enumVariables.add(variable);
    }
    
    public void addInnerClass(JavaFile file) {
        innerClasses.add(file);
        file.setInnerClass(true);
    }
    
    public void addField(JavaField field) {
        fields.add(field);
    }
    
    public boolean hasPackage() {
        return packageName != null;
    }
    
    public boolean hasGenericSuper() {
        return getGenericSuper() != null;
    }
    
    public void addEnumElement(JavaEnumElement element) {
        enumElements.add(element);
    }
    
    public String write() {
        return StringTemplate.render("file/writeFile", this);
    }
    
    public Map<Consumer<String>, String> getRewritableTypes() {
        Map<Consumer<String>, String> rewritable = new HashMap<>();
        addIfNonNull(rewritable, header::setGenericSuper, getGenericSuper());
        addIfNonNull(rewritable, header::setGenericDefine, getGenericDefine());
        for (JavaField field : fields) {
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
}
