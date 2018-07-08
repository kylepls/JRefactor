package in.kyle.ast.util.st;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import in.kyle.ast.code.file.JavaField;
import in.kyle.ast.code.file.JavaFile;
import in.kyle.ast.code.file.JavaFileType;

public class JavaFileAdaptor extends ObjectModelAdaptor {
    
    @Override
    public synchronized Object getProperty(Interpreter interp,
                                           ST self,
                                           Object o,
                                           Object property,
                                           String propertyName) throws STNoSuchPropertyException {
        JavaFile file = (JavaFile) o;
        if ("hasBuilder".equals(propertyName)) {
            return hadBuilder(file);
        } else if ("allFields".equals(propertyName)) {
            return getAllFields(file);
        } else if ("allFields_noDefaults".equals(propertyName)) {
            List<JavaField> allFields = getAllFields(file);
            removeDefaultFields(allFields);
            return allFields;
        } else if ("superFields".equals(propertyName)) {
            return getSuperFields(file);
        } else if ("statementFields".equals(propertyName)) {
            return getStatementFields(file);
        } else {
            return super.getProperty(interp, self, o, property, propertyName);
        }
    }
    
    private boolean hadBuilder(JavaFile file) {
        boolean isClass = file.typeIs(JavaFileType.CLASS);
        boolean hasFields = file.getFields().size() != 0;
        JavaFile temp = file;
        while ((temp = temp.getSuperType()) != null && !hasFields) {
            if (temp.getFields().size() != 0) {
                hasFields = true;
            }
        }
        boolean hasConcreteSuper =
                file.hasSuperType() && file.getSuperType().typeIs(JavaFileType.CLASS);
        return isClass && hasFields && !hasConcreteSuper;
    }
    
    private void transposeGenerics(JavaFile file, List<JavaField> fields) {
        for (int i = 0; i < fields.size(); i++) {
            JavaField fieldWithType = fields.get(i);
            if (isGenericType(fieldWithType.getType()) && file.hasGenericSuper()) {
                fieldWithType = new JavaField(file.getGenericSuper(),
                                              fieldWithType.getGeneric(),
                                              fieldWithType.getName(),
                                              fieldWithType.getValue());
                fields.set(i, fieldWithType);
            }
            if (fieldWithType.hasGeneric() && isGenericType(fieldWithType.getGeneric()) &&
                file.hasGenericSuper()) {
                fieldWithType = new JavaField(fieldWithType.getType(),
                                              file.getGenericSuper(),
                                              fieldWithType.getName(),
                                              fieldWithType.getValue());
                fields.set(i, fieldWithType);
            }
        }
        
    }
    
    private List<JavaField> getAllFields(JavaFile file) {
        List<JavaField> allFields = new ArrayList<>();
        if (file.hasSuperType()) {
            allFields.addAll(getAllFields(file.getSuperType()));
        }
        allFields.addAll(file.getFields());
        transposeGenerics(file, allFields);
        return allFields;
    }
    
    private boolean isGenericType(String string) {
        return string.length() == 1;
    }
    
    private List<JavaField> getSuperFields(JavaFile file) {
        if (file.hasSuperType()) {
            List<JavaField> superFields = getAllFields(file.getSuperType());
            transposeGenerics(file, superFields);
            removeDefaultFields(superFields);
            return superFields;
        } else {
            return Collections.emptyList();
        }
    }
    
    private List<JavaField> getStatementFields(JavaFile file) {
        List<JavaField> statementFields = new ArrayList<>(file.getFields());
        removeDefaultFields(statementFields);
        transposeGenerics(file, statementFields);
        return statementFields;
    }
    
    private void removeDefaultFields(Collection<JavaField> fields) {
        fields.removeIf(JavaField::hasValue);
    }
}
