<if(!innerClass)><if(packageName)>package <packageName>;

<endif><imports: {import | import <import>;
}><if(isClass)>import lombok.Data;

@Data
<endif><if(isEnum)>import lombok.AllArgsConstructor;

@AllArgsConstructor
<endif><endif>
<header> {
    <enumStrings ; separator=",\n"><if(enumStrings)>;<endif>
<if(stringFields)>    <stringFields : { field | private <field>;}; separator="\n">
<endif><if(methods)>   <methods ; separator="\n">
<endif>
<if(innerClassStrings)>    <innerClassStrings ; separator="\n\n"><endif>
}