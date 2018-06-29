package in.kyle.stgen;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectDependenciesResolver;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STRawGroupDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Execute(phase = LifecyclePhase.GENERATE_SOURCES, goal = "generate-sources")
@Mojo(name = "generate-template-sources",
      defaultPhase = LifecyclePhase.GENERATE_SOURCES,
      requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME,
      requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME)
@Data
public class STMojo extends AbstractMojo {
    
    @Parameter(name = "templateDirectory", defaultValue = "${project.basedir}/src/main/template/")
    private File templateDirectory;
    
    @Parameter(name = "targetDirectory",
               defaultValue = "${project.build.directory}/generated-sources/template/")
    private File targetDirectory;
    
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;
    
    @Parameter(property = "session", readonly = true)
    private MavenSession session;
    
    
    @Component
    private BuildPluginManager pluginManager;
    
    @Component
    private ProjectDependenciesResolver dependenciesResolver;
    
    @Override
    public void execute() throws MojoExecutionException {
        getLog().info("Template directory set to: " + templateDirectory.getAbsolutePath());
        getLog().info("Target directory set to: " + targetDirectory.getAbsolutePath());
        
        templateDirectory.mkdirs();
        targetDirectory.mkdirs();
        
        for (FilePair filePair : getFiles()) {
            File javaFile = filePair.getJava();
            String className = javaFile.getAbsolutePath()
                                       .substring(templateDirectory.getAbsolutePath().length() + 1)
                                       .replace(File.separator, ".")
                                       .replace(".java", "");
            try {
                FileExecutor.STParameters parameters =
                        FileExecutor.getParameters(filePair.getJava(), className, this);
                ST template = filePair.getSt();
                setParameters(template, parameters.getParameters());
                String render = template.render();
                File outputFile = new File(targetDirectory, parameters.getRelativeFile().getPath());
                outputFile.getParentFile().mkdirs();
                getLog().info("Writing to: " + outputFile.getAbsolutePath());
                writeFile(outputFile, render);
            } catch (Exception e) {
                throw new MojoExecutionException(
                        "Failed to create template for file " + javaFile.getAbsolutePath(), e);
            }
        }
    }
    
    private void writeFile(File file, String content) throws IOException {
        Files.write(file.toPath(), content.getBytes());
    }
    
    private void setParameters(ST st, Map<String, Object> parameters) {
        parameters.forEach(st::add);
    }
    
    private Collection<FilePair> getFiles() {
        Set<FilePair> files = new HashSet<>();
        STRawGroupDir rawGroupDir = new STRawGroupDir(templateDirectory.getAbsolutePath());
        for (File file : templateDirectory.listFiles()) {
            if (file.getName().endsWith(".java")) {
                String stName = file.getName().replace(".java", "");
                files.add(new FilePair(file, rawGroupDir.getInstanceOf(stName)));
            }
        }
        return files;
    }
    
    @Data
    private class FilePair {
        private final File java;
        private final ST st;
    }
}
