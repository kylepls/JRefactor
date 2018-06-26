package in.kyle.jrefactor.parser;

public class BaseMapper {
    //    
    //    @Override
    //    protected ParserRuleContext parseJObj(Java8Parser object) {
    //        throw new UnsupportedOperationException();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJValuePair(Java8Parser object) {
    //        throw new UnsupportedOperationException();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJLiteralBoolean(Java8Parser object) {
    //        return object.literal();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJLiteralCharacter(Java8Parser object) {
    //        return object.literal();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJNumericLiteral(Java8Parser object) {
    //        return object.literal();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStringLiteral(Java8Parser object) {
    //        return object.literal();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJLiteralDouble(Java8Parser object) {
    //        return object.literal();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJLiteralFloat(Java8Parser object) {
    //        return object.literal();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJFloatingLiteral(Java8Parser object) {
    //        return object.literal();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJIntegerLiteral(Java8Parser object) {
    //        return object.literal();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJLongLiteral(Java8Parser object) {
    //        return object.literal();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJExpressionAssignment(Java8Parser object) {
    //        return object.assignmentExpression();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJExpressionClassInstanceCreation(Java8Parser object) {
    //        return object.classInstanceCreationExpression();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJExpressionLambda(Java8Parser object) {
    //        return object.lambdaExpression();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJExpressionLeftRight(Java8Parser object) {
    //        return object.expression();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJExpressionLiteral(Java8Parser object) {
    //        return object.expression();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJExpressionMethodInvocation(Java8Parser object) {
    //        return object.methodInvocation();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJExpressionName(Java8Parser object) {
    //        return object.expressionName();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJExpressionParenthesis(Java8Parser object) {
    //        return object.expressionParenthesis();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJExpressionTernary(Java8Parser object) {
    //        return object.expression();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJExpressionTypeReference(Java8Parser object) {
    //        return object.primaryClassType();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJExpressionUnary(Java8Parser object) {
    //        return object.unaryExpression();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJAnnotation(Java8Parser object) {
    //        return object.annotation();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJAnnotationValue(Java8Parser object) {
    //        throw new UnsupportedOperationException();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJCompilationUnit(Java8Parser object) {
    //        return object.compilationUnit();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJExpression(Java8Parser object) {
    //        return object.expression();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJIdentifier(Java8Parser object) {
    //        return object.identifier();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJImport(Java8Parser object) {
    //        return object.importDeclaration();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJLambdaBody(Java8Parser object) {
    //        throw new UnsupportedOperationException();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJLambdaParameter(Java8Parser object) {
    //        throw new UnsupportedOperationException();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJModifiable(Java8Parser object) {
    //        return object.modifier();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatement(Java8Parser object) {
    //        return object.statement();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementElse(Java8Parser object) {
    //        throw new UnsupportedOperationException();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJTypeArgument(Java8Parser object) {
    //        return object.typeArgument();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJTypeName(Java8Parser object) {
    //        return object.typeName();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJUnit(Java8Parser object) {
    //        throw new UnsupportedOperationException();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJVariable(Java8Parser object) {
    //        return object.variableDeclarator();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJVariableInitializer(Java8Parser object) {
    //        return object.variableInitializer();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJLambdaParameterInferred(Java8Parser object) {
    //        throw new UnsupportedOperationException();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJClass(Java8Parser object) {
    //        return object.classDeclaration();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJEnumConstant(Java8Parser object) {
    //        return object.enumConstant();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJMethodHeader(Java8Parser object) {
    //        return object.methodHeader();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJType(Java8Parser object) {
    //        return object.typeDeclaration();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJAnnotationType(Java8Parser object) {
    //        return object.annotationTypeDeclaration();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJTypeSuperInterface(Java8Parser object) {
    //        return object.superinterfaces();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJClassType(Java8Parser object) {
    //        return object.classType();
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJEnumType(Java8Parser object) {
    //        return object.enum
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJInterfaceType(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJField(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJIdentifiable(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJMultiParameter(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJParameter(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJAnnotatable(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJCatchClause(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJBlock(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementAssert(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementControl(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementEmpty(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementExpression(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementLabeled(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementLocalVariableDeclaration(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementBreak(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementContinue(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJIdentifiableStatement(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementCatch(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementControlLoop(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementIf(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementReturn(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementSwitch(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementSynchronized(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementThrow(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementTry(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementTryWithResources(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementFor(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementWhile(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementBasicFor(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementEnhancedFor(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJStatementDoWhile(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJTypeArgumentReference(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJTypeArgumentWildcard(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJArrayTypeName(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJConstructorDeclaration(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJBodyElementInstantiable(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJTypeMember(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJAnnotationField(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJClassInstanceInitializer(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJClassStaticInitializer(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJClassInitializer(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJMethod(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJClassMember(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJAnnotationMember(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJEnumMember(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJInterfaceMember(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJBodyMember(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJTypeBody(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJAnnotationBody(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJClassBody(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJEnumBody(Java8Parser object) {
    //        return null;
    //    }
    //    
    //    @Override
    //    protected ParserRuleContext parseJInterfaceBody(Java8Parser object) {
    //        return null;
    //    }
}
