package <package>;

<imports: {import | import <import>;}; separator="\n">

public class <simpleName>Builder {
    
    private final <name> <simpleName; format="lower">;
    
    private <simpleName>Builder() {
        <simpleName; format="lower"> = new <name>();
    }
    
    public static <simpleName>Builder create() {
        return new <simpleName>Builder();
    }
    
    <fields : { field |
<if(field.normalType)>
public <simpleName>Builder <field.name>(<field.type> <field.name; format="lower">) {
    <simpleName; format="lower">.set<field.name; format="cap">(<field.name; format="lower">);
    return this;
\}
<endif><if(field.optional)>
public <simpleName>Builder <field.name>(<field.generic> <field.name; format="lower">) {
    <simpleName; format="lower">.set<field.name; format="cap">(java.util.Optional.ofNullable(<field.name; format="lower">));
    return this;
\}
<endif><if(field.collection)>
public <simpleName>Builder add<field.name; format="cap">(<field.generic>... <field.nameSingular>) {
    for (<field.generic> e : <field.nameSingular>) {
        <simpleName; format="lower">.add<field.nameSingular; format="cap">(e);
    \}
    return this;
\}
<endif>
    }; separator="\n">
    
    public <name> build() {
        return <simpleName; format="lower">;
    }
}