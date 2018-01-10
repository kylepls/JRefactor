/*
 * [The "BSD license"]
 *  Copyright (c) 2014 Terence Parr
 *  Copyright (c) 2014 Sam Harwell
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. The name of the author may not be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * A Java 8 grammar for ANTLR 4 derived from the Java Language Specification
 * chapter 19.
 *
 * NOTE: This grammar results in a generated parser that is much slower
 *       than the Java 7 grammar in the grammars-v4/java directory. This
 *     one is, however, extremely close to the spec.
 *
 * You can test with
 *
 *  $ antlr4 Java8.g4
 *  $ javac *.java
 *  $ grun Java8 compilationUnit *.java
 *
 * Or,
~/antlr/code/grammars-v4/java8 $ java Test .
/Users/parrt/antlr/code/grammars-v4/java8/./Java8BaseListener.java
/Users/parrt/antlr/code/grammars-v4/java8/./Java8Lexer.java
/Users/parrt/antlr/code/grammars-v4/java8/./Java8Listener.java
/Users/parrt/antlr/code/grammars-v4/java8/./Java8Parser.java
/Users/parrt/antlr/code/grammars-v4/java8/./Test.java
Total lexer+parser time 30844ms.
 */
grammar Java8;

// added
literal
	:	IntegerLiteral #IntegerLiteral 
	|	FloatingPointLiteral #FloatingPointLiteral 
	|	BooleanLiteral #BooleanLiteral
	|	CharacterLiteral #CharacterLiteral
	|	StringLiteral #StringLiteral
	|	NullLiteral #NullLiteral
	;

// added
type
	:	primitiveType
	|	referenceType
	;

// added
primitiveType
	:	annotation* numericType
	|	annotation* 'boolean'
	;

// added
numericType
	:	integralType
	|	floatingPointType
	;

// added
integralType
	:	'byte'
	|	'short'
	|	'int'
	|	'long'
	|	'char'
	;

// added
floatingPointType
	:	'float'
	|	'double'
	;

referenceType
	:	classOrInterfaceType
	|	typeVariable
	|	arrayType
	;

classOrInterfaceType
	:	(	classType_lfno_classOrInterfaceType
		|	interfaceType_lfno_classOrInterfaceType
		)
		(	classType_lf_classOrInterfaceType
		|	interfaceType_lf_classOrInterfaceType
		)*
	;

classType
	:	annotation* identifier typeArguments?
	|	classOrInterfaceType '.' annotation* identifier typeArguments?
	;

classType_lf_classOrInterfaceType
	:	'.' annotation* identifier typeArguments?
	;

classType_lfno_classOrInterfaceType
	:	annotation* identifier typeArguments?
	;

interfaceType
	:	classType
	;

interfaceType_lf_classOrInterfaceType
	:	classType_lf_classOrInterfaceType
	;

interfaceType_lfno_classOrInterfaceType
	:	classType_lfno_classOrInterfaceType
	;

typeVariable
	:	annotation* identifier
	;

arrayType
	:	arrayTypeName arrayDimension+
	;

arrayTypeName
    :   primitiveType
    |   classOrInterfaceType
    |   typeVariable
    ;

dims
	:	arrayDimension+
	;

arrayDimension
    :   '[' ']'
    ;

// added
typeParameter
	:	typeParameterModifier* identifier typeBound?
	;

// added
typeParameterModifier
	:	annotation
	;

// added
typeBound
	:	'extends' typeVariable                          #simpleTypeBound
	|	'extends' classOrInterfaceType additionalBound* #classTypeBound
	;

// added
additionalBound
	:	'&' interfaceType
	;

// added
typeArguments
	:	'<' typeArgumentList '>'
	;

// added
typeArgumentList
	:	typeArgument (',' typeArgument)*
	;

// added
typeArgument
	:	referenceType
	|	wildcard
	;

// added
wildcard
	:	'?' wildcardBounds?
	;

// added
wildcardBounds
	:	boundType=('extends'|'super') referenceType
	;

// added
packageName
	:	Identifier
	|	packageName '.' Identifier
	;

// added
typeName
	:	Identifier
	|	packageOrTypeName '.' Identifier
	;

// added
packageOrTypeName
	:	Identifier
	|	packageOrTypeName '.' Identifier
	;

// added
expressionName
	:	Identifier
	|	ambiguousName '.' Identifier
	;

// added
ambiguousName
	:	Identifier
	|	ambiguousName '.' Identifier
	;

// common entry point
// added
compilationUnit
	:	packageDeclaration? importDeclaration* typeDeclaration* EOF
	;

// added
packageDeclaration
	:	'package' packageName ';'
	;

// added
import_static: 'static';
// added
import_wildcard: '.' '*';
// added
importDeclaration
    : 'import' import_static? packageOrTypeName import_wildcard? ';'
    ;
    
// added
typeDeclaration
	:	classDeclaration
	|   enumDeclaration
	|	interfaceDeclaration
	|	';'
	;

// added
classDeclaration
	:	annotation* classModifier* 'class' identifier typeParameters? superclass? superinterfaces? classBody
	;

// added
classModifier
	:	'public'
	|	'protected'
	|	'private'
	|	'abstract'
	|	'static'
	|	'final'
	|	'strictfp'
	;

// added
typeParameterList
	:	typeParameter (',' typeParameter)*
	;
	
// added
typeParameters
	:	'<' typeParameterList '>'
	;

// added
superclass
	:	'extends' classType
	;

// added
superinterfaces
	:	'implements' interfaceTypeList
	;

// added
interfaceTypeList
	:	interfaceType (',' interfaceType)*
	;

// added
classBody
	:	'{' classBodyDeclaration* '}'
	;

classBodyDeclaration
	:	fieldDeclaration
	|	methodDeclaration
	|	classDeclaration
	|	interfaceDeclaration
	|	emptyStatement
	|	instanceInitializer
	|	staticInitializer
	|	constructorDeclaration
	;

fieldDeclaration
	:	fieldModifier* unannType variableDeclaratorList ';'
	;

fieldModifier
	:	annotation
	|	'public'
	|	'protected'
	|	'private'
	|	'static'
	|	'final'
	|	'transient'
	|	'volatile'
	;

variableDeclaratorList
	:	variableDeclarator (',' variableDeclarator)*
	;

variableDeclarator
	:	variableDeclaratorId ('=' variableInitializer)?
	;

variableDeclaratorId
	:	identifier arrayDimension*
	;

variableInitializer
	:	expression
	|	arrayInitializer
	;

unannType
	:	unannPrimitiveType
	|	unannReferenceType
	;

unannPrimitiveType
	:	numericType
	|	'boolean'
	;

unannReferenceType
	:	unannClassOrInterfaceType
	|	unannTypeVariable
	|	unannArrayType
	;

unannClassOrInterfaceType
	:	(	unannClassType_lfno_unannClassOrInterfaceType
		|	unannInterfaceType_lfno_unannClassOrInterfaceType
		)
		(	unannClassType_lf_unannClassOrInterfaceType
		|	unannInterfaceType_lf_unannClassOrInterfaceType
		)*
	;

unannClassType
	:	Identifier typeArguments?
	|	unannClassOrInterfaceType '.' annotation* Identifier typeArguments?
	;

unannClassType_lf_unannClassOrInterfaceType
	:	'.' annotation* Identifier typeArguments?
	;

unannClassType_lfno_unannClassOrInterfaceType
	:	Identifier typeArguments?
	;

unannInterfaceType
	:	unannClassType
	;

unannInterfaceType_lf_unannClassOrInterfaceType
	:	unannClassType_lf_unannClassOrInterfaceType
	;

unannInterfaceType_lfno_unannClassOrInterfaceType
	:	unannClassType_lfno_unannClassOrInterfaceType
	;

unannTypeVariable
	:	Identifier
	;

unannArrayType
	:	unannPrimitiveType arrayDimension+
	|	unannClassOrInterfaceType arrayDimension+
	|	unannTypeVariable arrayDimension+
	;

// added
methodDeclaration
	:	annotation* methodModifier* methodHeader methodBody
	;

// added
methodModifier
	:	'public'
	|	'protected'
	|	'private'
	|	'abstract'
	|	'static'
	|	'final'
	|	'synchronized'
	|	'native'
	|	'strictfp'
	;

// added
methodHeader
	:	result methodDeclarator throws_?
	|	typeParameters annotation* result methodDeclarator throws_?
	;

// added
result
	:	unannType
	|	'void'
	;

// added
methodDeclarator
	:	identifier '(' formalParameterList? ')' 
	;
	
// added
formalParameterList
	:	receiverParameter
	|	formalParameters ',' lastFormalParameter
	|	lastFormalParameter
	;
// added
formalParameters
	:	formalParameter (',' formalParameter)*
	|	receiverParameter (',' formalParameter)*
	;
// added
formalParameter
	:	annotation* variableModifier* unannType variableDeclaratorId
	;
// added
variableModifier 
    :	'final'
	;
// added
lastFormalParameter
	:	variableModifier* unannType annotation* '...' variableDeclaratorId
	|	formalParameter
	;

receiverParameter
	:	annotation* unannType (identifier '.')? 'this'
	;

// added
throws_
	:	'throws' exceptionTypeList
	;

// added
exceptionTypeList
	:	exceptionType (',' exceptionType)*
	;

// added
exceptionType
	:	classType
	|	typeVariable
	;

// added
methodBody
	:	block
	|	';'
	;

// added
instanceInitializer
	:	block
	;

// added
staticInitializer
	:	'static' block
	;

// added
constructorDeclaration
	:	annotation* constructorModifier* constructorDeclarator throws_? constructorBody
	;

// added
constructorModifier
	:	'public'
	|	'protected'
	|	'private'
	;

// added
constructorDeclarator
	:	typeParameters? simpleTypeName '(' formalParameterList? ')'
	;

simpleTypeName
	:	Identifier
	;

// added
constructorBody
	:	'{' explicitConstructorInvocation? blockStatements? '}'
	;

explicitConstructorInvocation
	:	typeArguments? 'this' '(' argumentList? ')' ';'                     
	|	typeArguments? 'super' '(' argumentList? ')' ';'
	|	expressionName '.' typeArguments? 'super' '(' argumentList? ')' ';'
	|	primary '.' typeArguments? 'super' '(' argumentList? ')' ';'
	;

// added
enumDeclaration
	:	annotation* classModifier* 'enum' identifier superinterfaces? enumBody
	;

// added
enumBody
	:	'{' enumConstantList? ','? enumBodyDeclarations? '}'
	;

// added
enumConstantList
	:	enumConstant (',' enumConstant)*
	;

// added
enumConstant
	:	annotation* identifier ('(' argumentList? ')')? classBody?
	;

// added
enumBodyDeclarations
	:	';' classBodyDeclaration*
	;

/*
 * Productions from §9 (Interfaces)
 */

// added
interfaceDeclaration
	:	normalInterfaceDeclaration
	|	annotationTypeDeclaration
	;

// added
normalInterfaceDeclaration
	:	annotation* interfaceModifier* 'interface' identifier typeParameters? extendsInterfaces? interfaceBody
	;

// added
interfaceModifier
	:	'public'
	|	'protected'
	|	'private'
	|	'abstract'
	|	'static'
	|	'strictfp'
	;

// added
extendsInterfaces
	:	'extends' interfaceTypeList
	;

// added
interfaceBody
	:	'{' interfaceMemberDeclaration* '}'
	;

// added
interfaceMemberDeclaration
	:	constantDeclaration
	|	interfaceMethodDeclaration
	|	classDeclaration
	|	interfaceDeclaration
	|   emptyStatement	
	;

// added
constantDeclaration
	:	annotation* constantModifier* unannType variableDeclaratorList ';'
	;

// added
constantModifier
	:	'public'
	|	'static'
	|	'final'
	;

// added
interfaceMethodDeclaration
	:	annotation* interfaceMethodModifier* methodHeader methodBody
	;

// added
interfaceMethodModifier
	:	'public'
	|	'abstract'
	|	'default'
	|	'static'
	|	'strictfp'
	;

// added
annotationTypeDeclaration
	:	annotation* interfaceModifier* '@' 'interface' identifier annotationTypeBody
	;

// added
annotationTypeBody
	:	'{' annotationTypeMemberDeclaration* '}'
	;

// added
annotationTypeMemberDeclaration
	:	annotationTypeElementDeclaration
	|	constantDeclaration
	|	classDeclaration
	|	interfaceDeclaration
	|	';'
	;

// added
annotationTypeElementDeclaration
	:	annotation* annotationTypeElementModifier* unannType identifier '(' ')' arrayDimension* defaultValue? ';'
	;

// added
annotationTypeElementModifier
	:	'public'
	|	'abstract'
	;

// added
defaultValue
	:	'default' elementValue
	;

// added
annotation
	:	normalAnnotation
	|	markerAnnotation
	|	singleElementAnnotation
	;

// added
normalAnnotation
	:	'@' typeName '(' elementValuePairList? ')'
	;

// added
elementValuePairList
	:	elementValuePair (',' elementValuePair)*
	;

// added
elementValuePair
	:	identifier '=' elementValue
	;

// added
elementValue
	:	conditionalExpression           #elementValueExpression
	|	elementValueArrayInitializer    #elementValueArray
	|	annotation                      #elementValueAnnotation
	;

elementValueArrayInitializer
	:	'{' elementValueList? ','? '}'
	;

// added
elementValueList
	:	elementValue (',' elementValue)*
	;

// added
markerAnnotation
	:	'@' typeName
	;

// added
singleElementAnnotation
	:	'@' typeName '(' elementValue ')'
	;

/*
 * Productions from §10 (Arrays)
 */

arrayInitializer
	:	'{' variableInitializerList? ','? '}'
	;

variableInitializerList
	:	variableInitializer (',' variableInitializer)*
	;

/*
 * Productions from §14 (Blocks and Statements)
 */

// added
block
	:	'{' blockStatement* '}'
	;

// added
blockStatements
	:	blockStatement+
	;

// added
blockStatement
	:	localVariableDeclarationStatement
	|	classDeclaration
	|	statement
	;

// added
localVariableDeclarationStatement
	:	localVariableDeclaration ';'
	;

// added
localVariableDeclaration
	:	annotation* variableModifier* unannType variableDeclaratorList
	;

// added
statement
	:	statementWithoutTrailingSubstatement
	|	labeledStatement
	|	ifThenStatement
	|	ifThenElseStatement
	|	whileStatement
	|	forStatement
	;

// added
statementNoShortIf
	:	statementWithoutTrailingSubstatement
	|	labeledStatementNoShortIf
	|	ifThenElseStatementNoShortIf
	|	whileStatementNoShortIf
	|	forStatementNoShortIf
	;

// added
statementWithoutTrailingSubstatement
	:	block
	|	emptyStatement
	|	expressionStatement
	|	assertStatement
	|	switchStatement
	|	doStatement
	|	breakStatement
	|	continueStatement
	|	returnStatement
	|	synchronizedStatement
	|	throwStatement
	|	tryStatement
	;

// added
emptyStatement
	:	';'
	;

// added
labeledStatement
	:	identifier ':' statement
	;

// added
labeledStatementNoShortIf
	:	identifier ':' statementNoShortIf
	;

// added
expressionStatement
	:	statementExpression ';'
	;

// added
statementExpression
	:	assignment                      #assignementStatement
	|	preIncrementExpression          #preIncrementStatement
	|	preDecrementExpression          #preDecrementStatement
	|	postIncrementExpression         #postIncrementStatement
	|	postDecrementExpression         #postDecrementStatement
	|	methodInvocation                #methodInvocationStatement
	|	classInstanceCreationExpression #classInstanceCreationStatement
	;

// added
ifThenStatement
	:	'if' '(' expression ')' statement
	;

// added
ifThenElseStatement
	:	'if' '(' expression ')' statementNoShortIf 'else' statement
	;

// added
ifThenElseStatementNoShortIf
	:	'if' '(' expression ')' statementNoShortIf 'else' statementNoShortIf
	;

// added
assertStatement
	:	'assert' expression ';'
	|	'assert' expression ':' expression ';'
	;

switchStatement
	:	'switch' '(' expression ')' switchBlock
	;

switchBlock
	:	'{' switchBlockStatementGroup* switchLabel* '}'
	;

switchBlockStatementGroup
	:	switchLabels blockStatements
	;

switchLabels
	:	switchLabel switchLabel*
	;

switchLabel
	:	'case' constantExpression ':'
	|	'case' enumConstantName ':'
	|	'default' ':'
	;

enumConstantName
	:	identifier
	;

// added
whileStatement
	:	'while' '(' expression ')' statement
	;

// added
whileStatementNoShortIf
	:	'while' '(' expression ')' statementNoShortIf
	;

// added
doStatement
	:	'do' statement 'while' '(' expression ')' ';'
	;

// added
forStatement
	:	basicForStatement
	|	enhancedForStatement
	;

// added
forStatementNoShortIf
	:	basicForStatementNoShortIf
	|	enhancedForStatementNoShortIf
	;

// added
basicForStatement
	:	'for' '(' forInit? ';' expression? ';' forUpdate? ')' statement
	;

// added
basicForStatementNoShortIf
	:	'for' '(' forInit? ';' expression? ';' forUpdate? ')' statementNoShortIf
	;

forInit
	:	statementExpressionList
	|	localVariableDeclaration
	;

forUpdate
	:	statementExpressionList
	;

// added
statementExpressionList
	:	statementExpression (',' statementExpression)*
	;

// added
enhancedForStatement
	:	'for' '(' variableModifier* unannType variableDeclaratorId ':' expression ')' statement
	;

// added
enhancedForStatementNoShortIf
	:	'for' '(' variableModifier* unannType variableDeclaratorId ':' expression ')' statementNoShortIf
	;

// added
breakStatement
	:	'break' identifier? ';'
	;

// added
continueStatement
	:	'continue' identifier? ';'
	;

// added
returnStatement
	:	'return' expression? ';'
	;

// added
throwStatement
	:	'throw' expression ';'
	;

// added
synchronizedStatement
	:	'synchronized' '(' expression ')' block
	;

// added
tryStatement
    :   basicTryStatement
	|	tryWithResourcesStatement
	;

// added
basicTryStatement
    :   'try' block catches             #tryCatchStatement
    |   'try' block catches? finally_   #tryCatchFinallyStatement
    ;
// added
catches
	:	catchClause catchClause*
	;

// added
catchClause
	:	'catch' '(' variableModifier* unannClassType ('|' classType)* variableDeclaratorId ')' block
	;

// added
finally_
	:	'finally' block
	;

tryWithResourcesStatement
	:	'try' resourceSpecification block catches? finally_?
	;

resourceSpecification
	:	'(' resourceList ';'? ')'
	;

resourceList
	:	resource (';' resource)*
	;

resource
	:	variableModifier* unannType variableDeclaratorId '=' expression
	;

/*
 * Productions from §15 (Expressions)
 */

primary
	:	(	primaryNoNewArray_lfno_primary
		|	arrayCreationExpression
		)
		(	primaryNoNewArray_lf_primary
		)*
	;
	
// added
expressionParenthesis
    :   '(' expression ')'
    ;

primaryNoNewArray_lf_arrayAccess
	:
	;

primaryNoNewArray_lfno_arrayAccess
	:	literal
	|	typeName ('[' ']')* '.' 'class'
	|	'void' '.' 'class'
	|	'this'
	|	typeName '.' 'this'
	|   expressionParenthesis 
	|	classInstanceCreationExpression
	|	fieldAccess
	|	methodInvocation
	|	methodReference
	;

primaryNoNewArray_lf_primary
	:	classInstanceCreationExpression_lf_primary
	|	fieldAccess_lf_primary
	|	arrayAccess_lf_primary
	|	methodInvocation_lf_primary
	|	methodReference_lf_primary
	;

primaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary
	:
	;

primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary
	:	classInstanceCreationExpression_lf_primary
	|	fieldAccess_lf_primary
	|	methodInvocation_lf_primary
	|	methodReference_lf_primary
	;
	
primaryClassType
    : primaryClassTypeAlternates bracketPair* '.' 'class'
    ;

primaryClassTypeAlternates
    : typeName
    | unannPrimitiveType
    | 'void'
    ;

bracketPair
    : '[' ']'
    ;
    
primaryNoNewArray_lfno_primary
	:	literal
	|	primaryClassType
	|	'this'
	|	typeName '.' 'this'
	|   expressionParenthesis	
	|	classInstanceCreationExpression_lfno_primary
	|	fieldAccess_lfno_primary
	|	arrayAccess_lfno_primary
	|	methodInvocation_lfno_primary
	|	methodReference_lfno_primary
	;

primaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary
	:
	;

primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary
	:	literal
	|	primaryClassType
	|	'this'
	|	typeName '.' 'this'
	|	expressionParenthesis
	|	classInstanceCreationExpression_lfno_primary
	|	fieldAccess_lfno_primary
	|	methodInvocation_lfno_primary
	|	methodReference_lfno_primary
	;

classIdentifier
    : Identifier ('.' Identifier)*
    ;
    
classInstanceCreationExpression
	:	'new' typeArguments?  classIdentifier typeArgumentsOrDiamond? 
	    '(' argumentList? ')' classBody?
	;
	
classInstanceCreationExpression_lf_primary
	:	'.' 'new' typeArguments? annotation* Identifier typeArgumentsOrDiamond? '(' argumentList? ')' classBody?
	;

classInstanceCreationExpression_lfno_primary
	:	'new' typeArguments? annotation* Identifier ('.' annotation* Identifier)* typeArgumentsOrDiamond? '(' argumentList? ')' classBody?
	|	expressionName '.' 'new' typeArguments? annotation* Identifier typeArgumentsOrDiamond? '(' argumentList? ')' classBody?
	;

// added
typeArgumentsOrDiamond
	:	typeArguments   #ig22
	|	'<' '>'         #diamond
	;

fieldAccess
	:	primary '.' identifier
	|	'super' '.' identifier
	|	typeName '.' 'super' '.' identifier
	;

fieldAccess_lf_primary
	:	'.' identifier
	;

fieldAccess_lfno_primary
	:	'super' '.' identifier
	|	typeName '.' 'super' '.' identifier
	;

arrayAccess
	:	(	expressionName '[' expression ']'
		|	primaryNoNewArray_lfno_arrayAccess '[' expression ']'
		)
		(	primaryNoNewArray_lf_arrayAccess '[' expression ']'
		)*
	;

arrayAccess_lf_primary
	:	(	primaryNoNewArray_lf_primary_lfno_arrayAccess_lf_primary '[' expression ']'
		)
		(	primaryNoNewArray_lf_primary_lf_arrayAccess_lf_primary '[' expression ']'
		)*
	;

arrayAccess_lfno_primary
	:	(	expressionName '[' expression ']'
		|	primaryNoNewArray_lfno_primary_lfno_arrayAccess_lfno_primary '[' expression ']'
		)
		(	primaryNoNewArray_lfno_primary_lf_arrayAccess_lfno_primary '[' expression ']'
		)*
	;

methodInvocation
	:	methodArea? typeArguments? identifier '(' argumentList? ')'
	;
	
methodArea
    :   typeName
    |   expressionName
    |   primary
    |   'super' '.'
    |   typeName '.' 'super' '.'
    ;
    
methodInvocation_lf_primary
	:	'.' typeArguments? identifier '(' argumentList? ')'
	;

methodInvocation_lfno_primary
	:	identifier '(' argumentList? ')'
	|	typeName '.' typeArguments? identifier '(' argumentList? ')'
	|	expressionName '.' typeArguments? identifier '(' argumentList? ')'
	|	'super' '.' typeArguments? identifier '(' argumentList? ')'
	|	typeName '.' 'super' '.' typeArguments? identifier '(' argumentList? ')'
	;

argumentList
	:	expression (',' expression)*
	;

methodReference
	:	expressionName '::' typeArguments? identifier
	|	referenceType '::' typeArguments? identifier
	|	primary '::' typeArguments? identifier
	|	'super' '::' typeArguments? identifier
	|	typeName '.' 'super' '::' typeArguments? identifier
	|	classType '::' typeArguments? 'new'
	|	arrayType '::' 'new'
	;

methodReference_lf_primary
	:	'::' typeArguments? identifier
	;

methodReference_lfno_primary
	:	expressionName '::' typeArguments? identifier
	|	referenceType '::' typeArguments? identifier
	|	'super' '::' typeArguments? identifier
	|	typeName '.' 'super' '::' typeArguments? identifier
	|	classType '::' typeArguments? 'new'
	|	arrayType '::' 'new'
	;

arrayCreationExpression
	:	'new' primitiveType dimExprs dims?
	|	'new' classOrInterfaceType dimExprs dims?
	|	'new' primitiveType dims arrayInitializer
	|	'new' classOrInterfaceType dims arrayInitializer
	;

dimExprs
	:	dimExpr dimExpr*
	;

dimExpr
	:	annotation* '[' expression ']'
	;

constantExpression
	:	expression
	;

// added
expression
	:	lambdaExpression
	|	assignmentExpression
	;

// added
lambdaExpression
	:	lambdaParameters '->' lambdaBody
	;

// added
lambdaParameters
	:	identifier                          #lambdaIdentifierParameter
	|	'(' formalParameterList? ')'        #lambdaParameterList
	|	'(' inferredFormalParameterList ')' #lambdaInferedParameterList
	;

// added
inferredFormalParameterList
	:	identifier (',' identifier)*
	;

// added
lambdaBody
	:	expression
	|	block
	;

// added
assignmentExpression
	:	conditionalExpression
	|	assignment
	;

// added
assignment
	:	leftHandSide assignmentOperator expression
	;

leftHandSide
	:	expressionName
	|	fieldAccess
	|	arrayAccess
	;

// added
assignmentOperator
	:	'='
	|	'*='
	|	'/='
	|	'%='
	|	'+='
	|	'-='
	|	'<<='
	|	'>>='
	|	'>>>='
	|	'&='
	|	'^='
	|	'|='
	;
	
// added
conditionalExpression
	:	conditionalOrExpression 
	|	conditionalTernary
	;

conditionalTernary
	:	conditionalOrExpression '?' expression ':' conditionalExpression 
	;

// added
conditionalOrExpression
	:	conditionalAndExpression                                            #ig2
	|	conditionalOrExpression '||' conditionalAndExpression               #conditionalOr
	;

// added
conditionalAndExpression
	:	inclusiveOrExpression                                               #ig3
	|	conditionalAndExpression '&&' inclusiveOrExpression                 #conditionalAnd
	;

// added
inclusiveOrExpression
	:	exclusiveOrExpression                                               #ig4
	|	inclusiveOrExpression '|' exclusiveOrExpression                     #binaryInclusiveOr
	;

// added
exclusiveOrExpression
	:	andExpression                                                       #ig5
	|	exclusiveOrExpression '^' andExpression                             #binaryExclusiveOr
	;

// added
andExpression
	:	equalityExpression                                                  #ig6
	|	andExpression '&' equalityExpression                                #binaryAnd
	;

// added
equalityExpression
	:	relationalExpression                                                #ig7
	|	equalityExpression '==' relationalExpression                        #conditionalEquality
	|	equalityExpression '!=' relationalExpression                        #conditionalNotEquality
	;

// added
relationalExpression
	:	shiftExpression                                                     #ig8
	|	relationalExpression '<' shiftExpression                            #conditionalLessThan
	|	relationalExpression '>' shiftExpression                            #conditionalGreaterThan
	|	relationalExpression '<=' shiftExpression                           #conditionalLessThanEq
	|	relationalExpression '>=' shiftExpression                           #conditionalGreatherThanEq
	|	relationalExpression 'instanceof' referenceType                     #conditionalInstanceOf
	;

// added
shiftExpression
	:	additiveExpression                                                  #ig9
	|	shiftExpression '<' '<' additiveExpression                          #binaryShiftLeft
	|	shiftExpression '>' '>' additiveExpression                          #binaryShiftRight
	|	shiftExpression '>' '>' '>' additiveExpression                      #binarcyAllignRight
	;

// added
additiveExpression
	:	multiplicativeExpression                                            #ig10
	|	additiveExpression '+' multiplicativeExpression                     #expressionAdd
	|	additiveExpression '-' multiplicativeExpression                     #expressionSubtract
	;

// added
multiplicativeExpression
	:	unaryExpression                                                     #ig11
	|	multiplicativeExpression '*' unaryExpression                        #expressionMultiply
	|	multiplicativeExpression '/' unaryExpression                        #expressionDivide
	|	multiplicativeExpression '%' unaryExpression                        #expressionModulus
	;

// added
unaryExpression
	:	preIncrementExpression                                              #ig12
	|	preDecrementExpression                                              #ig13
	|	'+' unaryExpression                                                 #idk_plus
	|	'-' unaryExpression                                                 #idk_minus
	|	unaryExpressionNotPlusMinus                                         #ig14
	;

// added
preIncrementExpression
	:	'++' unaryExpression
	;

// added
preDecrementExpression
	:	'--' unaryExpression
	;

// added
unaryExpressionNotPlusMinus
	:	postfixExpression                                                   #ig15
	|	'~' unaryExpression                                                 #binaryNot
	|	'!' unaryExpression                                                 #conditionalNot
	|	castExpression                                                      #ig16
	;

postfixExpression
    :   primary postfixIncString          #primaryPostInc
    |   expressionName postfixIncString   #expressionPostInc
    |   primary postfixDecString          #primaryPostDec
    |   expressionName postfixDecString   #expressionPostDec
    |   (primary | expressionName)        #ig72
	;

// added
postIncrementExpression
	:	postfixExpression '++'
	;

// added
postfixIncString
	:	'++'
	;

// added
postDecrementExpression
	:	postfixExpression '--'
	;

// added
postfixDecString
	:	'--'
	;

castExpression
	:	'(' primitiveType ')' unaryExpression                               #castSimple
	|	'(' referenceType additionalBound* ')' unaryExpressionNotPlusMinus  #castSimpleBound
	|	'(' referenceType additionalBound* ')' lambdaExpression             #castLamda
	;

// Misc

// added
identifier
    :   Identifier
    ;

// LEXER

// §3.9 Keywords

ABSTRACT : 'abstract';
ASSERT : 'assert';
BOOLEAN : 'boolean';
BREAK : 'break';
BYTE : 'byte';
CASE : 'case';
CATCH : 'catch';
CHAR : 'char';
CLASS : 'class';
CONST : 'const';
CONTINUE : 'continue';
DEFAULT : 'default';
DO : 'do';
DOUBLE : 'double';
ELSE : 'else';
ENUM : 'enum';
EXTENDS : 'extends';
FINAL : 'final';
FINALLY : 'finally';
FLOAT : 'float';
FOR : 'for';
IF : 'if';
GOTO : 'goto';
IMPLEMENTS : 'implements';
IMPORT : 'import';
INSTANCEOF : 'instanceof';
INT : 'int';
INTERFACE : 'interface';
LONG : 'long';
NATIVE : 'native';
NEW : 'new';
PACKAGE : 'package';
PRIVATE : 'private';
PROTECTED : 'protected';
PUBLIC : 'public';
RETURN : 'return';
SHORT : 'short';
STATIC : 'static';
STRICTFP : 'strictfp';
SUPER : 'super';
SWITCH : 'switch';
SYNCHRONIZED : 'synchronized';
THIS : 'this';
THROW : 'throw';
THROWS : 'throws';
TRANSIENT : 'transient';
TRY : 'try';
VOID : 'void';
VOLATILE : 'volatile';
WHILE : 'while';

// §3.10.1 Integer Literals

IntegerLiteral
	:	DecimalIntegerLiteral
	|	HexIntegerLiteral
	|	OctalIntegerLiteral
	|	BinaryIntegerLiteral
	;

fragment
DecimalIntegerLiteral
	:	DecimalNumeral IntegerTypeSuffix?
	;

fragment
HexIntegerLiteral
	:	HexNumeral IntegerTypeSuffix?
	;

fragment
OctalIntegerLiteral
	:	OctalNumeral IntegerTypeSuffix?
	;

fragment
BinaryIntegerLiteral
	:	BinaryNumeral IntegerTypeSuffix?
	;

fragment
IntegerTypeSuffix
	:	[lL]
	;

fragment
DecimalNumeral
	:	'0'
	|	NonZeroDigit (Digits? | Underscores Digits)
	;

fragment
Digits
	:	Digit (DigitsAndUnderscores? Digit)?
	;

fragment
Digit
	:	'0'
	|	NonZeroDigit
	;

fragment
NonZeroDigit
	:	[1-9]
	;

fragment
DigitsAndUnderscores
	:	DigitOrUnderscore+
	;

fragment
DigitOrUnderscore
	:	Digit
	|	'_'
	;

fragment
Underscores
	:	'_'+
	;

fragment
HexNumeral
	:	'0' [xX] HexDigits
	;

fragment
HexDigits
	:	HexDigit (HexDigitsAndUnderscores? HexDigit)?
	;

fragment
HexDigit
	:	[0-9a-fA-F]
	;

fragment
HexDigitsAndUnderscores
	:	HexDigitOrUnderscore+
	;

fragment
HexDigitOrUnderscore
	:	HexDigit
	|	'_'
	;

fragment
OctalNumeral
	:	'0' Underscores? OctalDigits
	;

fragment
OctalDigits
	:	OctalDigit (OctalDigitsAndUnderscores? OctalDigit)?
	;

fragment
OctalDigit
	:	[0-7]
	;

fragment
OctalDigitsAndUnderscores
	:	OctalDigitOrUnderscore+
	;

fragment
OctalDigitOrUnderscore
	:	OctalDigit
	|	'_'
	;

fragment
BinaryNumeral
	:	'0' [bB] BinaryDigits
	;

fragment
BinaryDigits
	:	BinaryDigit (BinaryDigitsAndUnderscores? BinaryDigit)?
	;

fragment
BinaryDigit
	:	[01]
	;

fragment
BinaryDigitsAndUnderscores
	:	BinaryDigitOrUnderscore+
	;

fragment
BinaryDigitOrUnderscore
	:	BinaryDigit
	|	'_'
	;

// §3.10.2 Floating-Point Literals

FloatingPointLiteral
	:	DecimalFloatingPointLiteral
	|	HexadecimalFloatingPointLiteral
	;

fragment
DecimalFloatingPointLiteral
	:	Digits '.' Digits? ExponentPart? FloatTypeSuffix?
	|	'.' Digits ExponentPart? FloatTypeSuffix?
	|	Digits ExponentPart FloatTypeSuffix?
	|	Digits FloatTypeSuffix
	;

fragment
ExponentPart
	:	ExponentIndicator SignedInteger
	;

fragment
ExponentIndicator
	:	[eE]
	;

fragment
SignedInteger
	:	Sign? Digits
	;

fragment
Sign
	:	[+-]
	;

fragment
FloatTypeSuffix
	:	[fFdD]
	;

fragment
HexadecimalFloatingPointLiteral
	:	HexSignificand BinaryExponent FloatTypeSuffix?
	;

fragment
HexSignificand
	:	HexNumeral '.'?
	|	'0' [xX] HexDigits? '.' HexDigits
	;

fragment
BinaryExponent
	:	BinaryExponentIndicator SignedInteger
	;

fragment
BinaryExponentIndicator
	:	[pP]
	;

// §3.10.3 Boolean Literals

BooleanLiteral
	:	'true'
	|	'false'
	;

// §3.10.4 Character Literals

CharacterLiteral
	:	'\'' SingleCharacter '\''
	|	'\'' EscapeSequence '\''
	;

fragment
SingleCharacter
	:	~['\\\r\n]
	;

// §3.10.5 String Literals

StringLiteral
	:	'"' StringCharacters? '"'
	;

fragment
StringCharacters
	:	StringCharacter+
	;

fragment
StringCharacter
	:	~["\\\r\n]
	|	EscapeSequence
	;

// §3.10.6 Escape Sequences for Character and String Literals

fragment
EscapeSequence
	:	'\\' [btnfr"'\\]
	|	OctalEscape
    |   UnicodeEscape // This is not in the spec but prevents having to preprocess the input
	;

fragment
OctalEscape
	:	'\\' OctalDigit
	|	'\\' OctalDigit OctalDigit
	|	'\\' ZeroToThree OctalDigit OctalDigit
	;

fragment
ZeroToThree
	:	[0-3]
	;

// This is not in the spec but prevents having to preprocess the input
fragment
UnicodeEscape
    :   '\\' 'u'+  HexDigit HexDigit HexDigit HexDigit
    ;

// §3.10.7 The Null Literal

NullLiteral
	:	'null'
	;

// §3.11 Separators

LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';
LBRACK : '[';
RBRACK : ']';
SEMI : ';';
COMMA : ',';
DOT : '.';

// §3.12 Operators

ASSIGN : '=';
GT : '>';
LT : '<';
BANG : '!';
TILDE : '~';
QUESTION : '?';
COLON : ':';
EQUAL : '==';
LE : '<=';
GE : '>=';
NOTEQUAL : '!=';
AND : '&&';
OR : '||';
INC : '++';
DEC : '--';
ADD : '+';
SUB : '-';
MUL : '*';
DIV : '/';
BITAND : '&';
BITOR : '|';
CARET : '^';
MOD : '%';
ARROW : '->';
COLONCOLON : '::';

ADD_ASSIGN : '+=';
SUB_ASSIGN : '-=';
MUL_ASSIGN : '*=';
DIV_ASSIGN : '/=';
AND_ASSIGN : '&=';
OR_ASSIGN : '|=';
XOR_ASSIGN : '^=';
MOD_ASSIGN : '%=';
LSHIFT_ASSIGN : '<<=';
RSHIFT_ASSIGN : '>>=';
URSHIFT_ASSIGN : '>>>=';

// §3.8 Identifiers (must appear after all keywords in the grammar)

Identifier
	:	JavaLetter JavaLetterOrDigit*
	;

fragment
JavaLetter
	:	[a-zA-Z$_] // these are the "java letters" below 0x7F
	|	// covers all characters above 0x7F which are not a surrogate
		~[\u0000-\u007F\uD800-\uDBFF]
		{Character.isJavaIdentifierStart(_input.LA(-1))}?
	|	// covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
		[\uD800-\uDBFF] [\uDC00-\uDFFF]
		{Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
	;

fragment
JavaLetterOrDigit
	:	[a-zA-Z0-9$_] // these are the "java letters or digits" below 0x7F
	|	// covers all characters above 0x7F which are not a surrogate
		~[\u0000-\u007F\uD800-\uDBFF]
		{Character.isJavaIdentifierPart(_input.LA(-1))}?
	|	// covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
		[\uD800-\uDBFF] [\uDC00-\uDFFF]
		{Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
	;

//
// Additional symbols not defined in the lexical specification
//

AT : '@';
ELLIPSIS : '...';

//
// Whitespace and comments
//

WS  :  [ \t\r\n\u000C]+ -> skip
    ;

COMMENT
    :   '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip
    ;