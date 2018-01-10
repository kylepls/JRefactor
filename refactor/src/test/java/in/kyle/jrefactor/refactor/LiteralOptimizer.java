package in.kyle.jrefactor.refactor;

import java.util.Arrays;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.expression.JLeftRightExpression;
import in.kyle.jrefactor.parser.expression.JParenthesisExpression;
import in.kyle.jrefactor.parser.expression.literal.JIntegerLiteral;
import in.kyle.jrefactor.parser.expression.literal.JLiteral;
import in.kyle.jrefactor.parser.expression.literal.JNumericLiteral;
import in.kyle.jrefactor.parser.expression.literal.JStringLiteral;
import in.kyle.jrefactor.writer.AbstractWriter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@RequiredArgsConstructor
public class LiteralOptimizer extends JavaBaseVisitor {
    
    private final AbstractWriter writer = new AbstractWriter();
    private final RefactorSession session;
    
    @Getter
    @Setter
    private boolean rerun = true;
    
    @Override
    public Object visitJLeftRightExpression(JLeftRightExpression object) {
        if (object.getOperation() == JLeftRightExpression.Operation.ADD && object.getLeft() instanceof JLiteral &&
            object.getRight() instanceof JLiteral) {
            System.out.print("Convert " + objToString(object) + " -> ");
            JLiteral replace =
                    convertLiterals((JLiteral) object.getLeft(), (JLiteral) object.getRight());
            session.replace(object, replace);
            System.out.println(objToString(replace));
            rerun = true;
        } else if (contains(JParenthesisExpression.class, object.getLeft(), object.getRight())) {
            JLiteral left = extractLiteral(object.getLeft());
            JLiteral right = extractLiteral(object.getRight());
            if (left != null) {
                object.setLeft(left);
            }
            if (right != null) {
                object.setRight(right);
            }
            if (left != null || right != null) {
                visitJLeftRightExpression(object);
                rerun = true;
            }
        }
        return super.visitJLeftRightExpression(object);
    }
    
    private JLiteral extractLiteral(JObject object) {
        if (object instanceof JParenthesisExpression) {
            JParenthesisExpression ex = (JParenthesisExpression) object;
            if (ex.getValue() instanceof JLiteral) {
                return (JLiteral) ex.getValue();
            } else if (ex.getValue() instanceof JParenthesisExpression){
                return extractLiteral(ex.getValue());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    
    private String objToString(JObject object) {
        writer.clear();
        writer.append(object);
        return writer.toString();
    }
    
    private JLiteral convertLiterals(JLiteral left, JLiteral right) {
        if (contains(JStringLiteral.class, left, right)) {
            return new JStringLiteral(left.getValue().toString() + right.getValue());
        } else if (left instanceof JNumericLiteral && right instanceof JNumericLiteral) {
            JNumericLiteral<Number> leftNumber = (JNumericLiteral) left;
            JNumericLiteral<Number> rightNumber = (JNumericLiteral) right;
            return new JIntegerLiteral(
                    leftNumber.getValue().intValue() + rightNumber.getValue().intValue());
        } else {
            throw new RuntimeException("idk");
        }
    }
    
    private boolean contains(Class<?> type, Object... arr) {
        return Arrays.stream(arr).anyMatch(o -> type.isAssignableFrom(o.getClass()));
    }
}
