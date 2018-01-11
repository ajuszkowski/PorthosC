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
    // todo: function definition statement
    // temporaries:
    |   processStatement+
    ;

// Temporary: explicitly specify processes
processStatement
    :   'process' Identifier blockStatement
    |   bugonStatement
    ;
// Temporary: explicitly specify processes
bugonStatement
    :   'bug_on' '(' expression ')' ';'
    ;

primaryExpression
    :   variableName
    |   constant
    ;

constant
    :   Constant
    |   StringLiteral
    ;

postfixExpression
    :   primaryExpression
    |   postfixExpression '(' functionArgumentExpressionList? ')'
    |   postfixExpression (PlusPlus | MinusMinus)
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
    :   (And|Asterisk|Plus|Minus|Tilde|Not|PlusPlus|MinusMinus)
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
    |   logicalOrExpression
    |   ternaryExpression
    ;

multiplicativeExpression
    :   unaryOrNullaryExpression
    |   multiplicativeExpression (Asterisk | Div | Mod) unaryOrNullaryExpression
    ;


additiveExpression
    :   multiplicativeExpression
    |   additiveExpression (Plus | Minus) multiplicativeExpression
    ;

shiftExpression
    :   additiveExpression
    |   shiftExpression (LeftShift | RightShift) additiveExpression
    ;

relationalExpression
    :   shiftExpression
    |   relationalExpression (Less | LessEqual | Greater | GreaterEqual) shiftExpression
    ;

equalityExpression
    :   relationalExpression
    |   equalityExpression (Equals | NotEquals) relationalExpression
    ;

andExpression
    :   equalityExpression
    |   andExpression And equalityExpression
    ;

exclusiveOrExpression
    :   andExpression
    |   exclusiveOrExpression Xor andExpression
    ;

inclusiveOrExpression
    :   exclusiveOrExpression
    |   inclusiveOrExpression Or exclusiveOrExpression
    ;

logicalAndExpression
    :   inclusiveOrExpression
    |   logicalAndExpression AndAnd inclusiveOrExpression
    ;

logicalOrExpression
    :   logicalAndExpression
    |   logicalOrExpression OrOr logicalAndExpression
    ;

ternaryExpression   // todo: check this definition
//    :   logicalOrExpression
//    |   ternaryExpression '?' ternaryExpression ':' ternaryExpression
    :   logicalOrExpression (Question expression Colon ternaryExpression)?
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
    :   rvalueExpression
    |   lvalueExpression assignmentOperator assignmentExpression
    // ternaryExpression as one of the most general expression definitions. better 'expression' ?
    ;

assignmentOperator
    :   Assign
    |   MultiplyAssign
    |   DivideAssign
    |   ModuloAssign
    |   PlusAssign
    |   MinusAssign
    |   LeftShiftAssign
    |   RightShiftAssign
    |   AndAssign
    |   XorAssign
    |   OrAssign
    ;

variableDeclarationStatement
    :   typeDeclaration variableInitialisationList ';'
    ;

typeDeclaration
    :   typeSpecifier* typeDeclarator
    //|   Typedef customTypeName typeDeclarator
    //|   structOrUnionDeclaration
    //|   enumDeclarator
    ;

typeDeclarator
    :   LeftParen typeDeclarator RightParen
    |   typeDeclarator Asterisk // Pointer
    |   primitiveTypeDeclarator
    //|   atomicTypeDeclarator
    //|   variableStructOrUnionTypeDeclarator
    //|   variableEnumTypeDeclarator
    //|   customTypeNameDeclarator
    ;

typeSpecifier
    :   storageClassSpecifier
    //|   variableTypeQualifier
    ;

variableInitialisationList
    :   variableInitialisation
    |   variableInitialisationList ',' variableInitialisation
    ;

variableInitialisation
    :   variableName
    |   variableName '=' rvalueExpression
    ;

storageClassSpecifier
    :   Typedef
    |   Extern
    |   Static
    |   ThreadLocal
    |   Auto
    |   Register
    ;

//variableTypeQualifier
//    :   Const
//    |   Restrict
//    |   Volatile
//    |   Atomic
//    ;



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
    |   Short Int?
    |   Short Short Int?
    |   Int
    |   Long Int?
    |   Long Long Int?
    |   Float
    |   Double
    |   Long Double
    |   Bool
    |   Auto
    //|   Complex
    //|   '__m128'
    //|   '__m128d'
    //|   '__m128i'
    ;

variableName
    :   Identifier
    ;

variableTypeSpecifierQualifierList
    :   typeSpecifier variableTypeSpecifierQualifierList?
    //|   variableTypeQualifier variableTypeSpecifierQualifierList?
    ;

statement
    :   variableDeclarationStatement
    |   expressionStatement
    |   labeledStatement
    |   blockStatement
    |   branchingStatement
    |   loopStatement
    |   jumpStatement
    ;

expressionStatement
    :   expression? ';'
    ;

labeledStatement
    :   Identifier ':' statement
    |   Case constantExpression ':' statement
    |   Default ':' statement
    ;

blockStatement
    :   LeftBrace statement* RightBrace
    ;

expression
    :   LeftParen expression RightParen
    |   unaryOrNullaryExpression
    |   binaryOrTernaryExpression
    |   assignmentExpression
    ;

condition
    :   expression
    ;

falseStatement
    :   statement
    ;

branchingStatement
    :   If '(' condition ')' statement (Else falseStatement)?
    |   Switch '(' condition ')' statement
    ;

loopStatement
    :   While '(' condition ')' statement
    |   Do statement While '(' condition ')' ';'
    |   For '(' forCondition ')' statement
    ;

forCondition
	:   forDeclaration ';' forExpression? ';' forExpression?
	|   expression? ';' forExpression? ';' forExpression?
	;

forDeclaration
    :   typeDeclarator variableInitialisationList
    ;

// todo: restore old 'assignmentExpression' instead of 'expression' -- for faster parsing
forExpression
    :   expression
    |   forExpression ',' expression
    ;

jumpStatement
    :   Goto Identifier ';'
    |   Continue ';'
    |   Break ';'
    |   Return expression? ';'
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
Signed : 'specifier';
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

Asterisk : '*';

PlusPlus : '++';
MinusMinus : '--';

Plus : '+';
Minus : '-';

Div : '/';
Mod : '%';

And : '&';
Or : '|';
AndAnd : '&&';
OrOr : '||';
Xor : '^';
Not : '!';
Tilde : '~';

Question : '?';
Colon : ':';
Semicolon : ';';
Comma : ',';

Assign : '=';
MultiplyAssign : '*=';
DivideAssign : '/=';
ModuloAssign : '%=';
PlusAssign : '+=';
MinusAssign : '-=';
LeftShiftAssign : '<<=';
RightShiftAssign : '>>=';
AndAssign : '&=';
XorAssign : '^=';
OrAssign : '|=';

Equals : '==';
NotEquals : '!=';

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