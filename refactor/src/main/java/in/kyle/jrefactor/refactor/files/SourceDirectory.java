package in.kyle.jrefactor.refactor.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import in.kyle.jrefactor.parser.JavaParser;
import in.kyle.jrefactor.refactor.util.FileUtils;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SourceDirectory implements SourceContainer {
    
    private final Set<SourceFile> files = new HashSet<>();
    private final Path path;
    
    public void loadFiles() {
        FileUtils.transverseFiles(path, this::loadFile);
    }
    
    private void loadFile(Path path) {
        try {
            files.add(new SourceFile(JavaParser.parseFile(Files.newInputStream(path))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Stream<JCompilationUnit> getDefinitionsInPackage(JPropertyLookup packageName) {
        return files.stream().flatMap(file -> file.getDefinitionsInPackage(packageName));
    }
}
