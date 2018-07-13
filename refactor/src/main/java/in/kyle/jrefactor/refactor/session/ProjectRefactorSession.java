package in.kyle.jrefactor.refactor.session;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import in.kyle.jrefactor.refactor.files.SourceDirectory;
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
