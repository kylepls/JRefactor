package in.kyle.scanner;
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
    public T visitJBooleanLiteral(in.kyle.parser.expression.literal.JBooleanLiteral object) {
        return visitChildren(object);
    }
    public T visitJByteLiteral(in.kyle.parser.expression.literal.JByteLiteral object) {
        return visitChildren(object);
    }
    public T visitJCharLiteral(in.kyle.parser.expression.literal.JCharLiteral object) {
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
    public T visitJLongLiteral(in.kyle.parser.expression.literal.JLongLiteral object) {
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
    public T visitJBreakStatement(in.kyle.parser.statement.control.JBreakStatement object) {
        return visitChildren(object);
    }
    public T visitJCatchClause(in.kyle.parser.statement.control.JCatchClause object) {
        return visitChildren(object);
    }
    public T visitJContinueStatement(in.kyle.parser.statement.control.JContinueStatement object) {
        return visitChildren(object);
    }
    public T visitJControlStatement(in.kyle.parser.statement.control.JControlStatement object) {
        return visitChildren(object);
    }
    public T visitJIfThenElseStatement(in.kyle.parser.statement.control.JIfThenElseStatement object) {
        return visitChildren(object);
    }
    public T visitJIfThenStatement(in.kyle.parser.statement.control.JIfThenStatement object) {
        return visitChildren(object);
    }
    public T visitJReturnStatement(in.kyle.parser.statement.control.JReturnStatement object) {
        return visitChildren(object);
    }
    public T visitJSwitchStatement(in.kyle.parser.statement.control.JSwitchStatement object) {
        return visitChildren(object);
    }
    public T visitJSynchronizedStatement(in.kyle.parser.statement.control.JSynchronizedStatement object) {
        return visitChildren(object);
    }
    public T visitJThrowStatement(in.kyle.parser.statement.control.JThrowStatement object) {
        return visitChildren(object);
    }
    public T visitJTryStatement(in.kyle.parser.statement.control.JTryStatement object) {
        return visitChildren(object);
    }
    public T visitJTryWithResourcesStatement(in.kyle.parser.statement.control.JTryWithResourcesStatement object) {
        return visitChildren(object);
    }
    public T visitJBasicForStatement(in.kyle.parser.statement.control.loops.JBasicForStatement object) {
        return visitChildren(object);
    }
    public T visitJDoWhileStatement(in.kyle.parser.statement.control.loops.JDoWhileStatement object) {
        return visitChildren(object);
    }
    public T visitJEnhancedForStatement(in.kyle.parser.statement.control.loops.JEnhancedForStatement object) {
        return visitChildren(object);
    }
    public T visitJLoopStatement(in.kyle.parser.statement.control.loops.JLoopStatement object) {
        return visitChildren(object);
    }
    public T visitJWhileStatement(in.kyle.parser.statement.control.loops.JWhileStatement object) {
        return visitChildren(object);
    }
    public T visitWhileStatement(in.kyle.parser.statement.control.loops.WhileStatement object) {
        return visitChildren(object);
    }
    public T visitJAssertStatement(in.kyle.parser.statement.JAssertStatement object) {
        return visitChildren(object);
    }
    public T visitJBlock(in.kyle.parser.statement.JBlock object) {
        return visitChildren(object);
    }
    public T visitJEmptyStatement(in.kyle.parser.statement.JEmptyStatement object) {
        return visitChildren(object);
    }
    public T visitJExpressionStatement(in.kyle.parser.statement.JExpressionStatement object) {
        return visitChildren(object);
    }
    public T visitJLabledStatement(in.kyle.parser.statement.JLabledStatement object) {
        return visitChildren(object);
    }
    public T visitJLocalVariableDeclaration(in.kyle.parser.statement.JLocalVariableDeclaration object) {
        return visitChildren(object);
    }
    public T visitJStatement(in.kyle.parser.statement.JStatement object) {
        return visitChildren(object);
    }
    public T visitAnnotationBase(in.kyle.parser.unit.AnnotationBase object) {
        return visitChildren(object);
    }
    public T visitJClassBody(in.kyle.parser.unit.body.classtype.JClassBody object) {
        return visitChildren(object);
    }
    public T visitJClassMember(in.kyle.parser.unit.body.classtype.JClassMember object) {
        return visitChildren(object);
    }
    public T visitJField(in.kyle.parser.unit.body.classtype.JField object) {
        return visitChildren(object);
    }
    public T visitJEnumBody(in.kyle.parser.unit.body.enumtype.JEnumBody object) {
        return visitChildren(object);
    }
    public T visitJEnumConstant(in.kyle.parser.unit.body.enumtype.JEnumConstant object) {
        return visitChildren(object);
    }
    public T visitJEnumMember(in.kyle.parser.unit.body.enumtype.JEnumMember object) {
        return visitChildren(object);
    }
    public T visitJInterface(in.kyle.parser.unit.body.interfacetype.JInterface object) {
        return visitChildren(object);
    }
    public T visitJInterfaceBody(in.kyle.parser.unit.body.interfacetype.JInterfaceBody object) {
        return visitChildren(object);
    }
    public T visitJInterfaceMember(in.kyle.parser.unit.body.interfacetype.JInterfaceMember object) {
        return visitChildren(object);
    }
    public T visitJInterfaceMethod(in.kyle.parser.unit.body.interfacetype.JInterfaceMethod object) {
        return visitChildren(object);
    }
    public T visitJArgumentList(in.kyle.parser.unit.body.JArgumentList object) {
        return visitChildren(object);
    }
    public T visitJMember(in.kyle.parser.unit.body.JMember object) {
        return visitChildren(object);
    }
    public T visitJMethod(in.kyle.parser.unit.body.JMethod object) {
        return visitChildren(object);
    }
    public T visitJMethodHeader(in.kyle.parser.unit.body.JMethodHeader object) {
        return visitChildren(object);
    }
    public T visitJParameter(in.kyle.parser.unit.body.JParameter object) {
        return visitChildren(object);
    }
    public T visitJTypeBody(in.kyle.parser.unit.body.JTypeBody object) {
        return visitChildren(object);
    }
    public T visitJVariable(in.kyle.parser.unit.body.JVariable object) {
        return visitChildren(object);
    }
    public T visitJVariableInitializer(in.kyle.parser.unit.body.JVariableInitializer object) {
        return visitChildren(object);
    }
    public T visitVariableHolder(in.kyle.parser.unit.body.VariableHolder object) {
        return visitChildren(object);
    }
    public T visitJAnnotatable(in.kyle.parser.unit.JAnnotatable object) {
        return visitChildren(object);
    }
    public T visitJAnnotation(in.kyle.parser.unit.JAnnotation object) {
        return visitChildren(object);
    }
    public T visitJCompilationUnit(in.kyle.parser.unit.JCompilationUnit object) {
        return visitChildren(object);
    }
    public T visitJIdentifier(in.kyle.parser.unit.JIdentifier object) {
        return visitChildren(object);
    }
    public T visitJImport(in.kyle.parser.unit.JImport object) {
        return visitChildren(object);
    }
    public T visitJModifier(in.kyle.parser.unit.JModifier object) {
        return visitChildren(object);
    }
    public T visitJPackage(in.kyle.parser.unit.JPackage object) {
        return visitChildren(object);
    }
    public T visitJParameterList(in.kyle.parser.unit.JParameterList object) {
        return visitChildren(object);
    }
    public T visitJThrowsList(in.kyle.parser.unit.JThrowsList object) {
        return visitChildren(object);
    }
    public T visitJType(in.kyle.parser.unit.JType object) {
        return visitChildren(object);
    }
    public T visitJReferenceTypeArgument(in.kyle.parser.unit.JTypeArgument.JReferenceTypeArgument object) {
        return visitChildren(object);
    }
    public T visitType(in.kyle.parser.unit.JTypeArgument.JWildcardTypeArgument.Type object) {
        return visitChildren(object);
    }
    public T visitJWildcardTypeArgument(in.kyle.parser.unit.JTypeArgument.JWildcardTypeArgument object) {
        return visitChildren(object);
    }
    public T visitJTypeArgument(in.kyle.parser.unit.JTypeArgument object) {
        return visitChildren(object);
    }
    public T visitJTypeName(in.kyle.parser.unit.JTypeName object) {
        return visitChildren(object);
    }
    public T visitJTypeParameter(in.kyle.parser.unit.JTypeParameter object) {
        return visitChildren(object);
    }
    public T visitModifiable(in.kyle.parser.unit.Modifiable object) {
        return visitChildren(object);
    }
    public T visitTypeable(in.kyle.parser.unit.Typeable object) {
        return visitChildren(object);
    }
    public T visitJClass(in.kyle.parser.unit.types.JClass object) {
        return visitChildren(object);
    }
    public T visitJEnum(in.kyle.parser.unit.types.JEnum object) {
        return visitChildren(object);
    }

}