package in.kyle.jrefactor.refactor;

import java.util.Arrays;

import in.kyle.jrefactor.tree.JObj;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight;
import in.kyle.jrefactor.tree.obj.expression.JExpressionLiteral;
import in.kyle.jrefactor.tree.obj.expression.JExpressionParenthesis;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralNumeric;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.JLiteralString;
import in.kyle.jrefactor.tree.obj.expression.expressionliteral.literalnumeric.JLiteralInteger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import static in.kyle.jrefactor.tree.obj.expression.JExpressionLeftRight.JLeftRightOperator.ADD;


@RequiredArgsConstructor
public class LiteralOptimizer extends JavaBaseVisitor {
    
    private final RefactorSession session;
    
    @Getter
    @Setter
    private boolean rerun;
    
    @Override
    public Object visitJExpressionLeftRight(JExpressionLeftRight object) {
        if (object.getOperator() == ADD && object.getLeft() instanceof JExpressionLiteral &&
            object.getRight() instanceof JExpressionLiteral) {
            JExpressionLiteral replace = convertLiterals((JExpressionLiteral) object.getLeft(),
                                                         (JExpressionLiteral) object.getRight());
            session.replace(object, replace);
            rerun = true;
        } else if (contains(JExpressionParenthesis.class, object.getLeft(), object.getRight())) {
            JExpressionLiteral left = extractLiteral(object.getLeft());
            JExpressionLiteral right = extractLiteral(object.getRight());
            if (left != null) {
                object.setLeft(left);
            }
            if (right != null) {
                object.setRight(right);
            }
            if (left != null || right != null) {
                visitJExpressionLeftRight(object);
                rerun = true;
            }
        }
        return super.visitJExpressionLeftRight(object);
    }
    
    private JExpressionLiteral extractLiteral(JObj object) {
        if (object instanceof JExpressionParenthesis) {
            JExpressionParenthesis ex = (JExpressionParenthesis) object;
            if (ex.getExpression() instanceof JExpressionLiteral) {
                return (JExpressionLiteral) ex.getExpression();
            } else if (ex.getExpression() instanceof JExpressionParenthesis) {
                return extractLiteral(ex.getExpression());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    
    private JExpressionLiteral convertLiterals(JExpressionLiteral left, JExpressionLiteral right) {
        if (contains(JLiteralString.class, left, right)) {
            return new JLiteralString(left.getValue().toString() + right.getValue());
        } else if (left instanceof JLiteralNumeric && right instanceof JLiteralNumeric) {
            JLiteralNumeric<Number> leftNumber = (JLiteralNumeric) left;
            JLiteralNumeric<Number> rightNumber = (JLiteralNumeric) right;
            return new JLiteralInteger(
                    leftNumber.getValue().intValue() + rightNumber.getValue().intValue());
        } else {
            throw new RuntimeException("idk");
        }
    }
    
    private boolean contains(Class<?> type, Object... arr) {
        return Arrays.stream(arr).anyMatch(o -> type.isAssignableFrom(o.getClass()));
    }
}
