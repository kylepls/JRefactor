package in.kyle.stgen;

import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.DefaultDependencyResolutionRequest;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.graph.Dependency;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;
import java.util.stream.Collectors;

import in.kyle.api.utils.Try;
import lombok.experimental.UtilityClass;

import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

@UtilityClass
class ClassUtils {
    
    static void compileFiles(File directory, STMojo mojo) throws MojoExecutionException {
        MavenProject project = mojo.getProject();
        project.addCompileSourceRoot(directory.getAbsolutePath());
        runCompilerPlugin(directory, mojo);
    }
    
    static Class<?> loadFile(String className, STMojo mojo) throws Exception {
        DefaultDependencyResolutionRequest request = createResolutionRequest(mojo);
        Set<URL> urls = mojo.getDependenciesResolver()
                            .resolve(request)
                            .getDependencies()
                            .stream()
                            .map(Dependency::getArtifact)
                            .map(artifact -> fileToUrl(artifact.getFile()))
                            .peek(url -> mojo.getLog().info("Add dependency " + url))
                            .collect(Collectors.toSet());
        
        String outputDirectory = mojo.getProject().getBuild().getOutputDirectory();
        urls.add(new File(outputDirectory).toURI().toURL());
        
        ClassLoader loader = URLClassLoader.newInstance(urls.toArray(new URL[0]),
                                                        FileExecutor.class.getClassLoader());
        return loader.loadClass(className);
    }
    
    private static DefaultDependencyResolutionRequest createResolutionRequest(STMojo mojo) {
        return new DefaultDependencyResolutionRequest(mojo.getProject(),
                                                      mojo.getSession().getRepositorySession());
    }
    
    private static URL fileToUrl(File file) {
        return Try.to(() -> file.getAbsoluteFile().toURI().toURL());
    }
    
    private static void runCompilerPlugin(File file, STMojo mojo) throws MojoExecutionException {
        mojo.getLog().info("Compiling " + file.getAbsolutePath());
        Plugin plugin = plugin(groupId("org.apache.maven.plugins"),
                               artifactId("maven-compiler-plugin"),
                               version("3.7.0"));
        Element[] excludes = mojo.getCompileExcludes()
                                 .stream()
                                 .peek(s -> mojo.getLog().info("Exclude " + s))
                                 .map(s -> element("exclude", s))
                                 .toArray(Element[]::new);
        executeMojo(plugin,
                    goal("compile"),
                    configuration(element(name("source"), "1.8"),
                                  element(name("target"), "1.8"),
                                  element(name("includes"), element("include", "*.java")),
                                  element(name("excludes"), (Element[]) excludes)),
                    executionEnvironment(mojo.getProject(),
                                         mojo.getSession(),
                                         mojo.getPluginManager()));
    }
}
