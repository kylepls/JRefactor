package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;

public class TestJCompilationUnit {
    
    @Test
    public void test() {
        String test = "";
        JCompilationUnit unit = Parser.parse(test, JCompilationUnit.class);
        Verify.that(unit.getPackageName()).isNotPresent();
        Verify.that(unit.getImportss()).isEmpty();
        Verify.that(unit.getTypes()).isEmpty();
    }
    
    @Test
    public void testPackage() {
        String test = "package test;";
        JCompilationUnit unit = Parser.parse(test, JCompilationUnit.class);
        Verify.that(unit.getPackageName()).isPresent();
        Verify.that(unit.getImportss()).isEmpty();
        Verify.that(unit.getTypes()).isEmpty();
    }
    
    @Test
    public void testImports() {
        String test = "import test;";
        JCompilationUnit unit = Parser.parse(test, JCompilationUnit.class);
        Verify.that(unit.getPackageName()).isNotPresent();
        Verify.that(unit.getImportss()).sizeIs(1);
        Verify.that(unit.getTypes()).isEmpty();
    }
    
    @Test
    public void testTypes() {
        String test = "class Test {}";
        JCompilationUnit unit = Parser.parse(test, JCompilationUnit.class);
        Verify.that(unit.getPackageName()).isNotPresent();
        Verify.that(unit.getImportss()).isEmpty();
        Verify.that(unit.getTypes()).sizeIs(1);
    }
}