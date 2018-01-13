package in.kyle.jrefactor.refactor.gen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import in.kyle.jrefactor.tree.JObject;
import lombok.Data;

// due to the location of this class, it is unlikely that it will be able to be inherited
public class JObjectNames {
    
    private static List<String> getJObjectClassNames() throws IOException {
        return findClasses().stream()
                            .filter(JObject.class::isAssignableFrom)
                            .map(clazz -> clazz.getName().replace("$", "."))
                            .collect(Collectors.toList());
    }
    
    private static List<Class<?>> findClasses() throws IOException {
        URL location = JObject.class.getProtectionDomain().getCodeSource().getLocation();
        File root = new File(location.getFile());
        return findClasses(root);
    }
    
    private static List<Class<?>> findClasses(File file) throws IOException {
        return getClassesInFile(file).stream().map(name -> {
            try {
                return Class.forName(name);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
    
    private static List<String> getClassesInFile(File file) throws IOException {
        List<String> classNames = new ArrayList<>();
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
    
    public Map<String, Object> getClassNames() throws IOException {
        Map<String, Object> result = new HashMap<>();
        List<String> jObjectClassNames = getJObjectClassNames();
        List<NamePair> namePairs = makeNamePairs(jObjectClassNames);
        result.put("names", namePairs);
        return result;
    }
    
    private List<NamePair> makeNamePairs(List<String> jObjectClassNames) {
        return jObjectClassNames.stream().map(this::makeNamePair).collect(Collectors.toList());
    }
    
    private NamePair makeNamePair(String name) {
        String simpleName = name.substring(name.lastIndexOf(".") + 1);
        return new NamePair(simpleName, name);
    }
    
    @Data
    private static class NamePair {
        private final String simpleName;
        private final String name;
    }
}
