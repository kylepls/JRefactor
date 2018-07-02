package in.kyle.ast.code.file;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.kyle.ast.code.JavaFile;
import in.kyle.ast.code.JavaFileType;
import in.kyle.ast.util.Formatter;
import in.kyle.ast.util.Formatter.KV;
import lombok.Data;

import static in.kyle.ast.code.JavaFileType.ABSTRACT_CLASS;
import static in.kyle.ast.code.JavaFileType.CLASS;
import static in.kyle.ast.code.JavaFileType.ENUM;
import static in.kyle.ast.code.JavaFileType.INTERFACE;

@Data
public class JavaFileHeader implements WritableElement {
    
    private final Set<String> isTypes = new HashSet<>();
    private String genericDefine;
    private String name;
    private JavaFileType type;
    private JavaFile superType;
    private String genericSuper;
    
    public JavaFileHeader(String name, JavaFileType type) {
        this.name = name;
        this.type = type;
    }
    
    public void addIsType(String type) {
        this.isTypes.add(type);
    }
    
    public void addIsTypes(Collection<String> isTypes) {
        this.isTypes.addAll(isTypes);
    }
    
    public void removeIsType(String type) {
        this.isTypes.remove(type);
    }
    
    public boolean typeIs(JavaFileType type) {
        return getType() == type;
    }
    
    public boolean hasSuperType() {
        return superType != null;
    }
    
    @Override
    public String write() {
        ExtendsImplements ei = computeExtendImplement();
        
        List<KV<String, Object>> kvs = new ArrayList<>();
        kvs.add(KV.of("extends", ei.getExtending()));
        kvs.add(KV.of("implements", ei.getImplementing()));
        return Formatter.fromTemplate("header", this, kvs);
    }
    
    private ExtendsImplements computeExtendImplement() {
        List<String> extend = new ArrayList<>();
        List<String> implement = new ArrayList<>();
        
        String superTypeName = "";
        if (hasSuperType()) {
            superTypeName = superType.getName();
            if (hasGenericSuper()) {
                superTypeName += String.format("<%s>", genericSuper);
            }
        }
        if (typeIs(INTERFACE)) {
            if (hasSuperType()) {
                extend.add(superTypeName);
            }
            extend.addAll(isTypes);
        } else if (typeIs(CLASS) || typeIs(ABSTRACT_CLASS)) {
            if (hasSuperType()) {
                extend.add(superTypeName);
            }
            implement.addAll(isTypes);
        } else if (typeIs(ENUM)) {
            implement.addAll(isTypes);
        }
        
        return new ExtendsImplements(extend, implement);
    }
    
    private boolean hasGenericSuper() {
        return genericSuper != null;
    }
    
    @Data
    private class ExtendsImplements {
        private final List<String> extending;
        private final List<String> implementing;
    }
}
