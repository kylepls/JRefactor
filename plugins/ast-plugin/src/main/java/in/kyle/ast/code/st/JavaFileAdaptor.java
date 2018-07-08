package in.kyle.ast.code.st;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import in.kyle.ast.code.JavaFile;
import in.kyle.ast.code.JavaFileType;
import in.kyle.ast.code.file.JavaField;

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
        } else if ("superFields".equals(propertyName)) {
            return getSuperFields(file, getAllFields(file));
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
    
    private List<JavaField> getAllFields(JavaFile file) {
        List<JavaField> allFields = new ArrayList<>();
        if (file.hasSuperType()) {
            allFields.addAll(getAllFields(file.getSuperType()));
        }
        allFields.addAll(file.getFields());
        // generics transposition
        for (int i = 0; i < allFields.size(); i++) {
            JavaField typeParameter = allFields.get(i);
            if (typeParameter.getType().length() == 1 && file.hasGenericSuper()) {
                JavaField field = new JavaField(file.getGenericSuper(),
                                                typeParameter.getGeneric(),
                                                typeParameter.getName(),
                                                typeParameter.getValue());
                allFields.set(i, field);
            }
        }
        removeDefaultFields(allFields);
        return allFields;
    }
    
    private List<JavaField> getSuperFields(JavaFile file, List<JavaField> allFields) {
        List<JavaField> superFields = new ArrayList<>(allFields);
        superFields.removeAll(file.getFields());
        return superFields;
    }
    
    private List<JavaField> getStatementFields(JavaFile file) {
        List<JavaField> statementFields = new ArrayList<>(file.getFields());
        removeDefaultFields(statementFields);
        return statementFields;
    }
    
    private void removeDefaultFields(Collection<JavaField> fields) {
        fields.removeIf(JavaField::hasValue);
    }
}
