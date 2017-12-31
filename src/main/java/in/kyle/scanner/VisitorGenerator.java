package in.kyle.scanner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.kyle.parser.JObject;

public class VisitorGenerator {
    
    private static final String TARGET_PACKAGE = "in.kyle.scanner";
    private static final String TARGET_INTERFACE_NAME = "JavaVisitor";
    private static final String TARGET_CLASS_NAME = "JavaBaseVisitor";
    private static final File TARGET_INTERFACE = new File(
            "src/main/java/" + TARGET_PACKAGE.replace(".", "/") + "/" + TARGET_INTERFACE_NAME +
            ".java");
    private static final File TARGET_CLASS = new File(
            "src/main/java/" + TARGET_PACKAGE.replace(".", "/") + "/" + TARGET_CLASS_NAME +
            ".java");
    private static File root;
    
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        List<Class<?>> classes = findClasses();
        classes.removeIf(clazz -> !JObject.class.isAssignableFrom(clazz) ||
                                  !Modifier.isPublic(clazz.getModifiers()));
        writeInterface(classes);
        writeClass(classes);
    }
    
    private static void writeClass(List<Class<?>> classes) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(TARGET_PACKAGE).append(";\n");
        builder.append("public class ")
               .append(TARGET_CLASS_NAME)
               .append("<T> extends AbstractJObjectVisitor<T> implements ")
               .append(TARGET_INTERFACE_NAME)
               .append("<T> {\n");
        for (Class<?> aClass : classes) {
            builder.append("    public T visit")
                   .append(aClass.getSimpleName())
                   .append("(")
                   .append(aClass.getName().replace("$", "."))
                   .append(" object) {\n");
            builder.append("        return visitChildren(object);\n    }\n");
        }
        builder.append("\n}");
        Files.write(TARGET_CLASS.toPath(), builder.toString().getBytes());
    }
    
    private static void writeInterface(List<Class<?>> classes) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(TARGET_PACKAGE).append(";\n");
        builder.append("public interface ").append(TARGET_INTERFACE_NAME).append("<T> {\n");
        
        for (Class<?> aClass : classes) {
            builder.append("    ");
            builder.append("T visit")
                   .append(aClass.getSimpleName())
                   .append("(")
                   .append(aClass.getName().replace("$", "."))
                   .append(" object);\n");
        }
        builder.append("\n}");
        
        Files.write(Paths.get(TARGET_INTERFACE.getPath()), builder.toString().getBytes());
    }
    
    private static List<Class<?>> findClasses() throws ClassNotFoundException {
        URL location = VisitorGenerator.class.getProtectionDomain().getCodeSource().getLocation();
        root = new File(location.getFile());
        System.out.println(root.getAbsolutePath());
        return findClasses(root);
    }
    
    private static List<Class<?>> findClasses(File file) throws ClassNotFoundException {
        if (file.isDirectory()) {
            List<Class<?>> acc = new ArrayList<>();
            for (File file1 : file.listFiles()) {
                List<Class<?>> classes = findClasses(file1);
                acc.addAll(classes);
            }
            return acc;
        } else {
            String path = file.getAbsolutePath().substring(root.getAbsolutePath().length() + 1);
            if (!path.contains(".class")) {
                return Collections.emptyList();
            }
            String replace = path.replace(File.separator, ".");
            System.out.println(replace);
            replace = replace.substring(0, replace.lastIndexOf("."));
            return Collections.singletonList(Class.forName(replace));
        }
    }
}
