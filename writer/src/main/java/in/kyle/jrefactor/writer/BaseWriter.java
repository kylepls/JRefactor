package in.kyle.jrefactor.writer;

import java.util.Iterator;

import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.JObjectList;
import in.kyle.jrefactor.parser.expression.*;
import in.kyle.jrefactor.parser.expression.lambda.JIdentifierParameter;
import in.kyle.jrefactor.parser.expression.lambda.JInferredParameters;
import in.kyle.jrefactor.parser.expression.lambda.JLambdaBody;
import in.kyle.jrefactor.parser.expression.lambda.JLambdaExpression;
import in.kyle.jrefactor.parser.expression.lambda.JLambdaParameters;
import in.kyle.jrefactor.parser.expression.literal.*;
import in.kyle.jrefactor.parser.statement.*;
import in.kyle.jrefactor.parser.statement.control.*;
import in.kyle.jrefactor.parser.statement.control.loops.JBasicForStatement;
import in.kyle.jrefactor.parser.statement.control.loops.JDoWhileStatement;
import in.kyle.jrefactor.parser.statement.control.loops.JEnhancedForStatement;
import in.kyle.jrefactor.parser.statement.control.loops.JLoopStatement;
import in.kyle.jrefactor.parser.statement.control.loops.JWhileStatement;
import in.kyle.jrefactor.parser.statement.control.loops.WhileStatement;
import in.kyle.jrefactor.parser.unit.*;
import in.kyle.jrefactor.parser.unit.body.*;
import in.kyle.jrefactor.parser.unit.types.JAnnotationDeclaration;
import in.kyle.jrefactor.parser.unit.types.JClassDeclaration;
import in.kyle.jrefactor.parser.unit.types.JEnumDeclaration;
import in.kyle.jrefactor.parser.unit.types.JInterfaceDeclaration;
import in.kyle.jrefactor.parser.unit.types.annotationtype.JAnnotationBody;
import in.kyle.jrefactor.parser.unit.types.annotationtype.JAnnotationMember;
import in.kyle.jrefactor.parser.unit.types.annotationtype.JAnnotationTypeElement;
import in.kyle.jrefactor.parser.unit.types.annotationtype.JElementValue;
import in.kyle.jrefactor.parser.unit.types.classtype.JClassBody;
import in.kyle.jrefactor.parser.unit.types.classtype.JClassInitializer;
import in.kyle.jrefactor.parser.unit.types.classtype.JClassInstanceInitializer;
import in.kyle.jrefactor.parser.unit.types.classtype.JClassMember;
import in.kyle.jrefactor.parser.unit.types.classtype.JClassStaticInitializer;
import in.kyle.jrefactor.parser.unit.types.classtype.JField;
import in.kyle.jrefactor.parser.unit.types.enumtype.JEnumBody;
import in.kyle.jrefactor.parser.unit.types.enumtype.JEnumConstant;
import in.kyle.jrefactor.parser.unit.types.enumtype.JEnumMember;
import in.kyle.jrefactor.parser.unit.types.interfacetype.JInterfaceBody;
import in.kyle.jrefactor.parser.unit.types.interfacetype.JInterfaceMember;
import in.kyle.jrefactor.parser.unit.types.interfacetype.JInterfaceMethod;

public class BaseWriter extends AbstractWriter {
    
    @Override
    public void write(JAssignment.JAssignmentOperator object) {
        writeString(object.getJavaString());
    }
    
    @Override
    public void write(JAssignment object) {
        write(object.getLeft());
        writeString(" ");
        write(object.getOperator());
        writeString(" ");
        write(object.getRight());
    }
    
    @Override
    public void write(JClassInstanceCreationExpression object) {
        writeString("new ");
        write(object.getType());
        write(object.getTypeArgumentList());
        writeString("(");
        write(object.getArgumentList());
        writeString(")");
        object.getBody().ifPresent(this::write);
    }
    
    @Override
    public void write(JExpression object) {
    }
    
    @Override
    public void write(JExpressionName object) {
        write(object.getIdentifier());
    }
    
    @Override
    public void write(JLeftRightExpression.Operation object) {
        writeString(object.getJavaString());
    }
    
    @Override
    public void write(JLeftRightExpression object) {
        write(object.getLeft());
        write(object.getOperation());
        write(object.getRight());
    }
    
    @Override
    public void write(JMethodInvocation object) {
        if (object.getMethodArea().isPresent()) {
            writeString(object.getMethodArea().get());
            writeString(" ");
        }
        write(object.getTypeArgumentList());
        write(object.getIdentifier());
        writeString("(");
        write(object.getArgumentList());
        writeString(")");
    }
    
    @Override
    public void write(JParenthesisExpression object) {
        writeString("(");
        write(object.getValue());
        writeString(")");
    }
    
    @Override
    public void write(JTernaryExpression object) {
        write(object.getCondition());
        writeString(" ? ");
        write(object.getLeft());
        writeString(" : ");
        write(object.getRight());
    }
    
    @Override
    public void write(JTypeReferenceExpression object) {
        write(object.getReference());
        writeString(".class");
    }
    
    @Override
    public void write(JUnaryExpression object) {
        if (object.getOperator().isBeforeOperator()) {
            write(object.getOperator());
            write(object.getExpression());
        } else {
            write(object.getExpression());
            write(object.getOperator());
        }
    }
    
    @Override
    public void write(JIdentifierParameter object) {
        write((JIdentifier) object);
        
    }
    
    @Override
    public void write(JInferredParameters object) {
        writeString("(");
        write((JObjectList) object);
        writeString(")");
    }
    
    @Override
    public void write(JLambdaBody object) {
    }
    
    @Override
    public void write(JLambdaExpression object) {
        write(object.getParameters());
        writeString(" -> ");
        write(object.getBody());
    }
    
    @Override
    public void write(JLambdaParameters object) {
    }
    
    @Override
    public void write(JBooleanLiteral object) {
        writeString(object.getValue().toString());
    }
    
    @Override
    public void write(JCharacterLiteral object) {
        writeString("'");
        writeString(object.getValue().toString());
        writeString("'");
    }
    
    @Override
    public void write(JDoubleLiteral object) {
        writeString(object.getValue().toString());
    }
    
    @Override
    public void write(JFloatingLiteral object) {
        writeString(object.getValue().toString());
    }
    
    @Override
    public void write(JFloatLiteral object) {
        writeString(object.getValue().toString());
    }
    
    @Override
    public void write(JIntegerLiteral object) {
        writeString(object.getValue().toString());
    }
    
    @Override
    public void write(JLiteral object) {
    }
    
    @Override
    public void write(JLongLiteral object) {
        writeString(object.getValue().toString());
        writeString("L");
    }
    
    @Override
    public void write(JNumericLiteral object) {
    }
    
    @Override
    public void write(JStringLiteral object) {
        writeString("\"");
        writeString(object.getValue());
        writeString("\"");
    }
    
    @Override
    public void write(JObject object) {
    }
    
    @Override
    public void write(JObjectList object) {
        for (Iterator iterator = object.iterator(); iterator.hasNext(); ) {
            JObject o = (JObject) iterator.next();
            write(o);
            if (iterator.hasNext()) {
                writeString(", ");
            }
        }
    }
    
    @Override
    public void write(JBreakStatement object) {
        writeString("break");
        
        if (object.getIdentifier().isPresent()) {
            writeString(" ");
            write(object.getIdentifier().get());
        }
    }
    
    @Override
    public void write(JCatchClause object) {
        writeString("catch (");
        for (Iterator<JTypeName> iterator =
             object.getCatchTypes().iterator(); iterator.hasNext(); ) {
            JTypeName jTypeName = iterator.next();
            write(jTypeName);
            if (iterator.hasNext()) {
                writeString(" | ");
            }
        }
        writeString(" ");
        write(object.getVariable());
        writeString(")");
        
        write(object.getBlock());
    }
    
    @Override
    public void write(JContinueStatement object) {
        writeString("continue");
        if (object.getIdentifier().isPresent()) {
            writeString(" ");
            write(object.getIdentifier().get());
        }
    }
    
    @Override
    public void write(JControlStatement object) {
    }
    
    @Override
    public void write(JIfThenElseStatement object) {
        writeString("if (");
        write(object.getExpression());
        writeString(") ");
        write(object.getIfCondition());
        writeString("else ");
        write(object.getElseCondition());
    }
    
    @Override
    public void write(JIfThenStatement object) {
        writeString("if (");
        write(object.getExpression());
        writeString(") ");
        write(object.getStatement());
    }
    
    @Override
    public void write(JReturnStatement object) {
        writeString("return");
        if (object.getExpression().isPresent()) {
            writeString(" ");
            write(object.getExpression().get());
        }
    }
    
    @Override
    public void write(JSwitchStatement object) {
    }
    
    @Override
    public void write(JSynchronizedStatement object) {
        writeString("synchronized (");
        write(object.getExpression());
        writeString(")");
        write(object.getBlock());
    }
    
    @Override
    public void write(JThrowStatement object) {
        writeString("throw ");
        write(object.getExpression());
    }
    
    @Override
    public void write(JTryStatement object) {
        writeString("try ");
        write(object.getBlock());
        for (JCatchClause catchClause : object.getCatchClauses()) {
            write(catchClause);
        }
        if (object.getFinallyBlock().isPresent()) {
            writeString("finally ");
            write(object.getFinallyBlock().get());
        }
    }
    
    @Override
    public void write(JTryWithResourcesStatement object) {
        
    }
    
    @Override
    public void write(JBasicForStatement object) {
        writeString("for (");
        object.getInit().ifPresent(this::write);
        writeString("; ");
        object.getCondition().ifPresent(this::write);
        writeString("; ");
        object.getUpdate().ifPresent(this::write);
        writeString(") ");
        write(object.getStatement());
    }
    
    @Override
    public void write(JDoWhileStatement object) {
        writeString("do ");
        write(object.getStatement());
        writeString(" while (");
        write(object.getExpression());
        writeString(");");
    }
    
    @Override
    public void write(JEnhancedForStatement object) {
        writeString("for (");
        write(object.getVariableType());
        writeString(" ");
        write(object.getVariableName());
        writeString(" : ");
        write(object.getExpression());
        writeString(") ");
        write(object.getStatement());
    }
    
    @Override
    public void write(JLoopStatement object) {
    }
    
    @Override
    public void write(JWhileStatement object) {
        writeString("while (");
        write(object.getExpression());
        writeString(") ");
        write(object.getStatement());
    }
    
    @Override
    public void write(WhileStatement object) {
    }
    
    @Override
    public void write(JAssertStatement object) {
        writeString("assert ");
        write(object.getAssertion());
        if (object.getMessage().isPresent()) {
            writeString(" : ");
            write(object.getMessage().get());
        }
    }
    
    @Override
    public void write(JBlock object) {
        writeString("{");
        indent();
        newLine();
        for (JStatement jStatement : object.getStatements()) {
            write(jStatement);
            newLine();
        }
        dedent();
        newLine();
        writeString("}");
    }
    
    @Override
    public void write(JEmptyStatement object) {
        writeString(";");
    }
    
    @Override
    public void write(JExpressionStatement object) {
        write(object.getExpression());
        writeString(";");
    }
    
    @Override
    public void write(JLabledStatement object) {
        write(object.getIdentifier());
        writeString(": ");
        write(object.getStatement());
    }
    
    @Override
    public void write(JLocalVariableDeclaration object) {
        write((VariableHolder) object) writeString(";");
    }
    
    @Override
    public void write(JStatement object) {
    }
    
    @Override
    public void write(JArgumentList object) {
        write(object.getArguments());
    }
    
    @Override
    public void write(JConstructorDeclaration object) {
        write(object.getModifiers());
        write(object.getTypeParameters());
        write(object.getIdentifier());
        writeString("(");
        write(object.getParameterList());
        writeString(") ");
        write(object.getThrowsList());
        write(object.getBody());
    }
    
    @Override
    public void write(JMember object) {
    }
    
    @Override
    public void write(JMemberList object) {
        for (Object o : object.getMembers()) {
            JObject member = (JObject) o;
            write(member);
            newLine();
        }
    }
    
    @Override
    public void write(JMethod object) {
        write(object.getHeader());
        write(object.getBody());
    }
    
    @Override
    public void write(JMethodHeader object) {
        write((Typeable) object);
        write(object.getTypeParameters());
        write(object.getResultType());
        writeString(" ");
        write(object.getName());
        write(object.getParameterList());
        write(object.getThrowsList());
    }
    
    @Override
    public void write(JParameter object) {
        write(object.getAnnotations());
        write(object.getModifiers());
        write(object.getType());
        writeString(" ");
        write(object.getIdentifier());
    }
    
    @Override
    public void write(JTypeBody object) {
        writeString("{");
        indent();
        newLine();
        write(object.getMembers());
        dedent();
        newLine();
        writeString("}");
    }
    
    @Override
    public void write(JVariable object) {
        write(object.getIdentifier());
        if (object.getInitializer().isPresent()) {
            writeString(" = ");
            write(object.getInitializer().get());
        }
    }
    
    @Override
    public void write(JVariableInitializer object) {
    }
    
    @Override
    public void write(VariableHolder object) {
        write(object.getModifiers());
        write(object.getType());
        writeString(" ");
        write(object.getVariables());
    }
    
    @Override
    public void write(JArrayTypeName object) {
        write((JTypeName) object);
        for (int i = 0; i < object.getDimensions(); i++) {
            writeString("[]");
        }
    }
    
    @Override
    public void write(JCompilationUnit object) {
        if (object.getPackageName().isPresent()) {
            write(object.getPackageName().get());
            newLine();
        }
        
        for (JImport jImport : object.getImports()) {
            write(jImport);
            newLine();
        }
        
        for (JTypeDeclaration typeDeclaration : object.getTypes()) {
            write(typeDeclaration);
            newLine();
        }
    }
    
    @Override
    public void write(JIdentifier object) {
        writeString(object.getName());
    }
    
    @Override
    public void write(JImport object) {
        writeString("import ");
        if (object.isStatik()) {
            writeString("static ");
        }
        if (!object.getPackageName().isEmpty()) {
            writeString(object.getPackageName());
            writeString(".");
        }
        writeString(object.getName());
        writeString(";");
    }
    
    @Override
    public void write(JModifier object) {
        writeString(object.name().toLowerCase());
    }
    
    @Override
    public void write(JPackage object) {
        writeString("package ");
        writeString(object.getName());
        writeString(";");
    }
    
    @Override
    public void write(JParameterList object) {
        write(object.getParameters());
    }
    
    @Override
    public void write(JThrowsList object) {
        if (!object.getThrowsTypes().isEmpty()) {
            writeString("throws ");
            write(object.getThrowsTypes());
        }
    }
    
    @Override
    public void write(JTypeArgument.JReferenceTypeArgument object) {
        write(object.getReferenceType());
    }
    
    @Override
    public void write(JTypeArgument.JWildcardTypeArgument.Type object) {
        writeString(object.name().toLowerCase());
    }
    
    @Override
    public void write(JTypeArgument.JWildcardTypeArgument object) {
        writeString("?");
        if (object.getReferenceType() != null) {
            writeString(" ");
            write(object.getBoundType());
            writeString(" ");
            write(object.getReferenceType());
        }
    }
    
    @Override
    public void write(JTypeArgument object) {
    }
    
    @Override
    public void write(JTypeDeclaration object) {
    }
    
    @Override
    public void write(JTypeName object) {
        writeString(object.getName());
    }
    
    @Override
    public void write(JTypeParameter object) {
        write(object.getName());
        if (!object.getBounds().isEmpty()) {
            writeString(" extends ");
            for (Iterator<JTypeName> iterator =
                 object.getBounds().iterator(); iterator.hasNext(); ) {
                JTypeName bound = iterator.next();
                write(bound);
                if (iterator.hasNext()) {
                    writeString(" & ");
                }
            }
        }
    }
    
    @Override
    public void write(JTypeParameterList object) {
        if (!object.getTypeParameters().isEmpty()) {
            writeString("<");
            write(object.getTypeParameters());
            writeString(">");
        } else if (object.isShowTypeParametersEmpty()) {
            writeString("<>");
        }
    }
    
    @Override
    public void write(Typeable object) {
    }
    
    @Override
    public void write(JAnnotationList object) {
        write(object.getList());
    }
    
    @Override
    public void write(JAnnotatable object) {
    }
    
    @Override
    public void write(JAnnotation object) {
        writeString("@");
        write(object.getType());
        write(object.getValue());
    }
    
    @Override
    public void write(JAnnotationValue.JElementPair object) {
        write(object.getIdentifier());
        writeString(" = ");
        write(object.getValue());
    }
    
    @Override
    public void write(JAnnotationValue.JPairCollection object) {
        write(object.getValues());
    }
    
    @Override
    public void write(JAnnotationValue.JSingleValue object) {
        write(object.getValue());
    }
    
    @Override
    public void write(JAnnotationValue object) {
    }
    
    @Override
    public void write(JAnnotationBody object) {
    }
    
    @Override
    public void write(JAnnotationMember object) {
    }
    
    @Override
    public void write(JAnnotationTypeElement object) {
        write(object.getAnnotations());
        write(object.getModifiers());
        write(object.getType());
        writeString(" ");
        write(object.getIdentifier());
        writeString("() ");
        write(object.getDefaultValue());
    }
    
    @Override
    public void write(JElementValue object) {
    }
    
    @Override
    public void write(JClassBody object) {
    }
    
    @Override
    public void write(JClassInitializer object) {
        write(object.getBlock());
    }
    
    @Override
    public void write(JClassInstanceInitializer object) {
        write(object.getBlock());
    }
    
    @Override
    public void write(JClassMember object) {
    }
    
    @Override
    public void write(JClassStaticInitializer object) {
        writeString("static ");
        write(object.getBlock());
    }
    
    @Override
    public void write(JField object) {
    }
    
    @Override
    public void write(JEnumBody object) {
    }
    
    @Override
    public void write(JEnumConstant object) {
        write(object.getAnnotations());
        write(object.getName());
        writeString("(");
        write(object.getArgumentList());
        writeString(")");
        if (object.getBody().isPresent()) {
            writeString(" ");
            write(object.getBody().get());
        }
    }
    
    @Override
    public void write(JEnumMember object) {
    }
    
    @Override
    public void write(JInterfaceBody object) {
    }
    
    @Override
    public void write(JInterfaceMember object) {
    }
    
    @Override
    public void write(JInterfaceMethod object) {
        write(object.getHeader());
        if (object.getBody().isPresent()) {
            writeString(" default ");
            write(object.getBody().get());
        } else {
            writeString(";");
        }
    }
    
    @Override
    public void write(JAnnotationDeclaration object) {
        write((JTypeDeclaration) object);
        write(object.getAnnotations());
        writeString("@interface ");
        write(object.getIdentifier());
        writeString(" ");
        write(object.getBody());
    }
    
    @Override
    public void write(JClassDeclaration object) {
        write((JTypeDeclaration) object);
        writeString("class ");
        write(object.getIdentifier());
        write(object.getTypeParameters());
        if (object.getExtendsType().isPresent()) {
            writeString("extends ");
            write(object.getExtendsType().get());
        }
        // write(object.getSuperInterfaces());
        write(object.getBody());
    }
    
    @Override
    public void write(JEnumDeclaration object) {
        write((JTypeDeclaration) object);
        writeString("enum ");
        write(object.getIdentifier());
        // write(object.getSuperInterfaces());
        write(object.getBody());
    }
    
    @Override
    public void write(JInterfaceDeclaration object) {
        write((JTypeDeclaration) object);
        writeString("interface ");
        write(object.getIdentifier());
        writeString(" ");
        write(object.getBody());
    }
    
    @Override
    public void write(Modifiable object) {
    }
    
    @Override
    public void write(JModifierList object) {
        write(object.getList());
    }
}
