package in.kyle.jrefactor.refactor.files;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProjectRefactorSession {
    
    private final Set<SourceDirectory> sourceDirectories = new HashSet<>();
    
    public void addSourceDirectory(Path path) {
        SourceDirectory directory = new SourceDirectory(path);
        sourceDirectories.add(directory);
    }
    
    public void loadFiles() {
        for (SourceDirectory directory : sourceDirectories) {
            directory.loadFiles();
        }
    }
}
