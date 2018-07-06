package in.kyle.ast.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

public class FileSet {
    
    @Getter
    private final Set<JavaFile> files = new HashSet<>();
    
    public void addFiles(Collection<JavaFile> file) {
        files.addAll(file);
    }
    
    public int getFileCount() {
        return files.size();
    }
    
    
    public void write(File root) throws IOException {
        for (JavaFile file : files) {
            File systemFile = new File(root, createRelPath(file));
            systemFile.getParentFile().mkdirs();
            Files.write(systemFile.toPath(), file.write().getBytes());
        }
    }
    
    public static String createRelPath(JavaFile file) {
        String path = "";
        if (file.getPackageName() != null) {
            path += file.getPackageName().replace(".", File.separator) + File.separator;
        }
        path += file.getName() + ".java";
        return path;
    }
}
