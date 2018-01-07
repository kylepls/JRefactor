package in.kyle.jrefactor.refactor;
public class JavaBaseVisitor<T> extends AbstractJObjectVisitor<T> implements JavaVisitor<T> {
    public T visitOperator(in.kyle.jrefactor.parser.expression.JAssignment.Operator object) {
        return visitChildren(object);
    }
    public T visitJAssignment(in.kyle.jrefactor.parser.expression.JAssignment object) {
        return visitChildren(object);
    }
    public T visitJClassInstanceCreationExpression(in.kyle.jrefactor.parser.expression.JClassInstanceCreationExpression object) {
        return visitChildren(object);
    }
    public T visitJExpression(in.kyle.jrefactor.parser.expression.JExpression object) {
        return visitChildren(object);
    }
    public T visitJExpressionName(in.kyle.jrefactor.parser.expression.JExpressionName object) {
        return visitChildren(object);
    }
    public T visitOperation(in.kyle.jrefactor.parser.expression.JLeftRightExpression.Operation object) {
        return visitChildren(object);
    }
    public T visitJLeftRightExpression(in.kyle.jrefactor.parser.expression.JLeftRightExpression object) {
        return visitChildren(object);
    }
    public T visitJMethodInvocation(in.kyle.jrefactor.parser.expression.JMethodInvocation object) {
        return visitChildren(object);
    }
    public T visitJParenthesisExpression(in.kyle.jrefactor.parser.expression.JParenthesisExpression object) {
        return visitChildren(object);
    }
    public T visitJTernaryExpression(in.kyle.jrefactor.parser.expression.JTernaryExpression object) {
        return visitChildren(object);
    }
    public T visitJTypeReferenceExpression(in.kyle.jrefactor.parser.expression.JTypeReferenceExpression object) {
        return visitChildren(object);
    }
    public T visitJUnaryExpression(in.kyle.jrefactor.parser.expression.JUnaryExpression object) {
        return visitChildren(object);
    }
    public T visitJIdentifierParameter(in.kyle.jrefactor.parser.expression.lambda.JIdentifierParameter object) {
        return visitChildren(object);
    }
    public T visitJInferredParameters(in.kyle.jrefactor.parser.expression.lambda.JInferredParameters object) {
        return visitChildren(object);
    }
    public T visitJLambdaBody(in.kyle.jrefactor.parser.expression.lambda.JLambdaBody object) {
        return visitChildren(object);
    }
    public T visitJLambdaExpression(in.kyle.jrefactor.parser.expression.lambda.JLambdaExpression object) {
        return visitChildren(object);
    }
    public T visitJLambdaParameters(in.kyle.jrefactor.parser.expression.lambda.JLambdaParameters object) {
        return visitChildren(object);
    }
    public T visitJBooleanLiteral(in.kyle.jrefactor.parser.expression.literal.JBooleanLiteral object) {
        return visitChildren(object);
    }
    public T visitJCharacterLiteral(in.kyle.jrefactor.parser.expression.literal.JCharacterLiteral object) {
        return visitChildren(object);
    }
    public T visitJDoubleLiteral(in.kyle.jrefactor.parser.expression.literal.JDoubleLiteral object) {
        return visitChildren(object);
    }
    public T visitJFloatingLiteral(in.kyle.jrefactor.parser.expression.literal.JFloatingLiteral object) {
        return visitChildren(object);
    }
    public T visitJFloatLiteral(in.kyle.jrefactor.parser.expression.literal.JFloatLiteral object) {
        return visitChildren(object);
    }
    public T visitJIntegerLiteral(in.kyle.jrefactor.parser.expression.literal.JIntegerLiteral object) {
        return visitChildren(object);
    }
    public T visitJLiteral(in.kyle.jrefactor.parser.expression.literal.JLiteral object) {
        return visitChildren(object);
    }
    public T visitJLongLiteral(in.kyle.jrefactor.parser.expression.literal.JLongLiteral object) {
        return visitChildren(object);
    }
    public T visitJNumericLiteral(in.kyle.jrefactor.parser.expression.literal.JNumericLiteral object) {
        return visitChildren(object);
    }
    public T visitJStringLiteral(in.kyle.jrefactor.parser.expression.literal.JStringLiteral object) {
        return visitChildren(object);
    }
    public T visitJObject(in.kyle.jrefactor.parser.JObject object) {
        return visitChildren(object);
    }
    public T visitJObjectList(in.kyle.jrefactor.parser.JObjectList object) {
        return visitChildren(object);
    }
    public T visitJBreakStatement(in.kyle.jrefactor.parser.statement.control.JBreakStatement object) {
        return visitChildren(object);
    }
    public T visitJCatchClause(in.kyle.jrefactor.parser.statement.control.JCatchClause object) {
        return visitChildren(object);
    }
    public T visitJContinueStatement(in.kyle.jrefactor.parser.statement.control.JContinueStatement object) {
        return visitChildren(object);
    }
    public T visitJControlStatement(in.kyle.jrefactor.parser.statement.control.JControlStatement object) {
        return visitChildren(object);
    }
    public T visitJIfThenElseStatement(in.kyle.jrefactor.parser.statement.control.JIfThenElseStatement object) {
        return visitChildren(object);
    }
    public T visitJIfThenStatement(in.kyle.jrefactor.parser.statement.control.JIfThenStatement object) {
        return visitChildren(object);
    }
    public T visitJReturnStatement(in.kyle.jrefactor.parser.statement.control.JReturnStatement object) {
        return visitChildren(object);
    }
    public T visitJSwitchStatement(in.kyle.jrefactor.parser.statement.control.JSwitchStatement object) {
        return visitChildren(object);
    }
    public T visitJSynchronizedStatement(in.kyle.jrefactor.parser.statement.control.JSynchronizedStatement object) {
        return visitChildren(object);
    }
    public T visitJThrowStatement(in.kyle.jrefactor.parser.statement.control.JThrowStatement object) {
        return visitChildren(object);
    }
    public T visitJTryStatement(in.kyle.jrefactor.parser.statement.control.JTryStatement object) {
        return visitChildren(object);
    }
    public T visitJTryWithResourcesStatement(in.kyle.jrefactor.parser.statement.control.JTryWithResourcesStatement object) {
        return visitChildren(object);
    }
    public T visitJBasicForStatement(in.kyle.jrefactor.parser.statement.control.loops.JBasicForStatement object) {
        return visitChildren(object);
    }
    public T visitJDoWhileStatement(in.kyle.jrefactor.parser.statement.control.loops.JDoWhileStatement object) {
        return visitChildren(object);
    }
    public T visitJEnhancedForStatement(in.kyle.jrefactor.parser.statement.control.loops.JEnhancedForStatement object) {
        return visitChildren(object);
    }
    public T visitJLoopStatement(in.kyle.jrefactor.parser.statement.control.loops.JLoopStatement object) {
        return visitChildren(object);
    }
    public T visitJWhileStatement(in.kyle.jrefactor.parser.statement.control.loops.JWhileStatement object) {
        return visitChildren(object);
    }
    public T visitWhileStatement(in.kyle.jrefactor.parser.statement.control.loops.WhileStatement object) {
        return visitChildren(object);
    }
    public T visitJAssertStatement(in.kyle.jrefactor.parser.statement.JAssertStatement object) {
        return visitChildren(object);
    }
    public T visitJBlock(in.kyle.jrefactor.parser.statement.JBlock object) {
        return visitChildren(object);
    }
    public T visitJEmptyStatement(in.kyle.jrefactor.parser.statement.JEmptyStatement object) {
        return visitChildren(object);
    }
    public T visitJExpressionStatement(in.kyle.jrefactor.parser.statement.JExpressionStatement object) {
        return visitChildren(object);
    }
    public T visitJLabledStatement(in.kyle.jrefactor.parser.statement.JLabledStatement object) {
        return visitChildren(object);
    }
    public T visitJLocalVariableDeclaration(in.kyle.jrefactor.parser.statement.JLocalVariableDeclaration object) {
        return visitChildren(object);
    }
    public T visitJStatement(in.kyle.jrefactor.parser.statement.JStatement object) {
        return visitChildren(object);
    }
    public T visitAnnotationBase(in.kyle.jrefactor.parser.unit.AnnotationBase object) {
        return visitChildren(object);
    }
    public T visitJAnnotationBody(in.kyle.jrefactor.parser.unit.body.annotationtype.JAnnotationBody object) {
        return visitChildren(object);
    }
    public T visitJAnnotationMember(in.kyle.jrefactor.parser.unit.body.annotationtype.JAnnotationMember object) {
        return visitChildren(object);
    }
    public T visitJAnnotationTypeElement(in.kyle.jrefactor.parser.unit.body.annotationtype.JAnnotationTypeElement object) {
        return visitChildren(object);
    }
    public T visitJElementValue(in.kyle.jrefactor.parser.unit.body.annotationtype.JElementValue object) {
        return visitChildren(object);
    }
    public T visitJClassBody(in.kyle.jrefactor.parser.unit.body.classtype.JClassBody object) {
        return visitChildren(object);
    }
    public T visitJClassInitializer(in.kyle.jrefactor.parser.unit.body.classtype.JClassInitializer object) {
        return visitChildren(object);
    }
    public T visitJClassInstanceInitializer(in.kyle.jrefactor.parser.unit.body.classtype.JClassInstanceInitializer object) {
        return visitChildren(object);
    }
    public T visitJClassMember(in.kyle.jrefactor.parser.unit.body.classtype.JClassMember object) {
        return visitChildren(object);
    }
    public T visitJClassStaticInitializer(in.kyle.jrefactor.parser.unit.body.classtype.JClassStaticInitializer object) {
        return visitChildren(object);
    }
    public T visitJField(in.kyle.jrefactor.parser.unit.body.classtype.JField object) {
        return visitChildren(object);
    }
    public T visitJEnumBody(in.kyle.jrefactor.parser.unit.body.enumtype.JEnumBody object) {
        return visitChildren(object);
    }
    public T visitJEnumConstant(in.kyle.jrefactor.parser.unit.body.enumtype.JEnumConstant object) {
        return visitChildren(object);
    }
    public T visitJEnumMember(in.kyle.jrefactor.parser.unit.body.enumtype.JEnumMember object) {
        return visitChildren(object);
    }
    public T visitJInterfaceBody(in.kyle.jrefactor.parser.unit.body.interfacetype.JInterfaceBody object) {
        return visitChildren(object);
    }
    public T visitJInterfaceMember(in.kyle.jrefactor.parser.unit.body.interfacetype.JInterfaceMember object) {
        return visitChildren(object);
    }
    public T visitJInterfaceMethod(in.kyle.jrefactor.parser.unit.body.interfacetype.JInterfaceMethod object) {
        return visitChildren(object);
    }
    public T visitJArgumentList(in.kyle.jrefactor.parser.unit.body.JArgumentList object) {
        return visitChildren(object);
    }
    public T visitJConstructorDeclaration(in.kyle.jrefactor.parser.unit.body.JConstructorDeclaration object) {
        return visitChildren(object);
    }
    public T visitJMember(in.kyle.jrefactor.parser.unit.body.JMember object) {
        return visitChildren(object);
    }
    public T visitJMemberList(in.kyle.jrefactor.parser.unit.body.JMemberList object) {
        return visitChildren(object);
    }
    public T visitJMethod(in.kyle.jrefactor.parser.unit.body.JMethod object) {
        return visitChildren(object);
    }
    public T visitJMethodHeader(in.kyle.jrefactor.parser.unit.body.JMethodHeader object) {
        return visitChildren(object);
    }
    public T visitJParameter(in.kyle.jrefactor.parser.unit.body.JParameter object) {
        return visitChildren(object);
    }
    public T visitJTypeBody(in.kyle.jrefactor.parser.unit.body.JTypeBody object) {
        return visitChildren(object);
    }
    public T visitJVariable(in.kyle.jrefactor.parser.unit.body.JVariable object) {
        return visitChildren(object);
    }
    public T visitJVariableInitializer(in.kyle.jrefactor.parser.unit.body.JVariableInitializer object) {
        return visitChildren(object);
    }
    public T visitVariableHolder(in.kyle.jrefactor.parser.unit.body.VariableHolder object) {
        return visitChildren(object);
    }
    public T visitJAnnotatable(in.kyle.jrefactor.parser.unit.JAnnotatable object) {
        return visitChildren(object);
    }
    public T visitJAnnotation(in.kyle.jrefactor.parser.unit.JAnnotation object) {
        return visitChildren(object);
    }
    public T visitJElementPair(in.kyle.jrefactor.parser.unit.JAnnotationValue.JElementPair object) {
        return visitChildren(object);
    }
    public T visitJPairCollection(in.kyle.jrefactor.parser.unit.JAnnotationValue.JPairCollection object) {
        return visitChildren(object);
    }
    public T visitJSingleValue(in.kyle.jrefactor.parser.unit.JAnnotationValue.JSingleValue object) {
        return visitChildren(object);
    }
    public T visitJAnnotationValue(in.kyle.jrefactor.parser.unit.JAnnotationValue object) {
        return visitChildren(object);
    }
    public T visitJArrayTypeName(in.kyle.jrefactor.parser.unit.JArrayTypeName object) {
        return visitChildren(object);
    }
    public T visitJCompilationUnit(in.kyle.jrefactor.parser.unit.JCompilationUnit object) {
        return visitChildren(object);
    }
    public T visitJIdentifier(in.kyle.jrefactor.parser.unit.JIdentifier object) {
        return visitChildren(object);
    }
    public T visitJImport(in.kyle.jrefactor.parser.unit.JImport object) {
        return visitChildren(object);
    }
    public T visitJModifier(in.kyle.jrefactor.parser.unit.JModifier object) {
        return visitChildren(object);
    }
    public T visitJPackage(in.kyle.jrefactor.parser.unit.JPackage object) {
        return visitChildren(object);
    }
    public T visitJParameterList(in.kyle.jrefactor.parser.unit.JParameterList object) {
        return visitChildren(object);
    }
    public T visitJThrowsList(in.kyle.jrefactor.parser.unit.JThrowsList object) {
        return visitChildren(object);
    }
    public T visitJReferenceTypeArgument(in.kyle.jrefactor.parser.unit.JTypeArgument.JReferenceTypeArgument object) {
        return visitChildren(object);
    }
    public T visitType(in.kyle.jrefactor.parser.unit.JTypeArgument.JWildcardTypeArgument.Type object) {
        return visitChildren(object);
    }
    public T visitJWildcardTypeArgument(in.kyle.jrefactor.parser.unit.JTypeArgument.JWildcardTypeArgument object) {
        return visitChildren(object);
    }
    public T visitJTypeArgument(in.kyle.jrefactor.parser.unit.JTypeArgument object) {
        return visitChildren(object);
    }
    public T visitJTypeDeclaration(in.kyle.jrefactor.parser.unit.JTypeDeclaration object) {
        return visitChildren(object);
    }
    public T visitJTypeName(in.kyle.jrefactor.parser.unit.JTypeName object) {
        return visitChildren(object);
    }
    public T visitJTypeParameter(in.kyle.jrefactor.parser.unit.JTypeParameter object) {
        return visitChildren(object);
    }
    public T visitJTypeParameterList(in.kyle.jrefactor.parser.unit.JTypeParameterList object) {
        return visitChildren(object);
    }
    public T visitModifiable(in.kyle.jrefactor.parser.unit.Modifiable object) {
        return visitChildren(object);
    }
    public T visitModifierList(in.kyle.jrefactor.parser.unit.ModifierList object) {
        return visitChildren(object);
    }
    public T visitTypeable(in.kyle.jrefactor.parser.unit.Typeable object) {
        return visitChildren(object);
    }
    public T visitJAnnotationDeclaration(in.kyle.jrefactor.parser.unit.types.JAnnotationDeclaration object) {
        return visitChildren(object);
    }
    public T visitJClassDeclaration(in.kyle.jrefactor.parser.unit.types.JClassDeclaration object) {
        return visitChildren(object);
    }
    public T visitJEnumDeclaration(in.kyle.jrefactor.parser.unit.types.JEnumDeclaration object) {
        return visitChildren(object);
    }
    public T visitJInterfaceDeclaration(in.kyle.jrefactor.parser.unit.types.JInterfaceDeclaration object) {
        return visitChildren(object);
    }

}