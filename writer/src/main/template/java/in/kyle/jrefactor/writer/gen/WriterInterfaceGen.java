package in.kyle.jrefactor.writer.gen;

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

import in.kyle.jrefactor.parser.JObject;

public class WriterInterfaceGen {
    
    private File root;
    
    public Map<String, Object> getProperties() throws IOException {
        Map<String, Object> result = new HashMap<>();
        List<String> members = findClasses().stream()
                                            .filter(JObject.class::isAssignableFrom)
                                            .map(clazz -> clazz.getName().replace("$", "."))
                                            .collect(Collectors.toList());
        result.put("members", members);
        return result;
    }
    
    private List<Class<?>> findClasses() throws IOException {
        URL location = JObject.class.getProtectionDomain().getCodeSource().getLocation();
        root = new File(location.getFile());
        return findClasses(root);
    }
    
    private List<Class<?>> findClasses(File file) throws IOException {
        return getClassesInFile(file).stream().map(name -> {
            try {
                return Class.forName(name);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
    
    private List<String> getClassesInFile(File file) throws IOException {
        List<String> classNames = new ArrayList<>();
        ZipFile zip = new ZipFile(file);
        Enumeration<? extends ZipEntry> s = zip.entries();
        while (s.hasMoreElements()) {
            ZipEntry entry = s.nextElement();
            if (entry.getName().endsWith(".class")) {
                String path = entry.getName().replace("/", ".");
                path = path.substring(0, path.length() - ".class".length());
                System.out.println(entry.getName());
                classNames.add(path);
            }
        }
        zip.close();
        return classNames;
    }
}
