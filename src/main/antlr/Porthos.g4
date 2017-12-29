grammar Porthos;


arith_expr
    :   arith_atom ARITH_OP arith_atom
	|   arith_atom
	;

arith_atom
    :   DIGIT
	|   register
	|   LPAR arith_expr RPAR
	;

arith_comp
    :   LPAR arith_expr COMP_OP arith_expr RPAR
    ;

bool_expr
    :   bool_atom
	|   bool_atom BOOL_OP bool_atom
	;

bool_atom
    : ('True' | 'true')
	| ('False' | 'false')
	| arith_comp
	| LPAR bool_expr RPAR
	;

location
    :   WORD
    ;

register
    :   WORD
    ;

local
    :   register '<-' arith_expr
    ;

load
    :   register '<:-' location
    ;

store
    :   location ':=' register
    ;

read
    :   register '=' location POINT 'load' LPAR ATOMIC RPAR
    ;

write
    :   location POINT 'store' LPAR ATOMIC COMMA register RPAR
    ;

fence
    :   mfence
	|   sync
	|   lwsync
	|   isync
    ;

mfence : 'mfence';
sync : 'sync';
lwsync : 'lwsync';
isync : 'isync';

inst
    :   atom
	|   seq
	|   while_
	|   if1
	|   if2
	;

atom
    :   local
	|   load
	|   store
	|   fence
	|   read
	|   write
	;

seq
    :   atom   ';' inst
	|   while_ ';' inst
	|   if1    ';' inst
	|   if2    ';' inst
	;

if1
    : 'if' bool_expr ('then')* LCBRA inst RCBRA 'else' LCBRA inst RCBRA
    ;

if2
    :   'if' bool_expr ('then')* LCBRA inst RCBRA
    ;

while_
    :   'while' bool_expr LCBRA inst RCBRA
    ;

program
    :   LCBRA location (COMMA location)* RCBRA
        ('thread t' DIGIT LCBRA inst RCBRA
            (   'exists' location '=' DIGIT ','
                | DIGIT ':' register '=' DIGIT ','
            )*
        )*
	;

///////////////////////////////////////////////////////////////////////////////////////

COMP_OP : EQ | NEQ | LEQ | LT | GEQ | GT;
ARITH_OP : ADD | SUB | MULT | DIV | MOD;
BOOL_OP : AND | OR; 

DIGIT : [0-9];
WORD : (LETTER | DIGIT)+;
LETTER : 'a'..'z' | 'A'..'Z';

WS : [ \t\r\n]+ -> skip;
LPAR : '(';
RPAR : ')';
LCBRA : '{';
RCBRA : '}';
COMMA : ',';
POINT : '.';
EQ : '==';
NEQ : '!=';
LEQ : '<=';
LT : '<';
GEQ : '>=';
GT : '>';
ADD : '+';
SUB : '-';
MULT : '*';
DIV : '/';
MOD : '%';
AND : 'and';
OR : 'or';

ATOMIC : '_na' | '_sc' | '_rx' | '_acq' | '_rel' | '_con';