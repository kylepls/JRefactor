package in.kyle.stgen;

import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.DefaultDependencyResolutionRequest;
import org.apache.maven.project.DependencyResolutionException;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.graph.Dependency;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import in.kyle.api.utils.Try;
import lombok.Data;
import lombok.experimental.UtilityClass;

import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

@UtilityClass
public class FileExecutor {
    
    public static STParameters getParameters(File file, String className, STMojo mojo)
            throws MojoExecutionException, ClassNotFoundException, MalformedURLException,
                   DependencyResolutionException {
        compileFile(file, mojo);
        Class<?> providerClass = loadFile(className, mojo);
        return getProperties(providerClass);
    }
    
    private static STParameters getProperties(Class<?> providerClass) {
        return new STParameters(getRelativeFile(providerClass), getParameters(providerClass));
    }
    
    private static File getRelativeFile(Class<?> clazz) {
        return (File) Try.to(() -> clazz.getDeclaredField("OUTPUT_FILE").get(null));
    }
    
    
    private static Map<String, Object> getParameters(Class<?> clazz) {
        return (Map<String, Object>) Try.to(() -> clazz.getMethod("getParameters").invoke(null));
    }
    
    private static Class<?> loadFile(String className, STMojo mojo)
            throws DependencyResolutionException, MalformedURLException, ClassNotFoundException {
        DefaultDependencyResolutionRequest request =
                new DefaultDependencyResolutionRequest(mojo.getProject(),
                                                       mojo.getSession().getRepositorySession());
        Set<URL> urls = mojo.getDependenciesResolver()
                            .resolve(request)
                            .getDependencies()
                            .stream()
                            .map(Dependency::getArtifact)
                            .map(artifact -> Try.to(() -> artifact.getFile()
                                                                  .getAbsoluteFile()
                                                                  .toURI()
                                                                  .toURL()))
                            .collect(Collectors.toSet());
        String outputDirectory = mojo.getProject().getBuild().getOutputDirectory();
        urls.add(new File(outputDirectory).toURI().toURL());
        
        DefaultDependencyResolutionRequest resolutionRequest =
                new DefaultDependencyResolutionRequest(mojo.getProject(),
                                                       mojo.getSession().getRepositorySession());
        for (Dependency dependency : mojo.getDependenciesResolver()
                                         .resolve(resolutionRequest)
                                         .getDependencies()) {
            Artifact artifact = dependency.getArtifact();
            mojo.getLog()
                .info("Add dependency " + artifact.getArtifactId() + " " +
                      artifact.getFile().getAbsolutePath());
            urls.add(artifact.getFile().toURI().toURL());
        }
    
        ClassLoader loader = URLClassLoader.newInstance(urls.toArray(new URL[urls.size()]),
                                                        FileExecutor.class.getClassLoader());
        return loader.loadClass(className);
    }
    
    private static void compileFile(File file, STMojo mojo) throws MojoExecutionException {
        MavenProject project = mojo.getProject();
        project.addCompileSourceRoot(file.getParentFile().getPath());
        runCompilerPlugin(file, mojo);
    }
    
    private static void runCompilerPlugin(File file, STMojo mojo) throws MojoExecutionException {
        mojo.getLog().info("Compiling " + file.getAbsolutePath());
        Plugin plugin = plugin(groupId("org.apache.maven.plugins"),
                               artifactId("maven-compiler-plugin"),
                               version("3.7.0"));
        executeMojo(plugin,
                    goal("compile"),
                    configuration(element(name("source"), "1.8"),
                                  element(name("target"), "1.8"),
                                  element(name("includes"), element("include", "*.java"))),
                    executionEnvironment(mojo.getProject(),
                                         mojo.getSession(),
                                         mojo.getPluginManager()));
    }
    
    @Data
    public static class STParameters {
        private final File relativeFile;
        private final Map<String, Object> parameters;
    }
}
