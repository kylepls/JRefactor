package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObjectList;
import lombok.Data;

@Data
public class JTypeParameterList extends JObjectList<JTypeParameter> {
    
    private boolean showTypeParametersEmpty = false;
    
}
