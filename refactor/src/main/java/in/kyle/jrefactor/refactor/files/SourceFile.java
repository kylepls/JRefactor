package in.kyle.jrefactor.refactor.files;

import java.util.Optional;
import java.util.stream.Stream;

import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SourceFile implements SourceContainer {
    
    @Getter
    private final JCompilationUnit unit;
    
    @Override
    public Stream<JCompilationUnit> getDefinitionsInPackage(JPropertyLookup packageName) {
        if (unit.getPackageName().equals(Optional.ofNullable(packageName))) {
            return Stream.of(unit);
        } else {
            return Stream.empty();
        }
    }
}
