package in.kyle.jrefactor.parser;

import org.antlr.v4.runtime.ParserRuleContext;

import in.kyle.jrefactor.tree.antlr.gen.Java8Parser;

public class BaseMapper extends in.kyle.jrefactor.parser.antlr4.AbstractParseMapper {
    
    @Override
    protected ParserRuleContext parseJAssignmentOperator(Java8Parser object) {
        return object.assignmentOperator();
    }
    
    @Override
    protected ParserRuleContext parseJAssignment(Java8Parser object) {
        return object.assignment();
    }
    
    @Override
    protected ParserRuleContext parseJClassInstanceCreationExpression(Java8Parser object) {
        return object.classInstanceCreationExpression();
    }
    
    @Override
    protected ParserRuleContext parseJExpression(Java8Parser object) {
        return object.expression();
    }
    
    @Override
    protected ParserRuleContext parseJExpressionName(Java8Parser object) {
        return object.expressionName();
    }
    
    @Override
    protected ParserRuleContext parseOperation(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJLeftRightExpression(Java8Parser object) {
        return object.expression();
    }
    
    @Override
    protected ParserRuleContext parseJMethodInvocation(Java8Parser object) {
        return object.methodInvocation();
    }
    
    @Override
    protected ParserRuleContext parseJParenthesisExpression(Java8Parser object) {
        return object.expression();
    }
    
    @Override
    protected ParserRuleContext parseJTernaryExpression(Java8Parser object) {
        return object.expression();
    }
    
    @Override
    protected ParserRuleContext parseJTypeReferenceExpression(Java8Parser object) {
        return object.primaryClassType();
    }
    
    @Override
    protected ParserRuleContext parseOperator(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJUnaryExpression(Java8Parser object) {
        return object.expression();
    }
    
    @Override
    protected ParserRuleContext parseJIdentifierParameter(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJInferredParameters(Java8Parser object) {
        return object.inferredFormalParameterList();
    }
    
    @Override
    protected ParserRuleContext parseJLambdaBody(Java8Parser object) {
        return object.lambdaBody();
    }
    
    @Override
    protected ParserRuleContext parseJLambdaExpression(Java8Parser object) {
        return object.lambdaExpression();
    }
    
    @Override
    protected ParserRuleContext parseJLambdaParameters(Java8Parser object) {
        return object.lambdaParameters();
    }
    
    @Override
    protected ParserRuleContext parseJBooleanLiteral(Java8Parser object) {
        return object.literal();
    }
    
    @Override
    protected ParserRuleContext parseJCharacterLiteral(Java8Parser object) {
        return object.literal();
    }
    
    @Override
    protected ParserRuleContext parseJDoubleLiteral(Java8Parser object) {
        return object.literal();
    }
    
    @Override
    protected ParserRuleContext parseJFloatingLiteral(Java8Parser object) {
        return object.floatingPointType();
    }
    
    @Override
    protected ParserRuleContext parseJFloatLiteral(Java8Parser object) {
        return object.literal();
    }
    
    @Override
    protected ParserRuleContext parseJIntegerLiteral(Java8Parser object) {
        return object.literal();
    }
    
    @Override
    protected ParserRuleContext parseJLiteral(Java8Parser object) {
        return object.literal();
    }
    
    @Override
    protected ParserRuleContext parseJLongLiteral(Java8Parser object) {
        return object.literal();
    }
    
    @Override
    protected ParserRuleContext parseJNumericLiteral(Java8Parser object) {
        return object.numericType();
    }
    
    @Override
    protected ParserRuleContext parseJStringLiteral(Java8Parser object) {
        return object.literal();
    }
    
    @Override
    protected ParserRuleContext parseJObject(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJObjectList(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJBreakStatement(Java8Parser object) {
        return object.breakStatement();
    }
    
    @Override
    protected ParserRuleContext parseJCatchClause(Java8Parser object) {
        return object.catchClause();
    }
    
    @Override
    protected ParserRuleContext parseJContinueStatement(Java8Parser object) {
        return object.continueStatement();
    }
    
    @Override
    protected ParserRuleContext parseJControlStatement(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJIfThenElseStatement(Java8Parser object) {
        return object.ifThenElseStatement();
    }
    
    @Override
    protected ParserRuleContext parseJIfThenStatement(Java8Parser object) {
        return object.ifThenStatement();
    }
    
    @Override
    protected ParserRuleContext parseJReturnStatement(Java8Parser object) {
        return object.returnStatement();
    }
    
    @Override
    protected ParserRuleContext parseJSwitchStatement(Java8Parser object) {
        return object.switchStatement();
    }
    
    @Override
    protected ParserRuleContext parseJSynchronizedStatement(Java8Parser object) {
        return object.synchronizedStatement();
    }
    
    @Override
    protected ParserRuleContext parseJThrowStatement(Java8Parser object) {
        return object.throwStatement();
    }
    
    @Override
    protected ParserRuleContext parseJTryStatement(Java8Parser object) {
        return object.tryStatement();
    }
    
    @Override
    protected ParserRuleContext parseJTryWithResourcesStatement(Java8Parser object) {
        return object.tryWithResourcesStatement();
    }
    
    @Override
    protected ParserRuleContext parseJBasicForStatement(Java8Parser object) {
        return object.forStatement();
    }
    
    @Override
    protected ParserRuleContext parseJDoWhileStatement(Java8Parser object) {
        return object.doStatement();
    }
    
    @Override
    protected ParserRuleContext parseJEnhancedForStatement(Java8Parser object) {
        return object.enhancedForStatement();
    }
    
    @Override
    protected ParserRuleContext parseJLoopStatement(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJWhileStatement(Java8Parser object) {
        return object.whileStatement();
    }
    
    @Override
    protected ParserRuleContext parseWhileStatement(Java8Parser object) {
        return object.whileStatement();
    }
    
    @Override
    protected ParserRuleContext parseJAssertStatement(Java8Parser object) {
        return object.assertStatement();
    }
    
    @Override
    protected ParserRuleContext parseJBlock(Java8Parser object) {
        return object.block();
    }
    
    @Override
    protected ParserRuleContext parseJEmptyStatement(Java8Parser object) {
        return object.emptyStatement();
    }
    
    @Override
    protected ParserRuleContext parseJExpressionStatement(Java8Parser object) {
        return object.expressionStatement();
    }
    
    @Override
    protected ParserRuleContext parseJLabledStatement(Java8Parser object) {
        return object.labeledStatement();
    }
    
    @Override
    protected ParserRuleContext parseJLocalVariableDeclaration(Java8Parser object) {
        return object.localVariableDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJStatement(Java8Parser object) {
        return object.statement();
    }
    
    @Override
    protected ParserRuleContext parseJArgumentList(Java8Parser object) {
        return object.argumentList();
    }
    
    @Override
    protected ParserRuleContext parseJConstructorDeclaration(Java8Parser object) {
        return object.constructorDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJMember(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJMemberList(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJMethod(Java8Parser object) {
        return object.methodDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJMethodHeader(Java8Parser object) {
        return object.methodHeader();
    }
    
    @Override
    protected ParserRuleContext parseJParameter(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJTypeBody(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJVariable(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJVariableInitializer(Java8Parser object) {
        return object.variableInitializer();
    }
    
    @Override
    protected ParserRuleContext parseVariableHolder(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJAnnotatable(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJAnnotation(Java8Parser object) {
        return object.annotation();
    }
    
    @Override
    protected ParserRuleContext parseJAnnotationList(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJElementPair(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJPairCollection(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJSingleValue(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJAnnotationValue(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJArrayTypeName(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJCompilationUnit(Java8Parser object) {
        return object.compilationUnit();
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
    protected ParserRuleContext parseJModifiable(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJModifier(Java8Parser object) {
        return object.modifier();
    }
    
    @Override
    protected ParserRuleContext parseJModifierList(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJPackage(Java8Parser object) {
        return object.packageDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJParameterList(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJThrowsList(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJReferenceTypeArgument(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseType(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJWildcardTypeArgument(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJTypeArgument(Java8Parser object) {
        return object.typeArgument();
    }
    
    @Override
    protected ParserRuleContext parseJTypeArgumentList(Java8Parser object) {
        return object.typeArguments();
    }
    
    @Override
    protected ParserRuleContext parseJTypeDeclaration(Java8Parser object) {
        return object.typeDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJTypeName(Java8Parser object) {
       return object.typeName(); 
    }
    
    @Override
    protected ParserRuleContext parseJTypeParameter(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJTypeParameterList(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseTypeable(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJAnnotationBody(Java8Parser object) {
        return object.annotationTypeBody();
    }
    
    @Override
    protected ParserRuleContext parseJAnnotationMember(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJAnnotationTypeElement(Java8Parser object) {
        return object.annotationTypeElementDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJElementValue(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJClassBody(Java8Parser object) {
        return object.classBody();
    }
    
    @Override
    protected ParserRuleContext parseJClassInitializer(Java8Parser object) {
       throw new UnsupportedOperationException(); 
    }
    
    @Override
    protected ParserRuleContext parseJClassInstanceInitializer(Java8Parser object) {
        return object.instanceInitializer();
    }
    
    @Override
    protected ParserRuleContext parseJClassMember(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJClassStaticInitializer(Java8Parser object) {
        return object.staticInitializer();
    }
    
    @Override
    protected ParserRuleContext parseJField(Java8Parser object) {
        return object.fieldDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJEnumBody(Java8Parser object) {
        return object.enumBody();
    }
    
    @Override
    protected ParserRuleContext parseJEnumConstant(Java8Parser object) {
        return object.enumConstant();
    }
    
    @Override
    protected ParserRuleContext parseJEnumMember(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJInterfaceBody(Java8Parser object) {
        return object.interfaceBody();
    }
    
    @Override
    protected ParserRuleContext parseJInterfaceMember(Java8Parser object) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected ParserRuleContext parseJInterfaceMethod(Java8Parser object) {
        return object.interfaceMethodDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJAnnotationDeclaration(Java8Parser object) {
        return object.annotationTypeDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJClassDeclaration(Java8Parser object) {
        return object.classDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJEnumDeclaration(Java8Parser object) {
        return object.enumDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJInterfaceDeclaration(Java8Parser object) {
        return object.interfaceDeclaration();
    }
    
    @Override
    protected ParserRuleContext parseJSuperInterfaceList(Java8Parser object) {
        return object.superinterfaces();
    }
}
