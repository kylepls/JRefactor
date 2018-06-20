package in.kyle.ast.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;

import in.kyle.ast.antlr.AstGenerator;
import in.kyle.ast.code.FileTree;

@Execute(phase = LifecyclePhase.GENERATE_SOURCES, goal = "generate-sources")
@Mojo(name = "generate-ast", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class GenerationMojo extends AbstractMojo {
    
    @Parameter(name = "targetDirectory",
               defaultValue = "${project.build.directory}/generated-sources/ast/")
    private File targetDirectory;
    
    @Parameter(name = "astDir", defaultValue = "${project.basedir}/src/main/ast/")
    private File astDir;
    
    @Parameter(name = "packageName")
    private String packageName = "";
    
    @Override
    public void execute() throws MojoExecutionException {
        getLog().info("Directory set to " + astDir.getAbsolutePath());
        getLog().info("Generating sources...");
        astDir.mkdirs();
        for (File file : Objects.requireNonNull(astDir.listFiles())) {
            if (file.getName().endsWith(".ast")) {
                getLog().info("Loading file " + file.getAbsolutePath());
                try {
                    generateSourcesForFile(file);
                } catch (IOException e) {
                    throw new MojoExecutionException(
                            "Could not generate sources for file " + file.getAbsolutePath(), e);
                }
            }
        }
    }
    
    private void generateSourcesForFile(File file) throws IOException {
        InputStream stream = Files.newInputStream(file.toPath());
        FileTree tree = AstGenerator.generateAstFromStream(stream);
        FileTree root = new FileTree();
        String filepath = targetDirectory.getAbsolutePath();
        String filePackage = getFilePackage();
        root.addChildren(filepath + File.separator + filePackage, tree);
        getLog().info("Generating " + root.getFiles().size() + " files");
        root.write(filepath);
    }
    
    private String getFilePackage() {
        String filePackage = packageName.replace(".", File.separator);
        if (!filePackage.isEmpty()) {
            filePackage += File.separator;
        }
        return filePackage;
    }
}
