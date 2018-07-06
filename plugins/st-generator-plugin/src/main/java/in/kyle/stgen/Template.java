package in.kyle.stgen;

import org.apache.maven.plugins.annotations.Parameter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STRawGroupDir;
import org.stringtemplate.v4.StringRenderer;

import java.io.File;
import java.util.Map;

import in.kyle.api.utils.Try;
import lombok.Data;
import lombok.Getter;

@Getter
public class Template {
    
    @Parameter(name = "templateName", required = true)
    private String templateName;
    
    @Parameter(name = "className", required = true)
    private String className;
    
    @Parameter(name = "outputPackage")
    private String outputPackage = "";
    
    @Parameter(name = "outputName", required = true)
    private String outputName;
    
    @Parameter(name = "multipleFile")
    private boolean multipleFiles = false;
    
    Map<String, Object> getParameters(STMojo mojo) throws Exception {
        Class<?> providerClass = ClassUtils.loadFile(className, mojo);
        return getProperties(providerClass);
    }
    
    Map<String, Map<String, Object>> getFiles(STMojo mojo) throws Exception {
        Class<?> providerClass = ClassUtils.loadFile(className, mojo);
        return getFiles(providerClass);        
    }
    
    ST getSt(File templateDirectory) {
        STRawGroupDir rawGroupDir = new STRawGroupDir(templateDirectory.getAbsolutePath());
        rawGroupDir.registerRenderer(String.class, new StringRenderer());
        return rawGroupDir.getInstanceOf(templateName);
    }
    
    File getOutputFile(File targetDirectory) {
        return new File(targetDirectory,
                        packageToPath(outputPackage) + File.separator + outputName);
    }
    
    private String packageToPath(String packageName) {
        return packageName.replace(".", File.separator);
    }
    
    private Map<String, Object> getProperties(Class<?> providerClass) {
        return getParameters(providerClass);
    }
    
    private Map<String, Object> getParameters(Class<?> clazz) {
        return (Map<String, Object>) Try.to(() -> clazz.getMethod("getParameters").invoke(null));
    }
    
    private Map<String, Map<String, Object>> getFiles(Class<?> clazz) {
        return (Map<String, Map<String, Object>>) Try.to(() -> clazz.getMethod("getFiles").invoke(null));
    }
    
    @Data
    static class STParameters {
        private final File relativeFile;
        private final Map<String, Object> parameters;
    }
}
