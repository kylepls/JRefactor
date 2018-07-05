package in.kyle.jrefactor.parser.obj;

import org.junit.Test;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.parser.Parser;
import in.kyle.jrefactor.tree.obj.statement.JStatementLocalVariableDeclaration;

public class TestJStatementLocalVariableDeclaration {

    @Test
    public void test() {
        String test = "int i = 0;";
        JStatementLocalVariableDeclaration statement = Parser.parse(test, JStatementLocalVariableDeclaration.class);
        Verify.that(statement.getAnnotations()).sizeIs(0);
        Verify.that(statement.getModifiers()).sizeIs(0);
        Verify.that(statement.getType()).isNotNull();
        Verify.that(statement.getVariables()).sizeIs(1);
    }
}