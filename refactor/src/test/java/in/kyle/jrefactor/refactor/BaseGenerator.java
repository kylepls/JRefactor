package in.kyle.jrefactor.refactor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.kyle.jrefactor.parser.JObject;

// TODO: 1/6/2018 cleanup 
public class BaseGenerator {
    
    private static final String TARGET_PACKAGE = "in.kyle.jrefactor.refactor";
    private static final String VISITOR_TARGET_INTERFACE_NAME = "JavaVisitor";
    private static final String VISITOR_TARGET_CLASS_NAME = "JavaBaseVisitor";
    private static final String LISTENER_TARGET_INTERFACE_NAME = "JavaListener";
    private static final String LISTENER_TARGET_CLASS_NAME = "JavaBaseListener";
    private static final File TARGET_VISITOR_INTERFACE = new File(
            "refactor/src/main/java/" + TARGET_PACKAGE.replace(".", "/") + "/" +
            VISITOR_TARGET_INTERFACE_NAME + ".java");
    private static final File TARGET_VISITOR_CLASS = new File(
            "refactor/src/main/java/" + TARGET_PACKAGE.replace(".", "/") + "/" +
            VISITOR_TARGET_CLASS_NAME + ".java");
    private static final File TARGET_LISTENER_INTERFACE = new File(
            "refactor/src/main/java/" + TARGET_PACKAGE.replace(".", "/") + "/" +
            LISTENER_TARGET_INTERFACE_NAME + ".java");
    private static final File TARGET_LISTENER_CLASS = new File(
            "refactor/src/main/java/" + TARGET_PACKAGE.replace(".", "/") + "/" +
            LISTENER_TARGET_CLASS_NAME + ".java");
    private static File root;
    
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        List<Class<?>> classes = findClasses();
        classes.removeIf(clazz -> !JObject.class.isAssignableFrom(clazz) ||
                                  !Modifier.isPublic(clazz.getModifiers()));
        writeVisitorInterface(classes);
        writeVisitorClass(classes);
        
        writeListener(classes);
        writeListenerBase(classes);
    }
    
    private static void writeListenerBase(List<Class<?>> classes) throws IOException {
        StringBuilder baseBuilder = new StringBuilder();
        baseBuilder.append("package ").append(TARGET_PACKAGE).append(";\n");
        
        baseBuilder.append("public class ")
                        .append(LISTENER_TARGET_CLASS_NAME)
                        .append(" extends AbstractJObjectListener implements ")
                        .append(LISTENER_TARGET_INTERFACE_NAME)
                        .append(" {\n");
        
        for (Class<?> aClass : classes) {
            String headerString =
                    aClass.getSimpleName() + "(" + aClass.getName().replace("$", ".") +
                    " object) {";
            baseBuilder.append("    public void enter").append(headerString).append("super.enterChildren(object);").append("}\n");
            baseBuilder.append("    public void exit").append(headerString).append("}\n");
        }
        baseBuilder.append("\n}");
        
        Files.write(TARGET_LISTENER_CLASS.toPath(), baseBuilder.toString().getBytes());
    }
    
    private static void writeListener(List<Class<?>> classes) throws IOException {
        StringBuilder interfaceBuilder = new StringBuilder();
        interfaceBuilder.append("package ").append(TARGET_PACKAGE).append(";\n");
        
        interfaceBuilder.append("public interface ")
                        .append(LISTENER_TARGET_INTERFACE_NAME)
                        .append(" {\n");
        
        for (Class<?> aClass : classes) {
            String headerString =
                    aClass.getSimpleName() + "(" + aClass.getName().replace("$", ".") + " object)";
            interfaceBuilder.append("    void enter").append(headerString).append(";\n");
            interfaceBuilder.append("    void exit").append(headerString).append(";\n");
            
        }
        interfaceBuilder.append("\n}");
        
        Files.write(TARGET_LISTENER_INTERFACE.toPath(), interfaceBuilder.toString().getBytes());
    }
    
    private static void writeVisitorClass(List<Class<?>> classes) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(TARGET_PACKAGE).append(";\n");
        builder.append("public class ")
               .append(VISITOR_TARGET_CLASS_NAME)
               .append("<T> extends AbstractJObjectVisitor<T> implements ")
               .append(VISITOR_TARGET_INTERFACE_NAME)
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
        Files.write(TARGET_VISITOR_CLASS.toPath(), builder.toString().getBytes());
    }
    
    private static void writeVisitorInterface(List<Class<?>> classes) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(TARGET_PACKAGE).append(";\n");
        builder.append("public interface ").append(VISITOR_TARGET_INTERFACE_NAME).append("<T> {\n");
        
        for (Class<?> aClass : classes) {
            builder.append("    ");
            builder.append("T visit")
                   .append(aClass.getSimpleName())
                   .append("(")
                   .append(aClass.getName().replace("$", "."))
                   .append(" object);\n");
        }
        builder.append("\n}");
        
        Files.write(Paths.get(TARGET_VISITOR_INTERFACE.getPath()), builder.toString().getBytes());
    }
    
    private static List<Class<?>> findClasses() throws ClassNotFoundException {
        URL location = JObject.class.getProtectionDomain().getCodeSource().getLocation();
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
