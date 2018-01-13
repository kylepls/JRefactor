package in.kyle.jrefactor.tree.unit;

import in.kyle.jrefactor.tree.JObjectList;
import lombok.Data;

@Data
public class JTypeArgumentList extends JObjectList<JTypeArgument> {
    
    private boolean showTypeParametersEmpty = false;
    
}
