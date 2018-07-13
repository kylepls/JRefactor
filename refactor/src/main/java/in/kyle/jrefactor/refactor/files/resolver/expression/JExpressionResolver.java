package in.kyle.jrefactor.refactor.files.resolver.expression;

import in.kyle.jrefactor.refactor.files.SourceContainer;
import in.kyle.jrefactor.refactor.files.resolver.JResolver;
import in.kyle.jrefactor.refactor.util.obj.JTypeNameUtils;
import in.kyle.jrefactor.tree.obj.JCompilationUnit;
import in.kyle.jrefactor.tree.obj.JExpression;
import in.kyle.jrefactor.tree.obj.JLeftRightOperator;
import in.kyle.jrefactor.tree.obj.JTypeName;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight.JLeftRightOperatorConditional;
import in.kyle.jrefactor.tree.obj.expression.JExpressionParenthesis;
import lombok.AllArgsConstructor;

import static in.kyle.jrefactor.refactor.util.obj.JTypeNames.BOOLEAN;
import static in.kyle.jrefactor.refactor.util.obj.JTypeNames.STRING;

/**
 * Resolves an expression to an output type
 */
@AllArgsConstructor
public class JExpressionResolver implements JResolver<JExpression, JTypeName> {
    
    private final SourceContainer context;
    private final JCompilationUnit unit;
    
    @Override
    public JTypeName resolve(JExpression input) {
        if (input instanceof JExpressionParenthesis) {
            return resolve(((JExpressionParenthesis) input).getExpression());
        } else if (input instanceof JExpressionLeftRight) {
            JExpressionLeftRight lr = (JExpressionLeftRight) input;
            JLeftRightOperator operator = lr.getOperator();
            
            if (operator instanceof JLeftRightOperatorConditional) {
                return BOOLEAN;
            } else {
                return resolveNonConditionalLR(lr);
            }
        }
        
        throw new RuntimeException("Resolve " + input);
    }
    
    private JTypeName resolveNonConditionalLR(JExpressionLeftRight lr) {
        JTypeName left = resolve(lr.getLeft());
        JTypeName right = resolve(lr.getRight());
        if (isString(left) || isString(right)) {
            return STRING;
        } else if (isNumeric(left) || isNumeric(right)) {
            // if either is number can infer result is number
            return JTypeNameUtils.getArithmeticResultType(left, right);
        } else {
            throw new RuntimeException("idk");
        }
    }
    
    private boolean isString(JTypeName typeName) {
        return typeName.equals(STRING);
    }
    
    private boolean isNumeric(JTypeName typeName) {
        return JTypeNameUtils.isNumber(typeName);
    }
}
