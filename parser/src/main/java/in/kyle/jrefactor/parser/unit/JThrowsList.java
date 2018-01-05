package in.kyle.jrefactor.parser.unit;

import java.util.Iterator;

import in.kyle.jrefactor.CodeWriter;
import in.kyle.jrefactor.parser.JObject;
import in.kyle.jrefactor.parser.JObjectList;
import lombok.Data;

@Data
public class JThrowsList implements JObject {
    
    private JObjectList<JTypeName> throwsTypes = new JObjectList<>();
    
    public boolean addThrowsType(JTypeName typeName) {
        return throwsTypes.add(typeName);
    }
    
    public boolean removeThrowsType(JTypeName typeName) {
        return throwsTypes.remove(typeName);
    }
    
    @Override
    public void write(CodeWriter writer) {
        if (!throwsTypes.isEmpty()) {
            writer.append("throws ");
            for (Iterator<JTypeName> iterator = throwsTypes.iterator(); iterator.hasNext(); ) {
                JTypeName throwsType = iterator.next();
                writer.append(throwsType);
                if (iterator.hasNext()) {
                    writer.append(", ");
                }
            }
        }
    }
    
}
