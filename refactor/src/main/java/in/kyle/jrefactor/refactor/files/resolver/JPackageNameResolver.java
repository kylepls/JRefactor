package in.kyle.jrefactor.refactor.files.resolver;

import java.util.List;

import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import lombok.AllArgsConstructor;

/**
 * Resolves a packageName as deep as possible
 */
@AllArgsConstructor
public class JPackageNameResolver implements JResolver<JPropertyLookup, JPropertyLookup> {
    
    private final SourceContainer context;
    
    @Override
    public JPropertyLookup resolve(JPropertyLookup input) {
        JPropertyLookup packageName = new JPropertyLookup();
    
        for (int i = 0; i < input.getAreas().size(); i++) {
            packageName.addArea(input.getAreas().get(i));
            if (!hasFiles(packageName)) {
                removeLast(packageName.getAreas());
                break;
            }
        }
    
        return packageName;
    }
    
    private boolean hasFiles(JPropertyLookup packageName) {
        return context.getDefinitionsInPackage(packageName).findAny().isPresent();
    }
    
    private void removeLast(List<?> list) {
        if (list.size() != 0) {
            list.remove(list.size() - 1);
        }
    }
}
