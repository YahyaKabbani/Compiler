parser grammar FlaskPythonParser;

options {
    tokenVocab = FlaskPythonLexer;
}

// =================== ENTRY ====================
program
    : (NEWLINE | INDENT | DEDENT | statement)* EOF
    ;


// =================== STATEMENTS ===================
// Consume trailing NEWLINEs after every statement
statement
    : simpleStmt NEWLINE*
    | compoundStmt NEWLINE*
    ;

// =================== SIMPLE STATEMENTS ===================
simpleStmt
    : importStmt
    | fromImportStmt
    | assignment
    | returnStmt
    | expr
    ;

// =================== COMPOUND STATEMENTS ===================
// Decorators must bind to the following function
compoundStmt
    : decoratedDef
    | functionDef
    | ifStmt
    | forStmt
    ;

// =================== DECORATORS ===================
decoratedDef
    : decorator+ functionDef
    ;

decorator
    : AT expr NEWLINE+
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

// =================== CONTROL FLOW ===================
ifStmt
    : IF expr COLON suite
    ;

forStmt
    : FOR IDENT IN expr COLON suite
    ;

returnStmt
    : RETURN expr
    ;

// =================== BLOCK (REAL PYTHON) ===================
suite
    : NEWLINE INDENT statement+ DEDENT
    ;

// =================== EXPRESSIONS ===================
// Left-recursive is intentional and works with ANTLR4
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

// =================== ARGUMENTS ===================
argList
    : argument (COMMA argument)*
    ;

argument
    : IDENT ASSIGN expr
    | expr
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
// Handles multiline Python dicts with indentation
dictLiteral
    : LBRACE
      (NEWLINE INDENT)?                 // "{\n    "
      NEWLINE*
      (dictEntry (COMMA NEWLINE* dictEntry)* COMMA?)?
      NEWLINE*
      (DEDENT)?
      RBRACE
    ;

dictEntry
    : expr COLON expr
    ;

// List literals (single-line is enough for Flask sample)
listLiteral
    : LBRACK (expr (COMMA expr)*)? RBRACK
    ;
