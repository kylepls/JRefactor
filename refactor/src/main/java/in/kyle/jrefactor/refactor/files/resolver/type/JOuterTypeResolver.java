package in.kyle.jrefactor.refactor.files.resolver.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.files.resolver.JResolver;
import in.kyle.jrefactor.refactor.util.JObjUtils;
import in.kyle.jrefactor.refactor.util.JObjUtilsStreams;
import in.kyle.jrefactor.refactor.util.obj.JPropertyLookupUtils;
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.modifiable.annotatable.identifiable.JType;
import lombok.RequiredArgsConstructor;

/**
 * Given an element inside a type, find the TypeName of the containing type
 */
@RequiredArgsConstructor
public class JOuterTypeResolver implements JResolver<JObj, JTypeName> {
    
    private final SourceContainer context;
    
    @Override
    public JTypeName resolve(JObj input) {
        JCompilationUnit unit = context.getAllDefinitions()
                .map(u -> findCompilationUnit(u, input))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny()
                .get();
    
        JPropertyLookup.JPropertyLookupBuilder builder = JPropertyLookup.builder();
        unit.getPackageName().ifPresent(name->builder.addAreas(name.getAreas()));
        builder.addAreas(getAreas(unit, input));
        return JPropertyLookupUtils.toTypeName(builder.build());
    }
    
    private Optional<JCompilationUnit> findCompilationUnit(JCompilationUnit unit, JObj input) {
        if (JObjUtilsStreams.getTree(unit).anyMatch(obj -> obj == input)) {
            return Optional.of(unit);
        } else {
            return Optional.empty();
        }
    }
    
    private List<String> getAreas(JCompilationUnit unit, JObj input) {
        List<String> strings = new ArrayList<>();
        JObj search = input;
        while (search != unit) {
            search = JObjUtils.findParent(unit, search);
//            System.out.println(search);
            if (search instanceof JType) {
                strings.add(((JType) search).getIdentifier().getName());
            }
        }
        Collections.reverse(strings);
        return strings;
    }
}
