lexer grammar FlaskPythonLexer;

tokens { INDENT, DEDENT }


@members {
    private java.util.LinkedList<Token> pending = new java.util.LinkedList<>();
    private java.util.Stack<Integer> indents = new java.util.Stack<>();
    private Token lastToken = null;

    @Override
    public Token nextToken() {
        if (!pending.isEmpty()) {
            return pending.poll();
        }

        Token next = super.nextToken();

        // EOF handling: unwind indentation stack
        if (next.getType() == EOF) {
            if (lastToken != null && lastToken.getType() != NEWLINE) {
                pending.add(makeToken(NEWLINE, "\n"));
            }
            while (!indents.isEmpty()) {
                indents.pop();
                pending.add(makeToken(DEDENT, ""));
            }
            pending.add(next);
            return pending.poll();
        }

        // NEWLINE handling → compute indentation
        if (next.getType() == NEWLINE) {
            String text = next.getText();
            int indent = 0;

            for (int i = text.length() - 1; i >= 0; i--) {
                char c = text.charAt(i);
                if (c == ' ') indent++;
                else if (c == '\t') indent += 8 - (indent % 8);
                else break;
            }

            int prev = indents.isEmpty() ? 0 : indents.peek();
            pending.add(next);

            if (indent > prev) {
                indents.push(indent);
                pending.add(makeToken(INDENT, ""));
            } else {
                while (!indents.isEmpty() && indents.peek() > indent) {
                    indents.pop();
                    pending.add(makeToken(DEDENT, ""));
                }
            }

            lastToken = next;
            return pending.poll();
        }

        if (next.getChannel() == DEFAULT_TOKEN_CHANNEL) {
            lastToken = next;
        }

        return next;
    }

    private Token makeToken(int type, String text) {
        CommonToken t = new CommonToken(type, text);
        t.setLine(getLine());
        t.setCharPositionInLine(getCharPositionInLine());
        return t;
    }
}

// KEYWORDS
FROM    : 'from';
IMPORT  : 'import';
DEF     : 'def';
RETURN  : 'return';
IF      : 'if';
FOR     : 'for';
IN      : 'in';
TRUE    : 'True';
FALSE   : 'False';
NONE    : 'None';

// OPERATORS
EQEQ    : '==';
ASSIGN  : '=';
PLUS    : '+';
GT      : '>';
LT      : '<';

// SYMBOLS
AT      : '@';
LPAREN  : '(';
RPAREN  : ')';
LBRACE  : '{';
RBRACE  : '}';
LBRACK  : '[';
RBRACK  : ']';
COLON   : ':';
COMMA   : ',';
DOT     : '.';

// LITERALS
NUMBER  : [0-9]+;

STRING
    : '"' (~["\r\n])* '"'
    | '\'' (~['\r\n])* '\''
    ;

// IDENTIFIERS
IDENT   : [a-zA-Z_][a-zA-Z0-9_]*;

// COMMENTS
COMMENT : '#' ~[\r\n]* -> skip;

// NEWLINE
NEWLINE
    : '\r'? '\n' [ \t]*
    ;

//  WS
WS
    : [ \t]+ -> skip
    ;
