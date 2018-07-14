grammar Ast;

ast: (ast_element | NL)* EOF;

ast_element
    : object 
    | enum_element
    | default_definition
    | variable_definition
    ;

variable_definition
    : '#' IDENTIFIER '=' STRING 
    ;

variable_placeholder
    : '#' IDENTIFIER
    ;
    
default_definition
    : IDENTIFIER '=' STRING NL
    ;
    
enum_element
    : IDENTIFIER object_implements? enum_block 
    ;
    
enum_block
    : '{' NL* enum_header NL* (enum_part NL*)+ (variable_placeholder | NL)* '}' 
    ;

enum_header
    : '<' (IDENTIFIER (',' IDENTIFIER)*)? '>'
    ;
    
enum_part
    : IDENTIFIER (',' STRING)*
    ;
    
object
    : CONCRETE? IDENTIFIER object_generic? object_implements? object_block 
    ;

object_generic
    : '<' (IDENTIFIER) '>'
    ;
    

object_implements
    : 'is' IDENTIFIER (',' IDENTIFIER)*
    ;

object_block
    : '{' object_elements '}' 
    ;

object_elements
    : (object_variable | variable_placeholder | NL)* (object | NL)* (enum_element | NL)* (inside_object | NL)*
    ;

object_variable
    : variable_type diamondType? IDENTIFIER
    ;

diamondType
    : '<' typeList '>'
    ;

typeList
    : variable_type (',' variable_type)*
    ;

variable_type
    : IDENTIFIER
    ;

inside_object
    : INSIDE enum_element
    ;

CONCRETE    : 'concrete' ;
TAG         : '#' ;
IS          : 'is' ;
INSIDE      : 'inside' ;
EQUALS      :  '=' ;
COMMA       :  ',' ;
OPEN_BLOCK  :  '{' ;
CLOSE_BLOCK :  '}' ;
IDENTIFIER  :  IDENTIFIER_LETTER+ ;
OPEN_DIAMOND:  '<' ;
CLOSE_DIAMOND: '>' ;


WS          : [ \t]+ -> skip;
NL          : ('\n' | '\r' | ' ')+ ;

STRING      :  '"' .*? '"' ;

fragment IDENTIFIER_LETTER: [a-zA-Z_] ;
