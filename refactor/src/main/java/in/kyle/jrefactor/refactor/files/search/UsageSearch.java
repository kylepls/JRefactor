package in.kyle.jrefactor.refactor.files.search;

import java.util.Collection;

import in.kyle.jrefactor.tree.JObj;

public interface UsageSearch<I, O extends JObj> {
    
    Collection<O> findUsages(I input);
}
