// Generated from grammarPythonFlask/FlaskPythonLexer.g4 by ANTLR 4.13.2
package gen;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class FlaskPythonLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INDENT=1, DEDENT=2, FROM=3, IMPORT=4, DEF=5, RETURN=6, IF=7, FOR=8, IN=9, 
		TRUE=10, FALSE=11, NONE=12, EQEQ=13, ASSIGN=14, PLUS=15, GT=16, LT=17, 
		AT=18, LPAREN=19, RPAREN=20, LBRACE=21, RBRACE=22, LBRACK=23, RBRACK=24, 
		COLON=25, COMMA=26, DOT=27, NUMBER=28, STRING=29, IDENT=30, COMMENT=31, 
		NEWLINE=32, WS=33;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"FROM", "IMPORT", "DEF", "RETURN", "IF", "FOR", "IN", "TRUE", "FALSE", 
			"NONE", "EQEQ", "ASSIGN", "PLUS", "GT", "LT", "AT", "LPAREN", "RPAREN", 
			"LBRACE", "RBRACE", "LBRACK", "RBRACK", "COLON", "COMMA", "DOT", "NUMBER", 
			"STRING", "IDENT", "COMMENT", "NEWLINE", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'from'", "'import'", "'def'", "'return'", "'if'", 
			"'for'", "'in'", "'True'", "'False'", "'None'", "'=='", "'='", "'+'", 
			"'>'", "'<'", "'@'", "'('", "')'", "'{'", "'}'", "'['", "']'", "':'", 
			"','", "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "INDENT", "DEDENT", "FROM", "IMPORT", "DEF", "RETURN", "IF", "FOR", 
			"IN", "TRUE", "FALSE", "NONE", "EQEQ", "ASSIGN", "PLUS", "GT", "LT", 
			"AT", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "COLON", 
			"COMMA", "DOT", "NUMBER", "STRING", "IDENT", "COMMENT", "NEWLINE", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


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


	public FlaskPythonLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FlaskPythonLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000!\u00cb\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001"+
		"\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001"+
		"\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001"+
		"\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019\u0004\u0019\u0091"+
		"\b\u0019\u000b\u0019\f\u0019\u0092\u0001\u001a\u0001\u001a\u0005\u001a"+
		"\u0097\b\u001a\n\u001a\f\u001a\u009a\t\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0005\u001a\u009f\b\u001a\n\u001a\f\u001a\u00a2\t\u001a\u0001\u001a"+
		"\u0003\u001a\u00a5\b\u001a\u0001\u001b\u0001\u001b\u0005\u001b\u00a9\b"+
		"\u001b\n\u001b\f\u001b\u00ac\t\u001b\u0001\u001c\u0001\u001c\u0005\u001c"+
		"\u00b0\b\u001c\n\u001c\f\u001c\u00b3\t\u001c\u0001\u001c\u0001\u001c\u0001"+
		"\u001d\u0003\u001d\u00b8\b\u001d\u0001\u001d\u0001\u001d\u0005\u001d\u00bc"+
		"\b\u001d\n\u001d\f\u001d\u00bf\t\u001d\u0004\u001d\u00c1\b\u001d\u000b"+
		"\u001d\f\u001d\u00c2\u0001\u001e\u0004\u001e\u00c6\b\u001e\u000b\u001e"+
		"\f\u001e\u00c7\u0001\u001e\u0001\u001e\u0000\u0000\u001f\u0001\u0003\u0003"+
		"\u0004\u0005\u0005\u0007\u0006\t\u0007\u000b\b\r\t\u000f\n\u0011\u000b"+
		"\u0013\f\u0015\r\u0017\u000e\u0019\u000f\u001b\u0010\u001d\u0011\u001f"+
		"\u0012!\u0013#\u0014%\u0015\'\u0016)\u0017+\u0018-\u0019/\u001a1\u001b"+
		"3\u001c5\u001d7\u001e9\u001f; =!\u0001\u0000\u0007\u0001\u000009\u0003"+
		"\u0000\n\n\r\r\"\"\u0003\u0000\n\n\r\r\'\'\u0003\u0000AZ__az\u0004\u0000"+
		"09AZ__az\u0002\u0000\n\n\r\r\u0002\u0000\t\t  \u00d4\u0000\u0001\u0001"+
		"\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001"+
		"\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000"+
		"\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000"+
		"\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000"+
		"\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000"+
		"\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000"+
		"\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000"+
		"\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000"+
		"\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'"+
		"\u0001\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000"+
		"\u0000\u0000\u0000-\u0001\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000"+
		"\u00001\u0001\u0000\u0000\u0000\u00003\u0001\u0000\u0000\u0000\u00005"+
		"\u0001\u0000\u0000\u0000\u00007\u0001\u0000\u0000\u0000\u00009\u0001\u0000"+
		"\u0000\u0000\u0000;\u0001\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000"+
		"\u0001?\u0001\u0000\u0000\u0000\u0003D\u0001\u0000\u0000\u0000\u0005K"+
		"\u0001\u0000\u0000\u0000\u0007O\u0001\u0000\u0000\u0000\tV\u0001\u0000"+
		"\u0000\u0000\u000bY\u0001\u0000\u0000\u0000\r]\u0001\u0000\u0000\u0000"+
		"\u000f`\u0001\u0000\u0000\u0000\u0011e\u0001\u0000\u0000\u0000\u0013k"+
		"\u0001\u0000\u0000\u0000\u0015p\u0001\u0000\u0000\u0000\u0017s\u0001\u0000"+
		"\u0000\u0000\u0019u\u0001\u0000\u0000\u0000\u001bw\u0001\u0000\u0000\u0000"+
		"\u001dy\u0001\u0000\u0000\u0000\u001f{\u0001\u0000\u0000\u0000!}\u0001"+
		"\u0000\u0000\u0000#\u007f\u0001\u0000\u0000\u0000%\u0081\u0001\u0000\u0000"+
		"\u0000\'\u0083\u0001\u0000\u0000\u0000)\u0085\u0001\u0000\u0000\u0000"+
		"+\u0087\u0001\u0000\u0000\u0000-\u0089\u0001\u0000\u0000\u0000/\u008b"+
		"\u0001\u0000\u0000\u00001\u008d\u0001\u0000\u0000\u00003\u0090\u0001\u0000"+
		"\u0000\u00005\u00a4\u0001\u0000\u0000\u00007\u00a6\u0001\u0000\u0000\u0000"+
		"9\u00ad\u0001\u0000\u0000\u0000;\u00c0\u0001\u0000\u0000\u0000=\u00c5"+
		"\u0001\u0000\u0000\u0000?@\u0005f\u0000\u0000@A\u0005r\u0000\u0000AB\u0005"+
		"o\u0000\u0000BC\u0005m\u0000\u0000C\u0002\u0001\u0000\u0000\u0000DE\u0005"+
		"i\u0000\u0000EF\u0005m\u0000\u0000FG\u0005p\u0000\u0000GH\u0005o\u0000"+
		"\u0000HI\u0005r\u0000\u0000IJ\u0005t\u0000\u0000J\u0004\u0001\u0000\u0000"+
		"\u0000KL\u0005d\u0000\u0000LM\u0005e\u0000\u0000MN\u0005f\u0000\u0000"+
		"N\u0006\u0001\u0000\u0000\u0000OP\u0005r\u0000\u0000PQ\u0005e\u0000\u0000"+
		"QR\u0005t\u0000\u0000RS\u0005u\u0000\u0000ST\u0005r\u0000\u0000TU\u0005"+
		"n\u0000\u0000U\b\u0001\u0000\u0000\u0000VW\u0005i\u0000\u0000WX\u0005"+
		"f\u0000\u0000X\n\u0001\u0000\u0000\u0000YZ\u0005f\u0000\u0000Z[\u0005"+
		"o\u0000\u0000[\\\u0005r\u0000\u0000\\\f\u0001\u0000\u0000\u0000]^\u0005"+
		"i\u0000\u0000^_\u0005n\u0000\u0000_\u000e\u0001\u0000\u0000\u0000`a\u0005"+
		"T\u0000\u0000ab\u0005r\u0000\u0000bc\u0005u\u0000\u0000cd\u0005e\u0000"+
		"\u0000d\u0010\u0001\u0000\u0000\u0000ef\u0005F\u0000\u0000fg\u0005a\u0000"+
		"\u0000gh\u0005l\u0000\u0000hi\u0005s\u0000\u0000ij\u0005e\u0000\u0000"+
		"j\u0012\u0001\u0000\u0000\u0000kl\u0005N\u0000\u0000lm\u0005o\u0000\u0000"+
		"mn\u0005n\u0000\u0000no\u0005e\u0000\u0000o\u0014\u0001\u0000\u0000\u0000"+
		"pq\u0005=\u0000\u0000qr\u0005=\u0000\u0000r\u0016\u0001\u0000\u0000\u0000"+
		"st\u0005=\u0000\u0000t\u0018\u0001\u0000\u0000\u0000uv\u0005+\u0000\u0000"+
		"v\u001a\u0001\u0000\u0000\u0000wx\u0005>\u0000\u0000x\u001c\u0001\u0000"+
		"\u0000\u0000yz\u0005<\u0000\u0000z\u001e\u0001\u0000\u0000\u0000{|\u0005"+
		"@\u0000\u0000| \u0001\u0000\u0000\u0000}~\u0005(\u0000\u0000~\"\u0001"+
		"\u0000\u0000\u0000\u007f\u0080\u0005)\u0000\u0000\u0080$\u0001\u0000\u0000"+
		"\u0000\u0081\u0082\u0005{\u0000\u0000\u0082&\u0001\u0000\u0000\u0000\u0083"+
		"\u0084\u0005}\u0000\u0000\u0084(\u0001\u0000\u0000\u0000\u0085\u0086\u0005"+
		"[\u0000\u0000\u0086*\u0001\u0000\u0000\u0000\u0087\u0088\u0005]\u0000"+
		"\u0000\u0088,\u0001\u0000\u0000\u0000\u0089\u008a\u0005:\u0000\u0000\u008a"+
		".\u0001\u0000\u0000\u0000\u008b\u008c\u0005,\u0000\u0000\u008c0\u0001"+
		"\u0000\u0000\u0000\u008d\u008e\u0005.\u0000\u0000\u008e2\u0001\u0000\u0000"+
		"\u0000\u008f\u0091\u0007\u0000\u0000\u0000\u0090\u008f\u0001\u0000\u0000"+
		"\u0000\u0091\u0092\u0001\u0000\u0000\u0000\u0092\u0090\u0001\u0000\u0000"+
		"\u0000\u0092\u0093\u0001\u0000\u0000\u0000\u00934\u0001\u0000\u0000\u0000"+
		"\u0094\u0098\u0005\"\u0000\u0000\u0095\u0097\b\u0001\u0000\u0000\u0096"+
		"\u0095\u0001\u0000\u0000\u0000\u0097\u009a\u0001\u0000\u0000\u0000\u0098"+
		"\u0096\u0001\u0000\u0000\u0000\u0098\u0099\u0001\u0000\u0000\u0000\u0099"+
		"\u009b\u0001\u0000\u0000\u0000\u009a\u0098\u0001\u0000\u0000\u0000\u009b"+
		"\u00a5\u0005\"\u0000\u0000\u009c\u00a0\u0005\'\u0000\u0000\u009d\u009f"+
		"\b\u0002\u0000\u0000\u009e\u009d\u0001\u0000\u0000\u0000\u009f\u00a2\u0001"+
		"\u0000\u0000\u0000\u00a0\u009e\u0001\u0000\u0000\u0000\u00a0\u00a1\u0001"+
		"\u0000\u0000\u0000\u00a1\u00a3\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001"+
		"\u0000\u0000\u0000\u00a3\u00a5\u0005\'\u0000\u0000\u00a4\u0094\u0001\u0000"+
		"\u0000\u0000\u00a4\u009c\u0001\u0000\u0000\u0000\u00a56\u0001\u0000\u0000"+
		"\u0000\u00a6\u00aa\u0007\u0003\u0000\u0000\u00a7\u00a9\u0007\u0004\u0000"+
		"\u0000\u00a8\u00a7\u0001\u0000\u0000\u0000\u00a9\u00ac\u0001\u0000\u0000"+
		"\u0000\u00aa\u00a8\u0001\u0000\u0000\u0000\u00aa\u00ab\u0001\u0000\u0000"+
		"\u0000\u00ab8\u0001\u0000\u0000\u0000\u00ac\u00aa\u0001\u0000\u0000\u0000"+
		"\u00ad\u00b1\u0005#\u0000\u0000\u00ae\u00b0\b\u0005\u0000\u0000\u00af"+
		"\u00ae\u0001\u0000\u0000\u0000\u00b0\u00b3\u0001\u0000\u0000\u0000\u00b1"+
		"\u00af\u0001\u0000\u0000\u0000\u00b1\u00b2\u0001\u0000\u0000\u0000\u00b2"+
		"\u00b4\u0001\u0000\u0000\u0000\u00b3\u00b1\u0001\u0000\u0000\u0000\u00b4"+
		"\u00b5\u0006\u001c\u0000\u0000\u00b5:\u0001\u0000\u0000\u0000\u00b6\u00b8"+
		"\u0005\r\u0000\u0000\u00b7\u00b6\u0001\u0000\u0000\u0000\u00b7\u00b8\u0001"+
		"\u0000\u0000\u0000\u00b8\u00b9\u0001\u0000\u0000\u0000\u00b9\u00bd\u0005"+
		"\n\u0000\u0000\u00ba\u00bc\u0007\u0006\u0000\u0000\u00bb\u00ba\u0001\u0000"+
		"\u0000\u0000\u00bc\u00bf\u0001\u0000\u0000\u0000\u00bd\u00bb\u0001\u0000"+
		"\u0000\u0000\u00bd\u00be\u0001\u0000\u0000\u0000\u00be\u00c1\u0001\u0000"+
		"\u0000\u0000\u00bf\u00bd\u0001\u0000\u0000\u0000\u00c0\u00b7\u0001\u0000"+
		"\u0000\u0000\u00c1\u00c2\u0001\u0000\u0000\u0000\u00c2\u00c0\u0001\u0000"+
		"\u0000\u0000\u00c2\u00c3\u0001\u0000\u0000\u0000\u00c3<\u0001\u0000\u0000"+
		"\u0000\u00c4\u00c6\u0007\u0006\u0000\u0000\u00c5\u00c4\u0001\u0000\u0000"+
		"\u0000\u00c6\u00c7\u0001\u0000\u0000\u0000\u00c7\u00c5\u0001\u0000\u0000"+
		"\u0000\u00c7\u00c8\u0001\u0000\u0000\u0000\u00c8\u00c9\u0001\u0000\u0000"+
		"\u0000\u00c9\u00ca\u0006\u001e\u0000\u0000\u00ca>\u0001\u0000\u0000\u0000"+
		"\u000b\u0000\u0092\u0098\u00a0\u00a4\u00aa\u00b1\u00b7\u00bd\u00c2\u00c7"+
		"\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}