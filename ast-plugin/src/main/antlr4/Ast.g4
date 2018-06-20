grammar Ast;

ast: (ast_element | NL)* EOF;

ast_element
    : object 
    | enum_element
    ;

enum_element
    : IDENTIFIER enum_block 
    ;
    
enum_block
    : OPEN_BLOCK NL (enum_part NL)+ CLOSE_BLOCK 
    ;
    
enum_part
    : IDENTIFIER (COMMA STRING)?
    ;
    
object
    : IDENTIFIER object_block
    ;

object_block
    : OPEN_BLOCK NL (object_element NL)* CLOSE_BLOCK 
    ;

object_element
    : object 
    | inside_object
    | object_variable
    ;

object_variable
    : IDENTIFIER diamondType? IDENTIFIER
    ;

diamondType
    : OPEN_DIAMOND typeList CLOSE_DIAMOND
    ;

typeList
    : IDENTIFIER (COMMA IDENTIFIER)*
    ;

inside_object
    : INSIDE enum_element
    ;


INSIDE      : 'inside' ;
COMMA       :  ',' ;
OPEN_BLOCK  :  '{' ;
CLOSE_BLOCK :  '}' ;
IDENTIFIER  :  [a-zA-Z_]+ ;
OPEN_DIAMOND:  '<' ;
CLOSE_DIAMOND: '>' ;

WS          : [ \t\u000C]+ -> skip;
NL          : ('\n' | '\r' | ' ')+ ;

STRING      :  '"' .*? '"' ;
