package in.kyle.ast.code.file;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class JavaFileHeader {
    
    private final Set<String> isTypes = new HashSet<>();
    private String genericDefine;
    private String name;
    private JavaFileType type;
    private JavaFile superType;
    private String genericSuper;
    private boolean concrete;
    
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
    
    public boolean hasGeneric() {
        return hasSuperType() || hasGenericDefine();
    }
    
    public boolean hasGenericDefine() {
        return genericDefine != null;
    }
}
