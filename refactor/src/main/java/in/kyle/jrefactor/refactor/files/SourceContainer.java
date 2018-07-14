package in.kyle.jrefactor.refactor.files;

import java.util.stream.Stream;

import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;

public interface SourceContainer {
    
    Stream<JCompilationUnit> getDefinitionsInPackage(JPropertyLookup packageName);
    
    Stream<JCompilationUnit> getAllDefinitions();
    
}
