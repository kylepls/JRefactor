package in.kyle.jrefactor.refactor.files.resolver;

import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.navigator.SourceNavigator;
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
        SourceNavigator navigator = new SourceNavigator(context);
    
        for (String area : input.getAreas()) {
            navigator.childPackage(area);
            if (!navigator.exists()) {
                break;
            }
        }
        
        return navigator.parentPackage().getPackageName();
    }
}
