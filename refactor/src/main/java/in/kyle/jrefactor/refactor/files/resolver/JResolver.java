package in.kyle.jrefactor.refactor.files.resolver;

public interface JResolver<I,O> {
    
    O resolve(I input);
}
