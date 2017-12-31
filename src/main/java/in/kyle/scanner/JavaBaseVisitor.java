package in.kyle.scanner;

import in.kyle.parser.JObject;
import in.kyle.parser.unit.JAnnotatable;
import in.kyle.parser.unit.JAnnotation;
import in.kyle.parser.unit.JClass;
import in.kyle.parser.unit.JClassMember;
import in.kyle.parser.unit.JCompilationUnit;
import in.kyle.parser.unit.JField;
import in.kyle.parser.unit.JTypeParameter;
import in.kyle.parser.unit.JImport;
import in.kyle.parser.unit.JMethod;
import in.kyle.parser.unit.JModifier;
import in.kyle.parser.unit.JPackage;
import in.kyle.parser.unit.JParameter;
import in.kyle.parser.unit.JType;
import in.kyle.parser.unit.JTypeName;
import in.kyle.parser.unit.JVariable;
import in.kyle.parser.unit.JVariableInitializer;

public class JavaBaseVisitor<T> extends AbstractJObjectVisitor<T> implements JavaVisitor<T> {
    public T visitJAnnotatable(JAnnotatable object) {
        return visitChildren(object);
    }
    public T visitJClassMember(JClassMember object) {
        return visitChildren(object);
    }
    public T visitJObject(JObject object) {
        return visitChildren(object);
    }
    public T visitJType(JType object) {
        return visitChildren(object);
    }
    public T visitJVariableInitializer(JVariableInitializer object) {
        return visitChildren(object);
    }
    public T visitJConditionalExpression(in.kyle.parser.expression.JConditionalExpression object) {
        return visitChildren(object);
    }
    public T visitJExpression(in.kyle.parser.expression.JExpression object) {
        return visitChildren(object);
    }
    public T visitJLamdaExpression(in.kyle.parser.expression.JLamdaExpression object) {
        return visitChildren(object);
    }
    public T visitOperation(in.kyle.parser.expression.JLeftRightExpression.Operation object) {
        return visitChildren(object);
    }
    public T visitJLeftRightExpression(in.kyle.parser.expression.JLeftRightExpression object) {
        return visitChildren(object);
    }
    public T visitJParenthesisExpression(in.kyle.parser.expression.JParenthesisExpression object) {
        return visitChildren(object);
    }
    public T visitJByteLiteral(in.kyle.parser.expression.literal.JByteLiteral object) {
        return visitChildren(object);
    }
    public T visitJDoubleLiteral(in.kyle.parser.expression.literal.JDoubleLiteral object) {
        return visitChildren(object);
    }
    public T visitJFloatLiteral(in.kyle.parser.expression.literal.JFloatLiteral object) {
        return visitChildren(object);
    }
    public T visitJIntegerLiteral(in.kyle.parser.expression.literal.JIntegerLiteral object) {
        return visitChildren(object);
    }
    public T visitJLiteral(in.kyle.parser.expression.literal.JLiteral object) {
        return visitChildren(object);
    }
    public T visitJNumericLiteral(in.kyle.parser.expression.literal.JNumericLiteral object) {
        return visitChildren(object);
    }
    public T visitJShortLiteral(in.kyle.parser.expression.literal.JShortLiteral object) {
        return visitChildren(object);
    }
    public T visitJStringLiteral(in.kyle.parser.expression.literal.JStringLiteral object) {
        return visitChildren(object);
    }
    public T visitJAnnotation(JAnnotation object) {
        return visitChildren(object);
    }
    public T visitJClass(JClass object) {
        return visitChildren(object);
    }
    public T visitJCompilationUnit(JCompilationUnit object) {
        return visitChildren(object);
    }
    public T visitJField(JField object) {
        return visitChildren(object);
    }
    public T visitJGeneric(JTypeParameter object) {
        return visitChildren(object);
    }
    public T visitJImport(JImport object) {
        return visitChildren(object);
    }
    public T visitJMethod(JMethod object) {
        return visitChildren(object);
    }
    public T visitJModifier(JModifier object) {
        return visitChildren(object);
    }
    public T visitJPackage(JPackage object) {
        return visitChildren(object);
    }
    public T visitJParameter(JParameter object) {
        return visitChildren(object);
    }
    public T visitJTypeName(JTypeName object) {
        return visitChildren(object);
    }
    public T visitJVariable(JVariable object) {
        return visitChildren(object);
    }

}