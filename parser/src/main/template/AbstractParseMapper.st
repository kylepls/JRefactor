package in.kyle.jrefactor.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import in.kyle.jrefactor.tree.antlr.gen.*;

import in.kyle.jrefactor.tree.JObj;
import java.util.Map;
import java.util.function.Function;
import java.util.HashMap;

public abstract class AbstractParseMapper {

    public ParserRuleContext parsePart(Java8Parser parser, Class\<? extends JObj> clazz) {
        return MAPPING_FUNCTIONS.get(clazz).apply(parser);
    }
    
    private final Map\<Class\<?>, Function\<Java8Parser, ParserRuleContext>> MAPPING_FUNCTIONS = 
    new HashMap\<Class\<?>, Function\<Java8Parser, ParserRuleContext>>() {
        {
            <names: { each | put(<each.name>.class, parser->parse<each.simpleName>(parser));
            }>
        }
    };
    
    <names: { each | protected abstract ParserRuleContext parse<each.simpleName>(Java8Parser object);
    }>
}
