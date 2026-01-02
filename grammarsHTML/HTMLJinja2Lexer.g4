lexer grammar HTMLJinja2Lexer;

// =======================
// JINJA2 TOKENS
// =======================

JINJA_EXPR
    : '{{' .*? '}}'
    ;

JINJA_STMT
    : '{%' .*? '%}'
    ;

JINJA_COMMENT
    : '{#' .*? '#}'
    ;

HTML_COMMENT: '<!--' .*? '-->';

HTML_CONDITIONAL_COMMENT: '<![' .*? ']>';

XML: '<?xml' .*? '>';

CDATA: '<![CDATA[' .*? ']]>';

DTD
    : '<!' .*? '>'
    ;

SCRIPTLET: '<?' .*? '?>' | '<%' .*? '%>';

SEA_WS: (' ' | '\t' | '\r'? '\n')+;

SCRIPT_OPEN: '<script' .*? '>' -> pushMode(SCRIPT);

STYLE_OPEN: '<style' .*? '>' -> pushMode(STYLE);

TAG_OPEN: '<' -> pushMode(TAG);

HTML_TEXT: ~[<{]+;

// tag declarations

mode TAG;

TAG_CLOSE: '>' -> popMode;

TAG_SLASH_CLOSE: '/>' -> popMode;

TAG_SLASH: '/';

TAG_EQUALS: '=' -> pushMode(ATTVALUE);

TAG_NAME: TAG_NameStartChar TAG_NameChar*;

TAG_WHITESPACE: [ \t\r\n] -> channel(HIDDEN);

fragment HEXDIGIT: [a-fA-F0-9];

fragment DIGIT: [0-9];

fragment TAG_NameChar:
    TAG_NameStartChar
    | '-'
    | '_'
    | '.'
    | DIGIT
    | '\u00B7'
    | '\u0300' ..'\u036F'
    | '\u203F' ..'\u2040'
;

fragment TAG_NameStartChar:
    [:a-zA-Z]
    | '\u2070' ..'\u218F'
    | '\u2C00' ..'\u2FEF'
    | '\u3001' ..'\uD7FF'
    | '\uF900' ..'\uFDCF'
    | '\uFDF0' ..'\uFFFD'
;

// <scripts>

mode SCRIPT;

SCRIPT_BODY
    : (JINJA_EXPR | JINJA_STMT | .)*? '</script>' -> popMode
    ;

// تعريف SCRIPT_SHORT_BODY لتجنب التحذير
SCRIPT_SHORT_BODY
    : .*? '</script>'
    ;

// <styles>

mode STYLE;

STYLE_BODY
    : (JINJA_EXPR | JINJA_STMT | .)*? '</style>' -> popMode
    ;

// attribute values

mode ATTVALUE;

ATTVALUE_VALUE
    : ' '* (ATTRIBUTE | JINJA_EXPR) -> popMode
    ;

ATTRIBUTE: DOUBLE_QUOTE_STRING | SINGLE_QUOTE_STRING | ATTCHARS | HEXCHARS | DECCHARS;

fragment ATTCHARS: ATTCHAR+ ' '?;

fragment ATTCHAR: '-' | '_' | '.' | '/' | '+' | ',' | '?' | '=' | ':' | ';' | '#' | [0-9a-zA-Z];

fragment HEXCHARS: '#' [0-9a-fA-F]+;

fragment DECCHARS: [0-9]+ '%'?;

fragment DOUBLE_QUOTE_STRING: '"' ~[<"]* '"';

fragment SINGLE_QUOTE_STRING: '\'' ~[<']* '\'';
