package in.kyle.jrefactor.tree.unit;

import in.kyle.jrefactor.tree.JObjectList;
import lombok.Data;

@Data
public class JTypeParameterList extends JObjectList<JTypeParameter> {
    
    private boolean showTypeParametersEmpty = false;
    
}
