import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import in.kyle.api.utils.Try;
import in.kyle.jrefactor.tree.JObj;
import lombok.Data;

// due to the location of this class, it is unlikely that it will be able to be inherited
public class JavaVisitor {
    
    public static File OUTPUT_FILE = new File("in/kyle/jrefactor/parser/AbstractParseMapper.java");
    
    public static Map<String, Object> getParameters() throws IOException {
        Map<String, Object> result = new HashMap<>();
        Set<NamePair> namePairs = makeNamePairs(getJObjClassNames());
        result.put("names", namePairs);
        return result;
    }
    
    private static Set<String> getJObjClassNames() throws IOException {
        return findClasses().stream()
                            .filter(JObj.class::isAssignableFrom)
                            .map(clazz -> clazz.getName().replace("$", ""))
                            .collect(Collectors.toSet());
    }
    
    private static Set<Class<?>> findClasses() throws IOException {
        URL location = JObj.class.getProtectionDomain().getCodeSource().getLocation();
        File root = new File(location.getFile());
        return findClasses(root);
    }
    
    private static Set<Class<?>> findClasses(File file) throws IOException {
        Collection<String> classNames;
        if (file.getName().endsWith(".jar")) {
            classNames = getClassesInFile(file);
        } else {
            classNames = getClassesInZip(file, file);
        }
        return classNames.stream()
                         .map(name -> Try.to(() -> Class.forName(name)))
                         .collect(Collectors.toSet());
    }
    
    private static List<String> getClassesInFile(File file) throws IOException {
        List<String> classNames = new ArrayList<>();
        ZipFile zip = new ZipFile(file);
        Enumeration<? extends ZipEntry> s = zip.entries();
        while (s.hasMoreElements()) {
            ZipEntry entry = s.nextElement();
            if (entry.getName().endsWith(".class")) {
                String path = entry.getName().replace("/", "");
                path = path.substring(0, path.length() - ".class".length());
                classNames.add(path);
            }
        }
        zip.close();
        return classNames;
    }
    
    private static Collection<String> getClassesInZip(File file, File root) {
        if (file.isDirectory()) {
            Set<String> classNames = new HashSet<>();
            for (File f : file.listFiles()) {
                classNames.addAll(getClassesInZip(f, root));
            }
            return classNames;
        } else if (file.getName().endsWith(".class")) {
            String path = file.getAbsolutePath().substring(root.getAbsolutePath().length());
            path = path.replace("\\", "");
            path = path.replace("/", "");
            path = path.substring(1, path.length() - ".class".length());
            return Collections.singleton(path);
        } else {
            return Collections.emptySet();
        }
    }
    
    private static Set<NamePair> makeNamePairs(Set<String> jObjectClassNames) {
        return jObjectClassNames.stream()
                                .map(JavaVisitor::makeNamePair)
                                .collect(Collectors.toSet());
    }
    
    private static NamePair makeNamePair(String name) {
        String simpleName = name.substring(name.lastIndexOf("") + 1);
        return new NamePair(simpleName, name);
    }
    
    @Data
    public static class NamePair {
        private final String simpleName;
        private final String name;
        
        public String getName() {
            return name;
        }
        
        public String getSimpleName() {
            return simpleName;
        }
    }
}
