package in.kyle.scanner;

import in.kyle.parser.statement.JStatement;

public class JavaBaseVisitor<T> extends AbstractJObjectVisitor<T> implements JavaVisitor<T> {
    public T visitOperator(in.kyle.parser.expression.JAssignment.Operator object) {
        return visitChildren(object);
    }
    public T visitJAssignment(in.kyle.parser.expression.JAssignment object) {
        return visitChildren(object);
    }
    public T visitJClassInstanceCreationExpression(in.kyle.parser.expression.JClassInstanceCreationExpression object) {
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
    public T visitJMethodInvocation(in.kyle.parser.expression.JMethodInvocation object) {
        return visitChildren(object);
    }
    public T visitJParenthesisExpression(in.kyle.parser.expression.JParenthesisExpression object) {
        return visitChildren(object);
    }
    public T visitJTypeReferenceExpression(in.kyle.parser.expression.JTypeReferenceExpression object) {
        return visitChildren(object);
    }
    public T visitJUnaryExpression(in.kyle.parser.expression.JUnaryExpression object) {
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
    public T visitJObject(in.kyle.parser.JObject object) {
        return visitChildren(object);
    }
    public T visitJBlock(in.kyle.parser.statement.JBlock object) {
        return visitChildren(object);
    }
    public T visitJBlockStatement(JStatement object) {
        return visitChildren(object);
    }
    public T visitJEmptyStatement(in.kyle.parser.statement.JEmptyStatement object) {
        return visitChildren(object);
    }
    public T visitJExpressionStatement(in.kyle.parser.statement.JExpressionStatement object) {
        return visitChildren(object);
    }
    public T visitJLocalVariableDeclaration(in.kyle.parser.statement.JLocalVariableDeclaration object) {
        return visitChildren(object);
    }
    public T visitJAnnotatable(in.kyle.parser.unit.JAnnotatable object) {
        return visitChildren(object);
    }
    public T visitJAnnotation(in.kyle.parser.unit.JAnnotation object) {
        return visitChildren(object);
    }
    public T visitJClass(in.kyle.parser.unit.JClass object) {
        return visitChildren(object);
    }
    public T visitJClassBody(in.kyle.parser.unit.JClassBody object) {
        return visitChildren(object);
    }
    public T visitJClassMember(in.kyle.parser.unit.JClassMember object) {
        return visitChildren(object);
    }
    public T visitJCompilationUnit(in.kyle.parser.unit.JCompilationUnit object) {
        return visitChildren(object);
    }
    public T visitJField(in.kyle.parser.unit.JField object) {
        return visitChildren(object);
    }
    public T visitJImport(in.kyle.parser.unit.JImport object) {
        return visitChildren(object);
    }
    public T visitJMethod(in.kyle.parser.unit.JMethod object) {
        return visitChildren(object);
    }
    public T visitJModifier(in.kyle.parser.unit.JModifier object) {
        return visitChildren(object);
    }
    public T visitJPackage(in.kyle.parser.unit.JPackage object) {
        return visitChildren(object);
    }
    public T visitJParameter(in.kyle.parser.unit.JParameter object) {
        return visitChildren(object);
    }
    public T visitJType(in.kyle.parser.unit.JType object) {
        return visitChildren(object);
    }
    public T visitJTypeName(in.kyle.parser.unit.JTypeName object) {
        return visitChildren(object);
    }
    public T visitJTypeParameter(in.kyle.parser.unit.JTypeParameter object) {
        return visitChildren(object);
    }
    public T visitJVariable(in.kyle.parser.unit.JVariable object) {
        return visitChildren(object);
    }
    public T visitJVariableInitializer(in.kyle.parser.unit.JVariableInitializer object) {
        return visitChildren(object);
    }
    public T visitModifiable(in.kyle.parser.unit.Modifiable object) {
        return visitChildren(object);
    }
    public T visitTypeable(in.kyle.parser.unit.Typeable object) {
        return visitChildren(object);
    }
    public T visitVariableHolder(in.kyle.parser.unit.VariableHolder object) {
        return visitChildren(object);
    }

}