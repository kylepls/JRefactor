<if(!innerClass)><if(packageName)>package <packageName>;

<endif><imports: {import | import <import>; }; separator="\n">
<if(isClass)>import lombok.Data;<endif>
<if(isEnum)>import lombok.Getter;<endif>
<if(packageName || imports || isClass || isEnum)>

<endif>
<if(isClass)>@Data<endif>
<endif><if(isEnum)>@Getter<endif>
<header> {
    <enumStrings ; separator=",\n"><if(enumStrings)>;<endif>
    <constructors ; separator="\n\n">
    <stringFields : { field | private <field>;}; separator="\n"><if(stringFields)>
<endif>
    <methods ; separator="\n"><if(methods)>
<endif>
    <innerClassStrings ; separator="\n\n">
    <if(hasBuilder)>
    public static <if(packageName)><packageName>.<endif><builderName> builder() {
        return <builderName>.create();
    }
    <endif>
}