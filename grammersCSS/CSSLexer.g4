lexer grammar CSSLexer;
channels {
    ERROR
}

OpenBracket  : '[';
CloseBracket : ']';
OpenParen    : '(';
CloseParen   : ')';
OpenBrace    : '{';
CloseBrace   : '}';
SemiColon    : ';';
Equal        : '=';
Colon        : ':';
Dot          : '.';
Multiply     : '*';
Divide       : '/';
Pipe         : '|';
Underscore   : '_';

fragment At: '@';

fragment Hex: [0-9a-fA-F];

fragment NewlineOrSpace
    : '\r\n'
    | [ \t\r\n\f]
    ;

fragment Unicode: '\\' Hex Hex? Hex? Hex? Hex? Hex? NewlineOrSpace;

fragment Escape: Unicode | '\\' ~[\r\n\f0-9a-fA-F];

//fragment Nmstart: [_a-zA-Z] | Nonascii | Escape;
//
//fragment Nmchar: [_a-zA-Z0-9\-] | Nonascii | Escape;

Comment: '/*' ~'*'* '*'+ ( ~[/*] ~'*'* '*'+)* '/';


// ================= BASIC TOKENS =================

Space
    : [ \t\r\n\f]+
    ;

Ident
    : '-'? [_a-zA-Z] [_a-zA-Z0-9-]*
    ;

Number
    : [0-9]+ ('.' [0-9]+)?
    ;

Percentage
    : Number '%'
    ;

Dimension
    : Number Ident
    ;

String_
    : '"' (~["\\\r\n])* '"'
    | '\'' (~['\\\r\n])* '\''
    ;

Hash
    : '#' [_a-zA-Z0-9-]+
    ;

Comma
    : ','
    ;

Plus
    : '+'
    ;

Minus
    : '-'
    ;

Greater
    : '>'
    ;

Tilde
    : '~'
    ;

Important
    : '!important'
    ;
