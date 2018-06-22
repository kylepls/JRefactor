package in.kyle.ast.code.file;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import in.kyle.ast.code.JavaFile;
import in.kyle.ast.code.JavaFileType;
import in.kyle.ast.util.Formatter;
import lombok.Data;

@Data
public class JavaFileHeader implements WritableElement {
    
    private final Set<String> implementingTypes = new HashSet<>();
    private String genericDefine;
    private String genericSuper;
    private String name;
    private JavaFileType type;
    private JavaFile superType;
    
    public void addImplementingType(String type) {
        implementingTypes.add(type);
    }
    
    @Override
    public String write() {
        String extendsString = computeExtendsString();
        Collection<String> implementing = computeImplementing();
        Object[] kv = new Object[]{"extends", extendsString, "implements", implementing};
        return Formatter.fromTemplate("header", this, kv);
    }
    
    private String computeExtendsString() {
        if (isExtendingType()) {
            return superType.getName();
        } else {
            return null;
        }
    }
    
    private Collection<String> computeImplementing() {
        Collection<String> implementing = new HashSet<>(implementingTypes);
        if (!isExtendingType() && hasSuperType()) {
            String superString = superType.getName();
            if (genericSuper != null) {
                superString += "<" + genericSuper + ">";
            }
            implementing.add(superString);
        }
        return implementing;
    }
    
    private boolean isExtendingType() {
        if (hasSuperType()) {
            if (superType.getHeader().getType() == JavaFileType.CLASS &&
                type != JavaFileType.INTERFACE) {
                // Class extending a class
                return true;
            } else if (superType.getHeader().getType() == JavaFileType.INTERFACE &&
                       type == JavaFileType.INTERFACE) {
                // interface extending interface
                return true;
            }
        }
        return false;
    }
    
    private boolean hasSuperType() {
        return superType != null;
    }
}
