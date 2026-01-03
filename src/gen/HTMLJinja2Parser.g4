parser grammar HTMLJinja2Parser;

options {
    tokenVocab = HTMLJinja2Lexer;
}

/*
 * ENTRY POINT
 */
htmlDocument
    : (htmlMisc | htmlElements | jinja)+
    ;

/*
 * HTML STRUCTURE
 */
htmlElements
    : htmlMisc* htmlElement htmlMisc*
    ;

htmlElement
    : TAG_OPEN TAG_NAME htmlAttribute*
        (
            TAG_CLOSE
                (htmlContent TAG_OPEN TAG_SLASH TAG_NAME TAG_CLOSE)?
          | TAG_SLASH_CLOSE
        )
    | script
    | style
    | jinja
    ;

/*
 * CONTENT INSIDE TAGS
 */
htmlContent
    : htmlChardata?
      (
        (htmlElement | CDATA | htmlComment | jinja)
        htmlChardata?
      )*
    ;

/*
 * ATTRIBUTES
 */
htmlAttribute
    : TAG_NAME (TAG_EQUALS ATTVALUE_VALUE)?
    ;

/*
 * TEXT / WHITESPACE
 */
htmlChardata
    : HTML_TEXT
    | SEA_WS
    ;

htmlMisc
    : htmlComment
    | DTD
    | SEA_WS
    ;

/*
 * COMMENTS
 */
htmlComment
    : HTML_COMMENT
    | HTML_CONDITIONAL_COMMENT
    ;

/*
 * SCRIPT / STYLE
 */
script
    : SCRIPT_OPEN (SCRIPT_BODY | SCRIPT_SHORT_BODY)
    ;

style
    : STYLE_OPEN (STYLE_BODY | STYLE_SHORT_BODY)
    ;

/*
 * JINJA
 */
jinja
    : JINJA_EXPR
    | JINJA_STMT
    | JINJA_COMMENT
    ;
