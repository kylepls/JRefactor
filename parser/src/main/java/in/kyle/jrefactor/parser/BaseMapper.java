package in.kyle.jrefactor.parser;

import org.antlr.v4.runtime.ParserRuleContext;

import in.kyle.jrefactor.tree.antlr.gen.Java8Parser;

public class BaseMapper extends AbstractParseMapper {
    
    @Override
    protected ParserRuleContext parseJModifier(Java8Parser object) {
        return object.modifier();
    }
    
    @Override
    protected ParserRuleContext parseJObj(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJValuePair(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJLiteralBoolean(Java8Parser object) {
        return object.literal();
    }
    
    @Override
    protected ParserRuleContext parseJLiteralCharacter(Java8Parser object) {
        return object.literal();
    }
    
    @Override
    protected ParserRuleContext parseJLiteralDouble(Java8Parser object) {
        return object.literal();
    }
    
    @Override
    protected ParserRuleContext parseJLiteralFloat(Java8Parser object) {
        return object.literal();
    }
    
    @Override
    protected ParserRuleContext parseJExpressionAssignment(Java8Parser object) {
        return object.assignmentExpression();
    }
    
    @Override
    protected ParserRuleContext parseJAssignmentOperator(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJExpressionClassInstanceCreation(Java8Parser object) {
        return object.classInstanceCreationExpression();
    }
    
    @Override
    protected ParserRuleContext parseJExpressionLambda(Java8Parser object) {
        return object.lambdaExpression();
    }
    
    @Override
    protected ParserRuleContext parseJExpressionLeftRight(Java8Parser object) {
        return object.assignmentExpression();
    }
    
    @Override
    protected ParserRuleContext parseJLeftRightOperator(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJExpressionLiteral(Java8Parser object) {
        return object.expression();
    }
    
    @Override
    protected ParserRuleContext parseJExpressionMethodInvocation(Java8Parser object) {
        return object.methodInvocation();
    }
    
    @Override
    protected ParserRuleContext parseJLiteralNumeric(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJExpressionName(Java8Parser object) {
        return object.expressionName();
    }
    
    @Override
    protected ParserRuleContext parseJExpressionParenthesis(Java8Parser object) {
        return object.expressionParenthesis();
    }
    
    @Override
    protected ParserRuleContext parseJExpressionTernary(Java8Parser object) {
        return object.expression();
    }
    
    @Override
    protected ParserRuleContext parseJExpressionTypeReference(Java8Parser object) {
        return object.primaryClassType();
    }
    
    @Override
    protected ParserRuleContext parseJExpressionUnary(Java8Parser object) {
        return object.unaryExpression();
    }
    
    @Override
    protected ParserRuleContext parseJOperator(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJAnnotation(Java8Parser object) {
        return object.annotation();
    }
    
    @Override
    protected ParserRuleContext parseJAnnotationValue(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJCompilationUnit(Java8Parser object) {
        return object.compilationUnit();
    }
    
    @Override
    protected ParserRuleContext parseJExpression(Java8Parser object) {
        return object.expression();
    }
    
    @Override
    protected ParserRuleContext parseJIdentifier(Java8Parser object) {
        return object.identifier();
    }
    
    @Override
    protected ParserRuleContext parseJImport(Java8Parser object) {
        return object.importDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJLambdaBody(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJLambdaParameter(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJModifiable(Java8Parser object) {
        return object.modifier();
    }
    
    @Override
    protected ParserRuleContext parseJPropertyLookup(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJStatement(Java8Parser object) {
        return object.statement();
    }
    
    @Override
    protected ParserRuleContext parseJStatementElse(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJSwitchCase(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJTypeArgument(Java8Parser object) {
        return object.typeArgument();
    }
    
    @Override
    protected ParserRuleContext parseJTypeName(Java8Parser object) {
        return object.typeName();
    }
    
    @Override
    protected ParserRuleContext parseJUnit(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJVariable(Java8Parser object) {
        return object.variableDeclarator();
    }
    
    @Override
    protected ParserRuleContext parseJLiteralInteger(Java8Parser object) {
        return object.literal();
    }
    
    @Override
    protected ParserRuleContext parseJClass(Java8Parser object) {
        return object.classDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJInterface(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJEnumConstant(Java8Parser object) {
        return object.enumConstant();
    }
    
    @Override
    protected ParserRuleContext parseJMethodHeader(Java8Parser object) {
        return object.methodHeader();
    }
    
    @Override
    protected ParserRuleContext parseJType(Java8Parser object) {
        return object.typeDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJTypeParameter(Java8Parser object) {
        return object.typeParameter();
    }
    
    @Override
    protected ParserRuleContext parseJAnnotationType(Java8Parser object) {
        return object.annotationTypeDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJSuperInterfaceType(Java8Parser object) {
        return object.superinterfaces();
    }
    
    @Override
    protected ParserRuleContext parseJEnum(Java8Parser object) {
        return object.enumDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJTypeParameterType(Java8Parser object) {
        return object.typeParameter();
    }
    
    @Override
    protected ParserRuleContext parseJField(Java8Parser object) {
        return object.fieldDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJIdentifiable(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJMultiParameter(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJParameter(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJAnnotatable(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJCatchClause(Java8Parser object) {
        return object.catchClause();
    }
    
    @Override
    protected ParserRuleContext parseJBlock(Java8Parser object) {
        return object.block();
    }
    
    @Override
    protected ParserRuleContext parseJStatementAssert(Java8Parser object) {
        return object.assertStatement();
    }
    
    @Override
    protected ParserRuleContext parseJLiteralFloating(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJStatementControl(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJStatementEmpty(Java8Parser object) {
        return object.emptyStatement();
    }
    
    @Override
    protected ParserRuleContext parseJStatementExpression(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJStatementLabeled(Java8Parser object) {
        return object.labeledStatement();
    }
    
    @Override
    protected ParserRuleContext parseJStatementLocalVariableDeclaration(Java8Parser object) {
        return object.localVariableDeclarationStatement();
    }
    
    @Override
    protected ParserRuleContext parseJStatementBreak(Java8Parser object) {
        return object.breakStatement();
    }
    
    @Override
    protected ParserRuleContext parseJStatementContinue(Java8Parser object) {
        return object.continueStatement();
    }
    
    @Override
    protected ParserRuleContext parseJIdentifiableStatement(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJStatementControlLoop(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJStatementIf(Java8Parser object) {
        return object.ifThenStatement();
    }
    
    @Override
    protected ParserRuleContext parseJStatementReturn(Java8Parser object) {
        return object.returnStatement();
    }
    
    @Override
    protected ParserRuleContext parseJStatementSwitch(Java8Parser object) {
        return object.switchStatement();
    }
    
    @Override
    protected ParserRuleContext parseJStatementSynchronized(Java8Parser object) {
        return object.synchronizedStatement();
    }
    
    @Override
    protected ParserRuleContext parseJStatementThrow(Java8Parser object) {
        return object.throwStatement();
    }
    
    @Override
    protected ParserRuleContext parseJStatementTry(Java8Parser object) {
        return object.tryStatement();
    }
    
    @Override
    protected ParserRuleContext parseJStatementTryWithResources(Java8Parser object) {
        return object.tryWithResourcesStatement();
    }
    
    @Override
    protected ParserRuleContext parseJStatementFor(Java8Parser object) {
        return object.forStatement();
    }
    
    @Override
    protected ParserRuleContext parseJStatementWhile(Java8Parser object) {
        return object.whileStatement();
    }
    
    @Override
    protected ParserRuleContext parseJStatementBasicFor(Java8Parser object) {
        return object.forStatement();
    }
    
    @Override
    protected ParserRuleContext parseJStatementEnhancedFor(Java8Parser object) {
        return object.forStatement();
    }
    
    @Override
    protected ParserRuleContext parseJStatementDoWhile(Java8Parser object) {
        return object.doStatement();
    }
    
    @Override
    protected ParserRuleContext parseJSwitchCaseDefault(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJSwitchCaseExpression(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJTypeArgumentReference(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJAnnotationElementValue(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJValueSingle(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJStatementBlock(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJTypeArgumentWildcard(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJWildcardType(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJArrayTypeName(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJConstructorDeclaration(Java8Parser object) {
        return object.constructorDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJTypeMember(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJAnnotationField(Java8Parser object) {
        return object.annotationTypeElementDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJClassInstanceInitializer(Java8Parser object) {
        return object.instanceInitializer();
    }
    
    @Override
    protected ParserRuleContext parseJClassStaticInitializer(Java8Parser object) {
        return object.staticInitializer();
    }
    
    @Override
    protected ParserRuleContext parseJClassInitializer(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJMethod(Java8Parser object) {
        return object.methodDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJClassMember(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJInterfaceMethod(Java8Parser object) {
        return object.interfaceMethodDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJAnnotationMember(Java8Parser object) {
        return object.annotationTypeMemberDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJEnumMember(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJLiteralLong(Java8Parser object) {
        return object.literal();
    }
    
    @Override
    protected ParserRuleContext parseJInterfaceMember(Java8Parser object) {
        return object.interfaceMemberDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJBodyMember(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJReference(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJTypeBody(Java8Parser object) {
        return null;
    }
    
    @Override
    protected ParserRuleContext parseJAnnotationBody(Java8Parser object) {
        return object.annotationTypeBody();
    }
    
    @Override
    protected ParserRuleContext parseJClassBody(Java8Parser object) {
        return object.classBodyDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJEnumBody(Java8Parser object) {
        return object.enumBodyDeclarations();
    }
    
    @Override
    protected ParserRuleContext parseJInterfaceBody(Java8Parser object) {
        return object.interfaceBody();
    }
    
    @Override
    protected ParserRuleContext parseJLiteralString(Java8Parser object) {
        return object.literal();
    }
}
