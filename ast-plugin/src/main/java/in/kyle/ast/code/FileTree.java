package in.kyle.ast.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class FileTree {
    
    private final Map<String, JavaFile> files = new HashMap<>();
    private CodeModifier modifier;
    
    public void addFile(String path, JavaFile file) {
        files.put(path, file);
    }
    
    public void addChildren(String prefix, FileTree tree) {
        tree.getFiles().forEach((path, file) -> files.put(prefix + path, file));
        if (modifier == null && tree.getModifier() != null) {
            setModifier(tree.getModifier());
        }
    }
    
    public void write(String filePath) throws IOException {
        updatePaths(filePath);
        createImportMappings();
        files.values().forEach(modifier::process);
        for (Map.Entry<String, JavaFile> entry : files.entrySet()) {
            Path path = Paths.get(entry.getKey());
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            JavaFile javaFile = entry.getValue();
            Files.write(path, javaFile.write().getBytes());
        }
    }
    
    private void createImportMappings() {
        files.values()
             .forEach(file -> modifier.getImportMappings().put(file.getName(), createImport(file)));
    }
    
    private String createImport(JavaFile file) {
        if (file.getPackageName() != null) {
            return file.getPackageName() + "." + file.getName();
        } else {
            return file.getName();
        }
    }
    
    private void updatePaths(String filePath) {
        files.forEach((path, file) -> file.setPackageName(getPackageName(path, filePath)));
    }
    
    private String getPackageName(String path, String directory) {
        String packageName = path.substring(directory.length() + 1)
                                 .replace(File.separator, ".")
                                 .replace(".java", "");
        if (packageName.contains(".")) {
            packageName = packageName.substring(0, packageName.lastIndexOf("."));
        } else {
            packageName = null;
        }
        return packageName;
    }
}
