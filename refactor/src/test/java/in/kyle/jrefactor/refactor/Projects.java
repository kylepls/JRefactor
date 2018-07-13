package in.kyle.jrefactor.refactor;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import in.kyle.api.utils.Conditions;
import in.kyle.api.utils.Try;
import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.files.SourceDirectory;

public class Projects {
    
    public static SourceContainer loadProject(String path) {
        URL resource = Projects.class.getResource("/tests/" + path);
        Conditions.notNull(resource);
        Path to = Try.to(() -> Paths.get(resource.toURI()));
        SourceDirectory directory = new SourceDirectory(to);
        directory.loadFiles();
        return directory;
    }
}
