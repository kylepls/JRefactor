package in.kyle.jrefactor.parser.unit;

import in.kyle.jrefactor.parser.JObjectList;
import lombok.Data;

@Data
public class JTypeArgumentList extends JObjectList<JTypeArgument> {
    
    private boolean showTypeParametersEmpty = false;
    
}
