package in.kyle.jrefactor.refactor.files.resolver.identifier;

import org.junit.Test;

import java.util.Optional;

import in.kyle.api.verify.Verify;
import in.kyle.jrefactor.refactor.util.obj.JTypeNames;
import in.kyle.jrefactor.tree.obj.JIdentifier;
import in.kyle.jrefactor.tree.obj.JVariableDefinition;
import in.kyle.jrefactor.tree.obj.block.JStatementBlock;
import in.kyle.jrefactor.tree.obj.expression.JExpressionName;
import in.kyle.jrefactor.tree.obj.statement.JStatementLocalVariableDeclaration;
import in.kyle.jrefactor.tree.obj.statement.statementcontrol.JStatementReturn;
import in.kyle.jrefactor.tree.obj.variabledefinition.JVariable;

public class TestJVariableResolverBlock {
    
    @Test
    public void testSameBlock() {
        JVariable variable = JVariable.builder().identifier(new JIdentifier("string")).build();
        JStatementLocalVariableDeclaration declaration =
                JStatementLocalVariableDeclaration.builder()
                        .type(JTypeNames.STRING)
                        .addVariables(variable)
                        .build();
        JExpressionName expression = new JExpressionName(new JIdentifier("string"));
        JStatementReturn ret = JStatementReturn.builder().expression(expression).build();
        JStatementBlock block = JStatementBlock.builder().addElements(declaration, ret).build();
    
        Optional<JVariableDefinition> resolve =
                new JVariableResolverBlock(block, block).resolve(expression);
        Verify.that(resolve).isPresent();
        Verify.that(resolve.get()).isSame(variable);
    }
}
