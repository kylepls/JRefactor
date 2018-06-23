grammar Ast;

ast: (ast_element | NL)* EOF;

ast_element
    : object 
    | enum_element
    | default_definition
    ;

default_definition
    : IDENTIFIER EQUALS STRING NL
    ;
    
enum_element
    : IDENTIFIER enum_block 
    ;
    
enum_block
    : OPEN_BLOCK NL* (enum_part NL*)+ CLOSE_BLOCK 
    ;
    
enum_part
    : IDENTIFIER (COMMA STRING)*
    ;
    
object
    : IDENTIFIER object_generic? object_implements? CONCRETE? object_block 
    ;

object_generic
    : object_generic_define
    | object_generic_super
    ;

object_generic_define
    : OPEN_DIAMOND UPPER_LETTER CLOSE_DIAMOND 
    ;

object_generic_super
    : OPEN_DIAMOND IDENTIFIER CLOSE_DIAMOND     
    ;

object_implements
    : IS IDENTIFIER (COMMA IDENTIFIER)*
    ;

object_block
    : OPEN_BLOCK object_elements CLOSE_BLOCK 
    ;

object_elements
    : (object_variable | NL)* (object | NL)* (inside_object | NL)*
    ;

object_variable
    : variable_type diamondType? IDENTIFIER
    ;

diamondType
    : OPEN_DIAMOND typeList CLOSE_DIAMOND
    ;

typeList
    : variable_type (COMMA variable_type)*
    ;

variable_type
    : IDENTIFIER
    | UPPER_LETTER
    ;

inside_object
    : INSIDE enum_element
    ;

IS          : 'is';
INSIDE      : 'inside' ;
CONCRETE    : 'concrete' ;
EQUALS      :  '=' ;
COMMA       :  ',' ;
OPEN_BLOCK  :  '{' ;
CLOSE_BLOCK :  '}' ;
UPPER_LETTER: [A-Z] ;
IDENTIFIER  :  IDENTIFIER_LETTER+ ;
OPEN_DIAMOND:  '<' ;
CLOSE_DIAMOND: '>' ;


WS          : [ \t\u000C]+ -> skip;
NL          : ('\n' | '\r' | ' ')+ ;

STRING      :  '"' .*? '"' ;
COMMENT_STRING: [^\n] ;

fragment IDENTIFIER_LETTER: [a-zA-Z_] ;
