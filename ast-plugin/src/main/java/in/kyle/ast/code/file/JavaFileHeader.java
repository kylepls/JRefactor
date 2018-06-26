package in.kyle.ast.code.file;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.kyle.ast.code.JavaFile;
import in.kyle.ast.code.JavaFileType;
import in.kyle.ast.util.Formatter;
import in.kyle.ast.util.Formatter.KV;
import lombok.Data;

@Data
public class JavaFileHeader implements WritableElement {
    
    private final Set<String> implementingTypes = new HashSet<>();
    private String genericDefine;
    private String genericSuper;
    private String name;
    private JavaFileType type;
    private JavaFile superType;
    
    public JavaFileHeader(String name, JavaFileType type) {
        this.name = name;
        this.type = type;
    }
    
    @Override
    public String write() {
        String extendsString = getExtendingType();
        Collection<String> implementing = computeImplementing(extendsString != null);
        List<KV<String, Object>> kvs = new ArrayList<>();
        kvs.add(KV.of("extends", extendsString));
        kvs.add(KV.of("implements", implementing));
        return Formatter.fromTemplate("header", this, kvs);
    }
    
    private Collection<String> computeImplementing(boolean isExtendingType) {
        if (type != JavaFileType.INTERFACE) {
            Collection<String> implementing = new HashSet<>(implementingTypes);
            boolean notExtendingType = !isExtendingType;
            if (notExtendingType && hasSuperType()) {
                String superString = superType.getName();
                if (genericSuper != null) {
                    superString += "<" + genericSuper + ">";
                }
                implementing.add(superString);
            }
            return implementing;
        } else {
            return Collections.emptyList();
        }
    }
    
    private String getExtendingType() {
        if (hasSuperType()) {
            if (superType() == JavaFileType.CLASS && type != JavaFileType.INTERFACE) {
                // Class extending a class
                return superType.getName();
            } else if (superType() == JavaFileType.INTERFACE && type == JavaFileType.INTERFACE) {
                // interface extending interface
                return superType.getName();
            }
        } else if (type == JavaFileType.INTERFACE && implementingTypes.size() != 0) {
            return implementingTypes.iterator().next();
        }
        return null;
    }
    
    private JavaFileType superType() {
        return superType.getType();
    }
    
    private boolean hasSuperType() {
        return superType != null;
    }
}
