package in.kyle.gen;

import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import in.kyle.api.utils.Try;
import in.kyle.jrefactor.tree.JObj;
import lombok.Data;

public class JObjParameters {
    
    public static void main(String[] args) throws IOException {
        List<ClassInfo> names = (List<ClassInfo>) getParameters().get("names");
        names.forEach(System.out::println);
    }
    
    public static Map<String, Object> getParameters() throws IOException {
        Map<String, Object> result = new HashMap<>();
        Collection<ClassInfo> classInfos = makeClassInfos(findClasses());
        result.put("names", classInfos);
        result.put("concrete", getConcreteClasses());
        return result;
    }
    
    private static Collection<Class<?>> findClasses() throws IOException {
        URL location = JObj.class.getProtectionDomain().getCodeSource().getLocation();
        File root = new File(location.getFile());
        return findClasses(root).stream()
                                .filter(JObj.class::isAssignableFrom)
                                .collect(Collectors.toList());
    }
    
    public static Collection<ClassInfo> getConcreteClasses() throws IOException {
        List<Class<?>> classes = findClasses().stream()
                                              .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                                              .collect(Collectors.toList());
        return makeClassInfos(classes);
    }
    
    private static Collection<Class<?>> findClasses(File file) throws IOException {
        Collection<String> classNames;
        if (file.getName().endsWith(".jar")) {
            System.out.println("Getting classes in jar file " + file.getAbsolutePath());
            classNames = getClassesInJar(file);
        } else {
            System.out.println("Getting classes in directory " + file.getAbsolutePath());
            classNames = getClassesInDirectory(file, file);
        }
        return classNames.stream()
                         .map(name -> Try.to(() -> Class.forName(name)))
                         .collect(Collectors.toList());
    }
    
    private static Collection<String> getClassesInJar(File file) throws IOException {
        Set<String> classNames = new TreeSet<>();
        ZipFile zip = new ZipFile(file);
        Enumeration<? extends ZipEntry> s = zip.entries();
        while (s.hasMoreElements()) {
            ZipEntry entry = s.nextElement();
            if (entry.getName().endsWith(".class")) {
                String path = entry.getName().replace("/", ".");
                path = path.substring(0, path.length() - ".class".length());
                classNames.add(path);
            }
        }
        zip.close();
        return classNames;
    }
    
    private static Collection<String> getClassesInDirectory(File file, File root) {
        if (file.isDirectory()) {
            Set<String> classNames = new OrderedHashSet<>();
            Arrays.stream(Objects.requireNonNull(file.listFiles()))
                  .filter(File::isFile)
                  .forEachOrdered(aFile -> classNames.addAll(getClassesInDirectory(aFile, root)));
            Arrays.stream(Objects.requireNonNull(file.listFiles()))
                  .filter(File::isDirectory)
                  .forEachOrdered(dir -> classNames.addAll(getClassesInDirectory(dir, root)));
            return classNames;
        } else if (file.getName().endsWith(".class")) {
            String path = file.getAbsolutePath().substring(root.getAbsolutePath().length());
            path = path.replace("\\", ".");
            path = path.replace("/", ".");
            path = path.substring(1, path.length() - ".class".length());
            return Collections.singleton(path);
        } else {
            return Collections.emptySet();
        }
    }
    
    private static Collection<ClassInfo> makeClassInfos(Collection<Class<?>> jObjectClassNames) {
        return jObjectClassNames.stream()
                                .map(JObjParameters::makeClassInfo)
                                .collect(Collectors.toList());
    }
    
    private static ClassInfo makeClassInfo(Class<?> clazz) {
        String name = classNameToPackage(clazz);
        String simpleName = name.substring(name.lastIndexOf(".") + 1);
        return new ClassInfo(simpleName, name, clazz);
    }
    
    private static String classNameToPackage(Class<?> clazz) {
        return clazz.getName().replace("$", ".");
    }
    
    @Data
    public static class ClassInfo implements Comparable<ClassInfo> {
        private final String simpleName;
        private final String name;
        private final Class<?> clazz;
        
        public String getName() {
            return name;
        }
        
        public String getSimpleName() {
            return simpleName;
        }
        
        @Override
        public int compareTo(ClassInfo o) {
            return simpleName.compareTo(o.simpleName);
        }
    }
}
