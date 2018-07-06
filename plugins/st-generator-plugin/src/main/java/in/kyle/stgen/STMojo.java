package in.kyle.stgen;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectDependenciesResolver;
import org.stringtemplate.v4.ST;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

//@Execute(phase = LifecyclePhase.GENERATE_SOURCES, goal = "generate-sources")
@Mojo(name = "generate-template-sources",
      //      defaultPhase = LifecyclePhase.GENERATE_SOURCES,
      requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME,
      requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME)

@Data
@EqualsAndHashCode(callSuper = true)
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
    
    @Parameter(name = "templates", property = "templates", required = true)
    private Template[] templates;
    
    @Parameter
    private Set<String> compileExcludes = new HashSet<>();
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
        
        compileTemplateClasses();
        for (Template template : templates) {
            try {
                processTemplate(template);
            } catch (Exception e) {
                throw new MojoExecutionException(
                        "Failed to create file for template " + template.getTemplateName(), e);
            }
        }
    }
    
    private void compileTemplateClasses() throws MojoExecutionException {
        ClassUtils.compileFiles(templateDirectory, this);
    }
    
    private void processTemplate(Template template) throws Exception {
        if (template.isMultipleFiles()) {
            processMultiFileTemplate(template);
        } else {
            processSingleFileTemplate(template);
        }
    }
    
    private void processMultiFileTemplate(Template template) throws Exception {
        ST blank = template.getSt(templateDirectory);
        Map<String, Map<String, Object>> files = template.getFiles(this);
        for (Map.Entry<String, Map<String, Object>> entry : files.entrySet()) {
            File outputFile = new File(targetDirectory, entry.getKey());
            ST st = new ST(blank);
            writeTemplate(outputFile, entry.getValue(), st);
        }
    }
    
    private void processSingleFileTemplate(Template template) throws Exception {
        ST st = template.getSt(templateDirectory);
        Map<String, Object> parameters = template.getParameters(this);
        File outputFile = template.getOutputFile(targetDirectory);
        writeTemplate(outputFile, parameters, st);
    }
    
    private void writeTemplate(File outputFile, Map<String, Object> parameters, ST st)
            throws IOException {
        parameters.forEach(st::add);
        String outputString = st.render();
        outputFile.getParentFile().mkdirs();
        getLog().info("Writing to: " + outputFile.getAbsolutePath());
        Files.write(outputFile.toPath(), outputString.getBytes());
    }
}
