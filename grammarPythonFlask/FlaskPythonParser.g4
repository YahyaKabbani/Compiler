parser grammar FlaskPythonParser;

options {
    tokenVocab = FlaskPythonLexer;
}

// =================== ENTRY ===================
program
    : statement* EOF
    ;

// =================== STATEMENTS ===================
statement
    : importStmt
    | fromImportStmt
    | assignment
    | functionDef
    | decorator
    | ifStmt
    | forStmt
    | returnStmt
    | expr
    ;

// =================== IMPORTS ===================
importStmt
    : IMPORT IDENT (COMMA IDENT)*
    ;

fromImportStmt
    : FROM IDENT IMPORT IDENT (COMMA IDENT)*
    ;

// =================== ASSIGNMENT ===================
assignment
    : IDENT ASSIGN expr
    ;

// =================== FUNCTIONS ===================
functionDef
    : DEF IDENT LPAREN paramList? RPAREN COLON suite
    ;

paramList
    : IDENT (COMMA IDENT)*
    ;

// =================== DECORATORS ===================
decorator
    : AT expr
    ;

// =================== CONTROL ===================
ifStmt
    : IF expr COLON suite
    ;

forStmt
    : FOR IDENT IN expr COLON suite
    ;

returnStmt
    : RETURN expr
    ;

// =================== BLOCK ===================
suite
    : NEWLINE statement+
    | statement
    ;

// =================== EXPRESSIONS ===================
expr
    : atom
    | expr DOT IDENT
    | expr LPAREN argList? RPAREN
    | expr LBRACK expr RBRACK
    | expr PLUS expr
    | expr GT expr
    | expr LT expr
    | expr EQEQ expr
    ;

argList
    : argument (COMMA argument)*
    ;

// =================== ATOMS ===================
atom
    : IDENT
    | STRING
    | NUMBER
    | TRUE
    | FALSE
    | NONE
    | dictLiteral
    | listLiteral
    | LPAREN expr RPAREN
    ;

// =================== COLLECTIONS ===================
dictLiteral
    : LBRACE (STRING COLON expr (COMMA STRING COLON expr)*)? RBRACE
    ;

listLiteral
    : LBRACK (expr (COMMA expr)*)? RBRACK
    ;


argument
    : expr
    | IDENT ASSIGN expr
    ;
