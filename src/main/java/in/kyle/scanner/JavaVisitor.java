package in.kyle.scanner;

import in.kyle.parser.statement.JStatement;

public interface JavaVisitor<T> {
    T visitOperator(in.kyle.parser.expression.JAssignment.Operator object);
    T visitJAssignment(in.kyle.parser.expression.JAssignment object);
    T visitJClassInstanceCreationExpression(in.kyle.parser.expression.JClassInstanceCreationExpression object);
    T visitJConditionalExpression(in.kyle.parser.expression.JConditionalExpression object);
    T visitJExpression(in.kyle.parser.expression.JExpression object);
    T visitJLamdaExpression(in.kyle.parser.expression.JLamdaExpression object);
    T visitOperation(in.kyle.parser.expression.JLeftRightExpression.Operation object);
    T visitJLeftRightExpression(in.kyle.parser.expression.JLeftRightExpression object);
    T visitJMethodInvocation(in.kyle.parser.expression.JMethodInvocation object);
    T visitJParenthesisExpression(in.kyle.parser.expression.JParenthesisExpression object);
    T visitJTypeReferenceExpression(in.kyle.parser.expression.JTypeReferenceExpression object);
    T visitJUnaryExpression(in.kyle.parser.expression.JUnaryExpression object);
    T visitJByteLiteral(in.kyle.parser.expression.literal.JByteLiteral object);
    T visitJDoubleLiteral(in.kyle.parser.expression.literal.JDoubleLiteral object);
    T visitJFloatLiteral(in.kyle.parser.expression.literal.JFloatLiteral object);
    T visitJIntegerLiteral(in.kyle.parser.expression.literal.JIntegerLiteral object);
    T visitJLiteral(in.kyle.parser.expression.literal.JLiteral object);
    T visitJNumericLiteral(in.kyle.parser.expression.literal.JNumericLiteral object);
    T visitJShortLiteral(in.kyle.parser.expression.literal.JShortLiteral object);
    T visitJStringLiteral(in.kyle.parser.expression.literal.JStringLiteral object);
    T visitJObject(in.kyle.parser.JObject object);
    T visitJBlock(in.kyle.parser.statement.JBlock object);
    T visitJBlockStatement(JStatement object);
    T visitJEmptyStatement(in.kyle.parser.statement.JEmptyStatement object);
    T visitJExpressionStatement(in.kyle.parser.statement.JExpressionStatement object);
    T visitJLocalVariableDeclaration(in.kyle.parser.statement.JLocalVariableDeclaration object);
    T visitJAnnotatable(in.kyle.parser.unit.JAnnotatable object);
    T visitJAnnotation(in.kyle.parser.unit.JAnnotation object);
    T visitJClass(in.kyle.parser.unit.JClass object);
    T visitJClassBody(in.kyle.parser.unit.JClassBody object);
    T visitJClassMember(in.kyle.parser.unit.JClassMember object);
    T visitJCompilationUnit(in.kyle.parser.unit.JCompilationUnit object);
    T visitJField(in.kyle.parser.unit.JField object);
    T visitJImport(in.kyle.parser.unit.JImport object);
    T visitJMethod(in.kyle.parser.unit.JMethod object);
    T visitJModifier(in.kyle.parser.unit.JModifier object);
    T visitJPackage(in.kyle.parser.unit.JPackage object);
    T visitJParameter(in.kyle.parser.unit.JParameter object);
    T visitJType(in.kyle.parser.unit.JType object);
    T visitJTypeName(in.kyle.parser.unit.JTypeName object);
    T visitJTypeParameter(in.kyle.parser.unit.JTypeParameter object);
    T visitJVariable(in.kyle.parser.unit.JVariable object);
    T visitJVariableInitializer(in.kyle.parser.unit.JVariableInitializer object);
    T visitModifiable(in.kyle.parser.unit.Modifiable object);
    T visitTypeable(in.kyle.parser.unit.Typeable object);
    T visitVariableHolder(in.kyle.parser.unit.VariableHolder object);

}