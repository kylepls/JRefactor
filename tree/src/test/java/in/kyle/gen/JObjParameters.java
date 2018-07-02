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
    
    public static Map<String, Object> getParameters() throws IOException {
        Map<String, Object> result = new HashMap<>();
        Collection<NamePair> namePairs = makeNamePairs(getJObjClassNames());
        result.put("names", namePairs);
        result.put("concrete", getConcreteClasses());
        return result;
    }
    
    private static Collection<NamePair> getConcreteClasses() throws IOException {
        List<String> classNames = findClasses().stream()
                                               .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                                               .map(JObjParameters::classToString)
                                               .collect(Collectors.toList());
        return makeNamePairs(classNames);
    }
    
    public static void main(String[] args) throws IOException {
        Set<NamePair> names = (Set<NamePair>) getParameters().get("names");
        names.forEach(System.out::println);
    }
    
    private static Collection<String> getJObjClassNames() throws IOException {
        return findClasses().stream()
                            .map(JObjParameters::classToString)
                            .collect(Collectors.toList());
    }
    
    private static String classToString(Class<?> clazz) {
        return clazz.getName().replace("$", ".");
    }
    
    private static Collection<Class<?>> findClasses() throws IOException {
        URL location = JObj.class.getProtectionDomain().getCodeSource().getLocation();
        File root = new File(location.getFile());
        return findClasses(root).stream()
                                .filter(JObj.class::isAssignableFrom)
                                .collect(Collectors.toList());
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
    
    private static Collection<NamePair> makeNamePairs(Collection<String> jObjectClassNames) {
        return jObjectClassNames.stream()
                                .map(JObjParameters::makeNamePair)
                                .collect(Collectors.toList());
    }
    
    private static NamePair makeNamePair(String name) {
        String simpleName = name.substring(name.lastIndexOf(".") + 1);
        return new NamePair(simpleName, name);
    }
    
    @Data
    public static class NamePair implements Comparable<NamePair> {
        private final String simpleName;
        private final String name;
        
        public String getName() {
            return name;
        }
        
        public String getSimpleName() {
            return simpleName;
        }
        
        public Class<?> getClassFromName() {
            return Try.to(() -> Class.forName(name));
        }
        
        @Override
        public int compareTo(NamePair o) {
            return simpleName.compareTo(o.simpleName);
        }
    }
}
