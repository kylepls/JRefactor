package in.kyle.ast.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class FileTree {
    
    @Getter
    private final Map<String, JavaFile> files = new HashMap<>();
    
    public void addFile(String path, JavaFile file) {
        files.put(path, file);
    }
    
    public void addChildren(String prefix, FileTree tree) {
        tree.getFiles().forEach((path, file) -> files.put(prefix + path, file));
    }
    
    public void write(String filePath) throws IOException {
        updatePaths(filePath);
        CodeContext context = new CodeContext();
        addMappings(context);
        for (Map.Entry<String, JavaFile> entry : files.entrySet()) {
            Path path = Paths.get(entry.getKey());
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            JavaFile javaFile = entry.getValue();
            Files.write(path, javaFile.classToString(false, context).getBytes());
        }
    }
    
    private void updatePaths(String filePath) {
        files.forEach((path, file) -> updatePath(path, file, filePath));
    }
    
    private void updatePath(String path, JavaFile file, String filePath) {
        file.setPackageName(getPackageName(path, filePath));
    }
    
    private void addMappings(CodeContext context) {
        files.forEach((path, file) -> context.addImport(file.getName(),
                                                        createImportStatement(file)));
        context.addImport("List", "java.util.List;");
        context.addImport("Set", "java.util.Set;");
        context.addFieldMethod("List",
                               "public void add{singular_name_upper}({diamond_type} {singular_name}) { " +
                               "{field_name}.add({singular_name}); }\n" +
                               "public void remove{singular_name_upper}({diamond_type} {singular_name})" +
                               " { " +
                               "{field_name}.remove({singular_name}); }\n" + "");
        context.addFieldMethod("Set",
                               "public void add{singular_name_upper}({diamond_type} {singular_name}) { " +
                               "{field_name}.add({singular_name}); }\n" +
                               "public void remove{singular_name_upper}({diamond_type} {singular_name})" +
                               " { " +
                               "{field_name}.remove({singular_name}); }\n" + "");
        context.addEnumMethod("public static {type} fromString(String value) { return valueOf(value.toUpperCase()); }");
    }
    
    private String createImportStatement(JavaFile file) {
        return file.getPackageName() + "." + file.getName();
    }
    
    private String getPackageName(String path, String directory) {
        String packageName = path.substring(directory.length())
                                 .replace(File.separator, ".")
                                 .replace(".java", "");
        if (packageName.contains(".")) {
            packageName = packageName.substring(0, packageName.lastIndexOf("."));
        } else {
            packageName = "";
        }
        return packageName;
    }
}
