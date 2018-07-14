package in.kyle.jrefactor.refactor.files.search;

import java.util.stream.Stream;

import in.kyle.jrefactor.tree.JObj;

public interface UsageSearch<I, O extends JObj> {
    
    Stream<O> findUsages(I input);
}
