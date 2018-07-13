package in.kyle.jrefactor.refactor.util.obj;

import java.util.List;

import in.kyle.jrefactor.tree.obj.JPropertyLookup;
import in.kyle.jrefactor.tree.obj.JTypeName;

public class JPropertyLookupUtils {
    
    public static JTypeName toTypeName(JPropertyLookup pl) {
        List<String> areas = pl.getAreas();
        if (areas.size() > 1) {
            return JTypeName.builder()
                    .area(JPropertyLookup.builder()
                                  .addAreas(areas.subList(0, areas.size() - 2))
                                  .build())
                    .type(areas.get(areas.size() - 1))
                    .build();
        } else {
            return new JTypeName(areas.get(0));
        }
    }
    
    public static JPropertyLookup fromStrings(String... strings) {
        return JPropertyLookup.builder().addAreas(strings).build();
    }
}
