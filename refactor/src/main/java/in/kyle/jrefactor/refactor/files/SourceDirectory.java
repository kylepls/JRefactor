package in.kyle.jrefactor.refactor.files;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
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
    
    private static final PathMatcher JAVA_MATCHER =
            FileSystems.getDefault().getPathMatcher("glob:**.java");
    
    private final Set<SourceFile> files = new HashSet<>();
    private final Path path;
    
    public void loadFiles() {
        FileUtils.transverseFiles(path, this::loadFile);
    }
    
    private void loadFile(Path path) {
        if (JAVA_MATCHER.matches(path)) {
            try {
                JCompilationUnit unit = JavaParser.parseFile(Files.newInputStream(path));
                SourceFile file = new SourceFile(unit);
                files.add(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    @Override
    public Stream<JCompilationUnit> getDefinitionsInPackage(JPropertyLookup packageName) {
        return files.stream().flatMap(file -> file.getDefinitionsInPackage(packageName));
    }
    
    @Override
    public Stream<JCompilationUnit> getAllDefinitions() {
        return files.stream().flatMap(SourceContainer::getAllDefinitions);
    }
}
