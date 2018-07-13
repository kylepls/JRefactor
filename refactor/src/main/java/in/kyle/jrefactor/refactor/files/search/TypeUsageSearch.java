package in.kyle.jrefactor.refactor.files.search;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.util.JObjUtilsStreams;
import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JImport;
import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.JTypeName;
import lombok.AllArgsConstructor;

import static in.kyle.jrefactor.refactor.util.JObjUtilsStreams.RECURSIVE;

@AllArgsConstructor
public class TypeUsageSearch {
    
    private final SourceContainer context;
    private final JTypeName input;
    
    public List<JObj> findUsages() {
        List<JCompilationUnit> importUsages = findImportUsages();
        List<JTypeName> fqReferences = findFullyQualifiedReferences(importUsages);
        return joinLists(importUsages, fqReferences);
    }
    
    private List<JCompilationUnit> findImportUsages() {
        return context.getAllDefinitions()
                .filter(unit -> isImportingType(unit, input))
                .collect(Collectors.toList());
    }
    
    private boolean isImportingType(JCompilationUnit unit, JTypeName typeName) {
        return isDirectImportingType(unit, typeName) || isWildcardImportingType(unit, typeName);
    }
    
    private boolean isDirectImportingType(JCompilationUnit unit, JTypeName typeName) {
        JPropertyLookup fqn = new JPropertyLookup();
        typeName.getArea().ifPresent(pl -> fqn.setAreas(pl.getAreas()));
        fqn.addArea(typeName.getType());
        
        return unit.getImportss().stream().anyMatch(i -> i.getArea().equals(fqn));
    }
    
    private boolean isWildcardImportingType(JCompilationUnit unit, JTypeName typeName) {
        if (typeName.getArea().isPresent()) {
            return unit.getImportss()
                    .stream()
                    .filter(JImport::isOnDemand)
                    .anyMatch(i -> i.getArea().equals(typeName.getArea().get()));
        } else {
            return false;
        }
    }
    
    private List<JTypeName> findFullyQualifiedReferences(List<JCompilationUnit> importUsages) {
        return context.getAllDefinitions()
                .filter(unit -> !importUsages.contains(unit))
                .flatMap(this::findTypeNames)
                .filter(ref -> ref.equals(input))
                .collect(Collectors.toList());
    }
    
    private Stream<JTypeName> findTypeNames(JCompilationUnit unit) {
        return JObjUtilsStreams.getChildrenOfType(RECURSIVE, unit, JTypeName.class);
    }
    
    private <T> List<T> joinLists(List a, List b) {
        return (List<T>) Stream.concat(a.stream(), b.stream()).collect(Collectors.toList());
    }
}
