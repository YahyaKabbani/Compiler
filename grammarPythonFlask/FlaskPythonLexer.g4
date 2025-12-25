lexer grammar FlaskPythonLexer;

// =================== KEYWORDS ===================
FROM        : 'from';
IMPORT      : 'import';
DEF         : 'def';
RETURN      : 'return';
IF          : 'if';
FOR         : 'for';
IN          : 'in';
TRUE        : 'True';
FALSE       : 'False';
NONE        : 'None';


EQEQ : '==';



// =================== SYMBOLS ===================
AT          : '@';
LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
LBRACK      : '[';
RBRACK      : ']';
COLON       : ':';
COMMA       : ',';
DOT         : '.';
ASSIGN      : '=';
PLUS        : '+';
GT          : '>';
LT          : '<';

// =================== LITERALS ===================
NUMBER
    : [0-9]+
    ;

STRING
    : '"' (~["\r\n])* '"'
    | '\'' (~['\r\n])* '\''
    ;

// =================== IDENTIFIERS ===================
IDENT
    : [a-zA-Z_][a-zA-Z0-9_]*
    ;

// =================== COMMENTS & WS ===================
COMMENT
    : '#' ~[\r\n]* -> skip
    ;

NEWLINE
    : '\r'? '\n' -> skip
    ;


WS
    : [ \t]+ -> skip
    ;