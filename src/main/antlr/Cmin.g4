/*
 [The "BSD licence"]
 Copyright (c) 2013 Sam Harwell
 All rights reserved.
 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.
 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

/** C 2011 grammar built from the C11 Spec */
/** source: https://github.com/antlr/grammars-v4/ */

/** Grammar for the minimised subset of C language */
grammar Cmin;

// Entry point
main
    :   statement+
    ;

primaryExpression
    :   Identifier
    |   Constant
    |   StringLiteral+
    ;

postfixExpression
    :   primaryExpression
    |   postfixExpression '(' functionArgumentExpressionList? ')'
    |   postfixExpression '++'
    |   postfixExpression '--'
    ;

functionArgumentExpressionList
    :   functionArgumentExpression
    |   functionArgumentExpressionList ',' functionArgumentExpression
    ;

functionArgumentExpression
    :   unaryOrNullaryExpression
    ;

unaryOrNullaryExpression
    :   postfixExpression
    |   unaryOperator unaryOrNullaryExpression
    ;

unaryOperator
    :   '&' | '*' | '+' | '-' | '~' | '!' | '--' | '++'
    ;

binaryOrTernaryExpression
    :   multiplicativeExpression
    |   additiveExpression
    |   shiftExpression
    |   relationalExpression
    |   equalityExpression
    |   andExpression
    |   exclusiveOrExpression
    |   inclusiveOrExpression
    |   logicalAndExpression
    |   logicalOrAndExpression
    |   ternaryExpression
    ;

multiplicativeExpression
    :   unaryOrNullaryExpression
    |   multiplicativeExpression '*' unaryOrNullaryExpression
    |   multiplicativeExpression '/' unaryOrNullaryExpression
    |   multiplicativeExpression '%' unaryOrNullaryExpression
    ;

additiveExpression
    :   multiplicativeExpression
    |   additiveExpression '+' multiplicativeExpression
    |   additiveExpression '-' multiplicativeExpression
    ;

shiftExpression
    :   additiveExpression
    |   shiftExpression '<<' additiveExpression
    |   shiftExpression '>>' additiveExpression
    ;

relationalExpression
    :   shiftExpression
    |   relationalExpression '<' shiftExpression
    |   relationalExpression '>' shiftExpression
    |   relationalExpression '<=' shiftExpression
    |   relationalExpression '>=' shiftExpression
    ;

equalityExpression
    :   relationalExpression
    |   equalityExpression '==' relationalExpression
    |   equalityExpression '!=' relationalExpression
    ;

andExpression
    :   equalityExpression
    |   andExpression '&' equalityExpression
    ;

exclusiveOrExpression
    :   andExpression
    |   exclusiveOrExpression '^' andExpression
    ;

inclusiveOrExpression
    :   exclusiveOrExpression
    |   inclusiveOrExpression '|' exclusiveOrExpression
    ;

logicalAndExpression
    :   inclusiveOrExpression
    |   logicalAndExpression '&&' inclusiveOrExpression
    ;

logicalOrAndExpression
    :   logicalAndExpression
    |   logicalOrAndExpression '||' logicalAndExpression
    ;

ternaryExpression   // todo: check this definition
    :   logicalOrAndExpression
    |   ternaryExpression '?' ternaryExpression ':' ternaryExpression
    ;

constantExpression   // todo: check this definition
    :   ternaryExpression  // as one of the most general expression definitions. better 'expression' ?
    ;

lvalueExpression
    :   unaryOrNullaryExpression
    ;

rvalueExpression
    :   ternaryExpression
    ;

// todo: check syntax 'a = b = c = 3;'
assignmentExpression
    :   lvalueExpression assignmentOperator rvalueExpression //todo: better 'expression' instead of constantExpression ?
    // ternaryExpression as one of the most general expression definitions. better 'expression' ?
    ;

assignmentOperator
    :   '=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '&=' | '^=' | '|='
    ;

assignmentExpressionList
    :   assignmentExpression
    |   assignmentExpressionList ',' assignmentExpression
    ;

variableDeclarationStatement
    :   typeSpecifier* typeDeclarator variableDeclaratorList ';'
    ;

typeSpecifier
    :   storageClassSpecifier
    |   variableTypeQualifier
    ;

variableDeclaratorList
    :   variableDeclarator
    |   variableDeclaratorList ',' variableDeclarator
    ;

variableDeclarator
    :   variableName
    |   variableName '=' variableInitializer
    ;

storageClassSpecifier
    :   Typedef
    |   Extern
    |   Static
    |   ThreadLocal
    |   Auto
    |   Register
    ;

variableTypeQualifier
    :   Const
    |   Restrict
    |   Volatile
    |   Atomic
    ;

typeDeclarator
    :   '(' typeDeclarator ')'
    |   pointerTypeDeclarator
    |   primitiveTypeDeclarator
    |   atomicTypeDeclarator
    ;

pointerTypeDeclarator
    :   primitiveTypeDeclarator '*'
    ;

primitiveTypeDeclarator
    :   primitiveTypeSpecifier? primitiveTypeKeyword
    ;

primitiveTypeSpecifier
    :   Signed
    |   Unsigned
    ;

primitiveTypeKeyword
    :   Void
    |   Char
    |   Short
    |   Int
    |   Long
    |   Long Long
    |   Float
    |   Double
    |   Bool
    //|   Complex
    //|   '__m128'
    //|   '__m128d'
    //|   '__m128i'
    ;

atomicTypeDeclarator  //note: Atomic may be also declared in typeSpecifier
    :   primitiveTypeKeyword Atomic   // for `int * _Atomic x;`
    ;

variableName
    :   Identifier
    ;

variableTypeSpecifierQualifierList
    :   typeSpecifier         variableTypeSpecifierQualifierList?
    |   variableTypeQualifier variableTypeSpecifierQualifierList?
    ;

variableInitializer
    :   rvalueExpression
    ;

statement
    :   variableDeclarationStatement
    |   expression ';'
    |   labeledStatement
    |   compoundStatement
    |   expressionStatement
    |   branchingStatement
    |   loopStatement
    |   jumpStatement
    ;

labeledStatement
    :   Identifier ':' statement
    |   Case constantExpression ':' statement
    |   Default ':' statement
    ;

compoundStatement
    :   '{' blockItemList? '}'
    ;

blockItemList
    :   blockItem
    |   blockItemList blockItem
    ;

blockItem
    :   variableDeclarationStatement
    |   statement
    ;

expressionStatement
    :   expression? ';'
    ;

expression
    :   '(' expression ')'
    |   unaryOrNullaryExpression
    |   binaryOrTernaryExpression
    |   assignmentExpression
    ;

branchingStatement
    :   If '(' expression ')' statement (Else statement)?
    |   Switch '(' assignmentExpressionList ')' statement
    ;

loopStatement
    :   While '(' assignmentExpressionList ')' statement
    |   Do statement While '(' assignmentExpressionList ')' ';'
    |   For '(' forCondition ')' statement
    ;

forCondition
	:   forDeclaration ';' forExpression? ';' forExpression?
	|   assignmentExpressionList? ';' forExpression? ';' forExpression?
	;

forDeclaration
    :   typeDeclarator variableDeclaratorList
    ;

forExpression
    :   assignmentExpression
    |   forExpression ',' assignmentExpression
    ;

jumpStatement
    :   Goto Identifier ';'
    |   Continue ';'
    |   Break ';'
    |   Return assignmentExpressionList? ';'
    ;


/*Preprocessing directives: from https://github.com/antlr/grammars-v4/blob/master/cpp/CPP14.g4 */
MultiLineMacro
    :   '#' (~[\n]*? '\\' '\r'? '\n')+ ~[\n]+
        -> skip  //-> channel(HIDDEN)
    ;

Directive
    :   '#' ~[\n]*
        -> skip  //-> channel(HIDDEN)
    ;

// -- LEXER --

Auto : 'auto';
Break : 'break';
Case : 'case';
Char : 'char';
Const : 'const';
Continue : 'continue';
Default : 'default';
Do : 'do';
Double : 'double';
Else : 'else';
Enum : 'enum';
Extern : 'extern';
Float : 'float';
For : 'for';
Goto : 'goto';
If : 'if';
Inline : 'inline';
Int : 'int';
Long : 'long';
Register : 'register';
Restrict : 'restrict';
Return : 'return';
Short : 'short';
Signed : 'signed';
Sizeof : 'sizeof';
Static : 'static';
Struct : 'struct';
Switch : 'switch';
Typedef : 'typedef';
Union : 'union';
Unsigned : 'unsigned';
Void : 'void';
Volatile : 'volatile';
While : 'while';

Alignas : '_Alignas';
Alignof : '_Alignof';
Atomic : '_Atomic';
Bool : '_Bool';
Complex : '_Complex';
Generic : '_Generic';
Imaginary : '_Imaginary';
Noreturn : '_Noreturn';
StaticAssert : '_Static_assert';
ThreadLocal : '_Thread_local';

LeftParen : '(';
RightParen : ')';
LeftBracket : '[';
RightBracket : ']';
LeftBrace : '{';
RightBrace : '}';

Less : '<';
LessEqual : '<=';
Greater : '>';
GreaterEqual : '>=';
LeftShift : '<<';
RightShift : '>>';

Plus : '+';
PlusPlus : '++';
Minus : '-';
MinusMinus : '--';
Asterisk : '*';
Div : '/';
Mod : '%';

And : '&';
Or : '|';
AndAnd : '&&';
OrOr : '||';
Caret : '^';
Not : '!';
Tilde : '~';

Question : '?';
Colon : ':';
Semi : ';';
Comma : ',';

Assign : '=';
AsteriskAssign : '*=';
DivAssign : '/=';
ModAssign : '%=';
PlusAssign : '+=';
MinusAssign : '-=';
LeftShiftAssign : '<<=';
RightShiftAssign : '>>=';
AndAssign : '&=';
XorAssign : '^=';
OrAssign : '|=';

Equal : '==';
NotEqual : '!=';

Arrow : '->';
Dot : '.';
Ellipsis : '...';

Identifier
    :   IdentifierNondigit
        (   IdentifierNondigit
        |   Digit
        )*
    ;

fragment
IdentifierNondigit
    :   Nondigit
    |   UniversalCharacterName
    //|   // other implementation-defined characters...
    ;

fragment
Nondigit
    :   [a-zA-Z_]
    ;

fragment
Digit
    :   [0-9]
    ;

fragment
UniversalCharacterName
    :   '\\u' HexQuad
    |   '\\U' HexQuad HexQuad
    ;

fragment
HexQuad
    :   HexadecimalDigit HexadecimalDigit HexadecimalDigit HexadecimalDigit
    ;

Constant
    :   IntegerConstant
    |   FloatingConstant
    //|   EnumerationConstant
    |   CharacterConstant
    ;

fragment
IntegerConstant
    :   DecimalConstant IntegerSuffix?
    |   OctalConstant IntegerSuffix?
    |   HexadecimalConstant IntegerSuffix?
    |	BinaryConstant
    ;

fragment
BinaryConstant
	:	'0' [bB] [0-1]+
	;

fragment
DecimalConstant
    :   NonzeroDigit Digit*
    ;

fragment
OctalConstant
    :   '0' OctalDigit*
    ;

fragment
HexadecimalConstant
    :   HexadecimalPrefix HexadecimalDigit+
    ;

fragment
HexadecimalPrefix
    :   '0' [xX]
    ;

fragment
NonzeroDigit
    :   [1-9]
    ;

fragment
OctalDigit
    :   [0-7]
    ;

fragment
HexadecimalDigit
    :   [0-9a-fA-F]
    ;

fragment
IntegerSuffix
    :   UnsignedSuffix LongSuffix?
    |   UnsignedSuffix LongLongSuffix
    |   LongSuffix UnsignedSuffix?
    |   LongLongSuffix UnsignedSuffix?
    ;

fragment
UnsignedSuffix
    :   [uU]
    ;

fragment
LongSuffix
    :   [lL]
    ;

fragment
LongLongSuffix
    :   'll' | 'LL'
    ;

fragment
FloatingConstant
    :   DecimalFloatingConstant
    |   HexadecimalFloatingConstant
    ;

fragment
DecimalFloatingConstant
    :   FractionalConstant ExponentPart? FloatingSuffix?
    |   DigitSequence ExponentPart FloatingSuffix?
    ;

fragment
HexadecimalFloatingConstant
    :   HexadecimalPrefix HexadecimalFractionalConstant BinaryExponentPart FloatingSuffix?
    |   HexadecimalPrefix HexadecimalDigitSequence BinaryExponentPart FloatingSuffix?
    ;

fragment
FractionalConstant
    :   DigitSequence? '.' DigitSequence
    |   DigitSequence '.'
    ;

fragment
ExponentPart
    :   'e' Sign? DigitSequence
    |   'E' Sign? DigitSequence
    ;

fragment
Sign
    :   '+' | '-'
    ;

DigitSequence
    :   Digit+
    ;

fragment
HexadecimalFractionalConstant
    :   HexadecimalDigitSequence? '.' HexadecimalDigitSequence
    |   HexadecimalDigitSequence '.'
    ;

fragment
BinaryExponentPart
    :   'p' Sign? DigitSequence
    |   'P' Sign? DigitSequence
    ;

fragment
HexadecimalDigitSequence
    :   HexadecimalDigit+
    ;

fragment
FloatingSuffix
    :   'f' | 'l' | 'F' | 'L'
    ;

fragment
CharacterConstant
    :   '\'' CCharSequence '\''
    |   'L\'' CCharSequence '\''
    |   'u\'' CCharSequence '\''
    |   'U\'' CCharSequence '\''
    ;

fragment
CCharSequence
    :   CChar+
    ;

fragment
CChar
    :   ~['\\\r\n]
    |   EscapeSequence
    ;
fragment
EscapeSequence
    :   SimpleEscapeSequence
    |   OctalEscapeSequence
    |   HexadecimalEscapeSequence
    |   UniversalCharacterName
    ;
fragment
SimpleEscapeSequence
    :   '\\' ['"?abfnrtv\\]
    ;
fragment
OctalEscapeSequence
    :   '\\' OctalDigit
    |   '\\' OctalDigit OctalDigit
    |   '\\' OctalDigit OctalDigit OctalDigit
    ;
fragment
HexadecimalEscapeSequence
    :   '\\x' HexadecimalDigit+
    ;
StringLiteral
    :   EncodingPrefix? '"' SCharSequence? '"'
    ;
fragment
EncodingPrefix
    :   'u8'
    |   'u'
    |   'U'
    |   'L'
    ;
fragment
SCharSequence
    :   SChar+
    ;
fragment
SChar
    :   ~["\\\r\n]
    |   EscapeSequence
    |   '\\\n'   // Added line
    |   '\\\r\n' // Added line
    ;

ComplexDefine
    :   '#' Whitespace? 'define'  ~[#]*
        -> skip
    ;

// ignore the assembler blocks:
AsmBlock
    :   'asm' ~'{'* '{' ~'}'* '}'
	-> skip
    ;

// ignore the lines generated by c preprocessor
LineAfterPreprocessing
    :   '#line' Whitespace* ~[\r\n]*
        -> skip
    ;

LineDirective
    :   '#' Whitespace? DecimalConstant Whitespace? StringLiteral ~[\r\n]*
        -> skip
    ;

PragmaDirective
    :   '#' Whitespace? 'pragma' Whitespace ~[\r\n]*
        -> skip
    ;

Whitespace
    :   [ \t]+
        -> skip
    ;

Newline
    :   (   '\r' '\n'?
        |   '\n'
        )
        -> skip
    ;

BlockComment
    :   '/*' .*? '*/'
        -> skip
    ;

LineComment
    :   '//' ~[\r\n]*
        -> skip
    ;