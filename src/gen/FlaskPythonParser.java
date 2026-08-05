// Generated from FlaskPythonParser.g4 by ANTLR 4.13.2
package gen;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class FlaskPythonParser extends Parser {
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
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_simpleStmt = 2, RULE_compoundStmt = 3, 
		RULE_decoratedDef = 4, RULE_decorator = 5, RULE_importStmt = 6, RULE_fromImportStmt = 7, 
		RULE_assignment = 8, RULE_functionDef = 9, RULE_paramList = 10, RULE_ifStmt = 11, 
		RULE_forStmt = 12, RULE_returnStmt = 13, RULE_suite = 14, RULE_expr = 15, 
		RULE_argList = 16, RULE_argument = 17, RULE_atom = 18, RULE_dictLiteral = 19, 
		RULE_dictEntry = 20, RULE_listLiteral = 21, RULE_layout = 22;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "statement", "simpleStmt", "compoundStmt", "decoratedDef", 
			"decorator", "importStmt", "fromImportStmt", "assignment", "functionDef", 
			"paramList", "ifStmt", "forStmt", "returnStmt", "suite", "expr", "argList", 
			"argument", "atom", "dictLiteral", "dictEntry", "listLiteral", "layout"
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

	@Override
	public String getGrammarFileName() { return "FlaskPythonParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FlaskPythonParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(FlaskPythonParser.EOF, 0); }
		public List<TerminalNode> NEWLINE() { return getTokens(FlaskPythonParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(FlaskPythonParser.NEWLINE, i);
		}
		public List<TerminalNode> INDENT() { return getTokens(FlaskPythonParser.INDENT); }
		public TerminalNode INDENT(int i) {
			return getToken(FlaskPythonParser.INDENT, i);
		}
		public List<TerminalNode> DEDENT() { return getTokens(FlaskPythonParser.DEDENT); }
		public TerminalNode DEDENT(int i) {
			return getToken(FlaskPythonParser.DEDENT, i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 6185295358L) != 0)) {
				{
				setState(50);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NEWLINE:
					{
					setState(46);
					match(NEWLINE);
					}
					break;
				case INDENT:
					{
					setState(47);
					match(INDENT);
					}
					break;
				case DEDENT:
					{
					setState(48);
					match(DEDENT);
					}
					break;
				case FROM:
				case IMPORT:
				case DEF:
				case RETURN:
				case IF:
				case FOR:
				case TRUE:
				case FALSE:
				case NONE:
				case AT:
				case LPAREN:
				case LBRACE:
				case LBRACK:
				case NUMBER:
				case STRING:
				case IDENT:
					{
					setState(49);
					statement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(54);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(55);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public SimpleStmtContext simpleStmt() {
			return getRuleContext(SimpleStmtContext.class,0);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(FlaskPythonParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(FlaskPythonParser.NEWLINE, i);
		}
		public CompoundStmtContext compoundStmt() {
			return getRuleContext(CompoundStmtContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			int _alt;
			setState(71);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FROM:
			case IMPORT:
			case RETURN:
			case TRUE:
			case FALSE:
			case NONE:
			case LPAREN:
			case LBRACE:
			case LBRACK:
			case NUMBER:
			case STRING:
			case IDENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(57);
				simpleStmt();
				setState(61);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(58);
						match(NEWLINE);
						}
						} 
					}
					setState(63);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
				}
				}
				break;
			case DEF:
			case IF:
			case FOR:
			case AT:
				enterOuterAlt(_localctx, 2);
				{
				setState(64);
				compoundStmt();
				setState(68);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(65);
						match(NEWLINE);
						}
						} 
					}
					setState(70);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SimpleStmtContext extends ParserRuleContext {
		public ImportStmtContext importStmt() {
			return getRuleContext(ImportStmtContext.class,0);
		}
		public FromImportStmtContext fromImportStmt() {
			return getRuleContext(FromImportStmtContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public ReturnStmtContext returnStmt() {
			return getRuleContext(ReturnStmtContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public SimpleStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterSimpleStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitSimpleStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitSimpleStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleStmtContext simpleStmt() throws RecognitionException {
		SimpleStmtContext _localctx = new SimpleStmtContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_simpleStmt);
		try {
			setState(78);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(73);
				importStmt();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(74);
				fromImportStmt();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(75);
				assignment();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(76);
				returnStmt();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(77);
				expr(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CompoundStmtContext extends ParserRuleContext {
		public DecoratedDefContext decoratedDef() {
			return getRuleContext(DecoratedDefContext.class,0);
		}
		public FunctionDefContext functionDef() {
			return getRuleContext(FunctionDefContext.class,0);
		}
		public IfStmtContext ifStmt() {
			return getRuleContext(IfStmtContext.class,0);
		}
		public ForStmtContext forStmt() {
			return getRuleContext(ForStmtContext.class,0);
		}
		public CompoundStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compoundStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterCompoundStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitCompoundStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitCompoundStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompoundStmtContext compoundStmt() throws RecognitionException {
		CompoundStmtContext _localctx = new CompoundStmtContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_compoundStmt);
		try {
			setState(84);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AT:
				enterOuterAlt(_localctx, 1);
				{
				setState(80);
				decoratedDef();
				}
				break;
			case DEF:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				functionDef();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 3);
				{
				setState(82);
				ifStmt();
				}
				break;
			case FOR:
				enterOuterAlt(_localctx, 4);
				{
				setState(83);
				forStmt();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DecoratedDefContext extends ParserRuleContext {
		public FunctionDefContext functionDef() {
			return getRuleContext(FunctionDefContext.class,0);
		}
		public List<DecoratorContext> decorator() {
			return getRuleContexts(DecoratorContext.class);
		}
		public DecoratorContext decorator(int i) {
			return getRuleContext(DecoratorContext.class,i);
		}
		public DecoratedDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decoratedDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterDecoratedDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitDecoratedDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitDecoratedDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecoratedDefContext decoratedDef() throws RecognitionException {
		DecoratedDefContext _localctx = new DecoratedDefContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_decoratedDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(86);
				decorator();
				}
				}
				setState(89); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==AT );
			setState(91);
			functionDef();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DecoratorContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(FlaskPythonParser.AT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(FlaskPythonParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(FlaskPythonParser.NEWLINE, i);
		}
		public DecoratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decorator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterDecorator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitDecorator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitDecorator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecoratorContext decorator() throws RecognitionException {
		DecoratorContext _localctx = new DecoratorContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_decorator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(AT);
			setState(94);
			expr(0);
			setState(96); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(95);
				match(NEWLINE);
				}
				}
				setState(98); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==NEWLINE );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ImportStmtContext extends ParserRuleContext {
		public TerminalNode IMPORT() { return getToken(FlaskPythonParser.IMPORT, 0); }
		public List<TerminalNode> IDENT() { return getTokens(FlaskPythonParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(FlaskPythonParser.IDENT, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(FlaskPythonParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(FlaskPythonParser.COMMA, i);
		}
		public ImportStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterImportStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitImportStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitImportStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportStmtContext importStmt() throws RecognitionException {
		ImportStmtContext _localctx = new ImportStmtContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_importStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			match(IMPORT);
			setState(101);
			match(IDENT);
			setState(106);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(102);
				match(COMMA);
				setState(103);
				match(IDENT);
				}
				}
				setState(108);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FromImportStmtContext extends ParserRuleContext {
		public TerminalNode FROM() { return getToken(FlaskPythonParser.FROM, 0); }
		public List<TerminalNode> IDENT() { return getTokens(FlaskPythonParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(FlaskPythonParser.IDENT, i);
		}
		public TerminalNode IMPORT() { return getToken(FlaskPythonParser.IMPORT, 0); }
		public List<TerminalNode> COMMA() { return getTokens(FlaskPythonParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(FlaskPythonParser.COMMA, i);
		}
		public FromImportStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fromImportStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterFromImportStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitFromImportStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitFromImportStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FromImportStmtContext fromImportStmt() throws RecognitionException {
		FromImportStmtContext _localctx = new FromImportStmtContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_fromImportStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			match(FROM);
			setState(110);
			match(IDENT);
			setState(111);
			match(IMPORT);
			setState(112);
			match(IDENT);
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(113);
				match(COMMA);
				setState(114);
				match(IDENT);
				}
				}
				setState(119);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode IDENT() { return getToken(FlaskPythonParser.IDENT, 0); }
		public TerminalNode ASSIGN() { return getToken(FlaskPythonParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			match(IDENT);
			setState(121);
			match(ASSIGN);
			setState(122);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionDefContext extends ParserRuleContext {
		public TerminalNode DEF() { return getToken(FlaskPythonParser.DEF, 0); }
		public TerminalNode IDENT() { return getToken(FlaskPythonParser.IDENT, 0); }
		public TerminalNode LPAREN() { return getToken(FlaskPythonParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(FlaskPythonParser.RPAREN, 0); }
		public TerminalNode COLON() { return getToken(FlaskPythonParser.COLON, 0); }
		public SuiteContext suite() {
			return getRuleContext(SuiteContext.class,0);
		}
		public ParamListContext paramList() {
			return getRuleContext(ParamListContext.class,0);
		}
		public FunctionDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterFunctionDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitFunctionDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitFunctionDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDefContext functionDef() throws RecognitionException {
		FunctionDefContext _localctx = new FunctionDefContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_functionDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			match(DEF);
			setState(125);
			match(IDENT);
			setState(126);
			match(LPAREN);
			setState(128);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENT) {
				{
				setState(127);
				paramList();
				}
			}

			setState(130);
			match(RPAREN);
			setState(131);
			match(COLON);
			setState(132);
			suite();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamListContext extends ParserRuleContext {
		public List<TerminalNode> IDENT() { return getTokens(FlaskPythonParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(FlaskPythonParser.IDENT, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(FlaskPythonParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(FlaskPythonParser.COMMA, i);
		}
		public ParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterParamList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitParamList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitParamList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamListContext paramList() throws RecognitionException {
		ParamListContext _localctx = new ParamListContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_paramList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			match(IDENT);
			setState(139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(135);
				match(COMMA);
				setState(136);
				match(IDENT);
				}
				}
				setState(141);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfStmtContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(FlaskPythonParser.IF, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode COLON() { return getToken(FlaskPythonParser.COLON, 0); }
		public SuiteContext suite() {
			return getRuleContext(SuiteContext.class,0);
		}
		public IfStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterIfStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitIfStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStmtContext ifStmt() throws RecognitionException {
		IfStmtContext _localctx = new IfStmtContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_ifStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142);
			match(IF);
			setState(143);
			expr(0);
			setState(144);
			match(COLON);
			setState(145);
			suite();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForStmtContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(FlaskPythonParser.FOR, 0); }
		public TerminalNode IDENT() { return getToken(FlaskPythonParser.IDENT, 0); }
		public TerminalNode IN() { return getToken(FlaskPythonParser.IN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode COLON() { return getToken(FlaskPythonParser.COLON, 0); }
		public SuiteContext suite() {
			return getRuleContext(SuiteContext.class,0);
		}
		public ForStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterForStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitForStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitForStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStmtContext forStmt() throws RecognitionException {
		ForStmtContext _localctx = new ForStmtContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_forStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			match(FOR);
			setState(148);
			match(IDENT);
			setState(149);
			match(IN);
			setState(150);
			expr(0);
			setState(151);
			match(COLON);
			setState(152);
			suite();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnStmtContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(FlaskPythonParser.RETURN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ReturnStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterReturnStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitReturnStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitReturnStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStmtContext returnStmt() throws RecognitionException {
		ReturnStmtContext _localctx = new ReturnStmtContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_returnStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			match(RETURN);
			setState(155);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SuiteContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(FlaskPythonParser.NEWLINE, 0); }
		public TerminalNode INDENT() { return getToken(FlaskPythonParser.INDENT, 0); }
		public TerminalNode DEDENT() { return getToken(FlaskPythonParser.DEDENT, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public SuiteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_suite; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterSuite(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitSuite(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitSuite(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SuiteContext suite() throws RecognitionException {
		SuiteContext _localctx = new SuiteContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_suite);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(157);
			match(NEWLINE);
			setState(158);
			match(INDENT);
			setState(160); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(159);
				statement();
				}
				}
				setState(162); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 1890328056L) != 0) );
			setState(164);
			match(DEDENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(FlaskPythonParser.PLUS, 0); }
		public TerminalNode GT() { return getToken(FlaskPythonParser.GT, 0); }
		public TerminalNode LT() { return getToken(FlaskPythonParser.LT, 0); }
		public TerminalNode EQEQ() { return getToken(FlaskPythonParser.EQEQ, 0); }
		public TerminalNode DOT() { return getToken(FlaskPythonParser.DOT, 0); }
		public TerminalNode IDENT() { return getToken(FlaskPythonParser.IDENT, 0); }
		public TerminalNode LPAREN() { return getToken(FlaskPythonParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(FlaskPythonParser.RPAREN, 0); }
		public ArgListContext argList() {
			return getRuleContext(ArgListContext.class,0);
		}
		public TerminalNode LBRACK() { return getToken(FlaskPythonParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(FlaskPythonParser.RBRACK, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(167);
			atom();
			}
			_ctx.stop = _input.LT(-1);
			setState(197);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(195);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(169);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(170);
						match(PLUS);
						setState(171);
						expr(5);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(172);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(173);
						match(GT);
						setState(174);
						expr(4);
						}
						break;
					case 3:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(175);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(176);
						match(LT);
						setState(177);
						expr(3);
						}
						break;
					case 4:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(178);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(179);
						match(EQEQ);
						setState(180);
						expr(2);
						}
						break;
					case 5:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(181);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(182);
						match(DOT);
						setState(183);
						match(IDENT);
						}
						break;
					case 6:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(184);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(185);
						match(LPAREN);
						setState(187);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1890065408L) != 0)) {
							{
							setState(186);
							argList();
							}
						}

						setState(189);
						match(RPAREN);
						}
						break;
					case 7:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(190);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(191);
						match(LBRACK);
						setState(192);
						expr(0);
						setState(193);
						match(RBRACK);
						}
						break;
					}
					} 
				}
				setState(199);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgListContext extends ParserRuleContext {
		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}
		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(FlaskPythonParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(FlaskPythonParser.COMMA, i);
		}
		public ArgListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterArgList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitArgList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitArgList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgListContext argList() throws RecognitionException {
		ArgListContext _localctx = new ArgListContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_argList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			argument();
			setState(205);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(201);
				match(COMMA);
				setState(202);
				argument();
				}
				}
				setState(207);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentContext extends ParserRuleContext {
		public TerminalNode IDENT() { return getToken(FlaskPythonParser.IDENT, 0); }
		public TerminalNode ASSIGN() { return getToken(FlaskPythonParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitArgument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_argument);
		try {
			setState(212);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(208);
				match(IDENT);
				setState(209);
				match(ASSIGN);
				setState(210);
				expr(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(211);
				expr(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AtomContext extends ParserRuleContext {
		public TerminalNode IDENT() { return getToken(FlaskPythonParser.IDENT, 0); }
		public TerminalNode STRING() { return getToken(FlaskPythonParser.STRING, 0); }
		public TerminalNode NUMBER() { return getToken(FlaskPythonParser.NUMBER, 0); }
		public TerminalNode TRUE() { return getToken(FlaskPythonParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(FlaskPythonParser.FALSE, 0); }
		public TerminalNode NONE() { return getToken(FlaskPythonParser.NONE, 0); }
		public DictLiteralContext dictLiteral() {
			return getRuleContext(DictLiteralContext.class,0);
		}
		public ListLiteralContext listLiteral() {
			return getRuleContext(ListLiteralContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(FlaskPythonParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(FlaskPythonParser.RPAREN, 0); }
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_atom);
		try {
			setState(226);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(214);
				match(IDENT);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(215);
				match(STRING);
				}
				break;
			case NUMBER:
				enterOuterAlt(_localctx, 3);
				{
				setState(216);
				match(NUMBER);
				}
				break;
			case TRUE:
				enterOuterAlt(_localctx, 4);
				{
				setState(217);
				match(TRUE);
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 5);
				{
				setState(218);
				match(FALSE);
				}
				break;
			case NONE:
				enterOuterAlt(_localctx, 6);
				{
				setState(219);
				match(NONE);
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 7);
				{
				setState(220);
				dictLiteral();
				}
				break;
			case LBRACK:
				enterOuterAlt(_localctx, 8);
				{
				setState(221);
				listLiteral();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 9);
				{
				setState(222);
				match(LPAREN);
				setState(223);
				expr(0);
				setState(224);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DictLiteralContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(FlaskPythonParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(FlaskPythonParser.RBRACE, 0); }
		public List<LayoutContext> layout() {
			return getRuleContexts(LayoutContext.class);
		}
		public LayoutContext layout(int i) {
			return getRuleContext(LayoutContext.class,i);
		}
		public List<DictEntryContext> dictEntry() {
			return getRuleContexts(DictEntryContext.class);
		}
		public DictEntryContext dictEntry(int i) {
			return getRuleContext(DictEntryContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(FlaskPythonParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(FlaskPythonParser.COMMA, i);
		}
		public DictLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dictLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterDictLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitDictLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitDictLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DictLiteralContext dictLiteral() throws RecognitionException {
		DictLiteralContext _localctx = new DictLiteralContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_dictLiteral);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			match(LBRACE);
			setState(232);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(229);
					layout();
					}
					} 
				}
				setState(234);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			setState(252);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1890065408L) != 0)) {
				{
				setState(235);
				dictEntry();
				setState(246);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(236);
						match(COMMA);
						setState(240);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4294967302L) != 0)) {
							{
							{
							setState(237);
							layout();
							}
							}
							setState(242);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(243);
						dictEntry();
						}
						} 
					}
					setState(248);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
				}
				setState(250);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(249);
					match(COMMA);
					}
				}

				}
			}

			setState(257);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4294967302L) != 0)) {
				{
				{
				setState(254);
				layout();
				}
				}
				setState(259);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(260);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DictEntryContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode COLON() { return getToken(FlaskPythonParser.COLON, 0); }
		public DictEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dictEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterDictEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitDictEntry(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitDictEntry(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DictEntryContext dictEntry() throws RecognitionException {
		DictEntryContext _localctx = new DictEntryContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_dictEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(262);
			expr(0);
			setState(263);
			match(COLON);
			setState(264);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ListLiteralContext extends ParserRuleContext {
		public TerminalNode LBRACK() { return getToken(FlaskPythonParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(FlaskPythonParser.RBRACK, 0); }
		public List<LayoutContext> layout() {
			return getRuleContexts(LayoutContext.class);
		}
		public LayoutContext layout(int i) {
			return getRuleContext(LayoutContext.class,i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(FlaskPythonParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(FlaskPythonParser.COMMA, i);
		}
		public ListLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterListLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitListLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitListLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListLiteralContext listLiteral() throws RecognitionException {
		ListLiteralContext _localctx = new ListLiteralContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_listLiteral);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(266);
			match(LBRACK);
			setState(270);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(267);
					layout();
					}
					} 
				}
				setState(272);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
			}
			setState(290);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1890065408L) != 0)) {
				{
				setState(273);
				expr(0);
				setState(284);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(274);
						match(COMMA);
						setState(278);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4294967302L) != 0)) {
							{
							{
							setState(275);
							layout();
							}
							}
							setState(280);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						setState(281);
						expr(0);
						}
						} 
					}
					setState(286);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
				}
				setState(288);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(287);
					match(COMMA);
					}
				}

				}
			}

			setState(295);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4294967302L) != 0)) {
				{
				{
				setState(292);
				layout();
				}
				}
				setState(297);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(298);
			match(RBRACK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LayoutContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(FlaskPythonParser.NEWLINE, 0); }
		public TerminalNode INDENT() { return getToken(FlaskPythonParser.INDENT, 0); }
		public TerminalNode DEDENT() { return getToken(FlaskPythonParser.DEDENT, 0); }
		public LayoutContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_layout; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).enterLayout(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FlaskPythonParserListener ) ((FlaskPythonParserListener)listener).exitLayout(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FlaskPythonParserVisitor ) return ((FlaskPythonParserVisitor<? extends T>)visitor).visitLayout(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LayoutContext layout() throws RecognitionException {
		LayoutContext _localctx = new LayoutContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_layout);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(300);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 4294967302L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 15:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		case 2:
			return precpred(_ctx, 2);
		case 3:
			return precpred(_ctx, 1);
		case 4:
			return precpred(_ctx, 7);
		case 5:
			return precpred(_ctx, 6);
		case 6:
			return precpred(_ctx, 5);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001!\u012f\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0005\u00003\b\u0000\n\u0000\f\u00006\t\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0005\u0001<\b\u0001\n\u0001\f\u0001?\t\u0001"+
		"\u0001\u0001\u0001\u0001\u0005\u0001C\b\u0001\n\u0001\f\u0001F\t\u0001"+
		"\u0003\u0001H\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0003\u0002O\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0003\u0003U\b\u0003\u0001\u0004\u0004\u0004X\b\u0004\u000b"+
		"\u0004\f\u0004Y\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0004\u0005a\b\u0005\u000b\u0005\f\u0005b\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0005\u0006i\b\u0006\n\u0006\f\u0006l\t\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0005\u0007t\b\u0007\n\u0007\f\u0007w\t\u0007\u0001\b\u0001\b\u0001\b"+
		"\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t\u0081\b\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0005\n\u008a\b\n\n\n\f\n\u008d"+
		"\t\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000e\u0004\u000e\u00a1\b\u000e\u000b"+
		"\u000e\f\u000e\u00a2\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0003\u000f\u00bc\b\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0005\u000f\u00c4\b\u000f\n\u000f\f\u000f"+
		"\u00c7\t\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010\u00cc\b"+
		"\u0010\n\u0010\f\u0010\u00cf\t\u0010\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0003\u0011\u00d5\b\u0011\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u00e3\b\u0012\u0001\u0013"+
		"\u0001\u0013\u0005\u0013\u00e7\b\u0013\n\u0013\f\u0013\u00ea\t\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u00ef\b\u0013\n\u0013\f\u0013"+
		"\u00f2\t\u0013\u0001\u0013\u0005\u0013\u00f5\b\u0013\n\u0013\f\u0013\u00f8"+
		"\t\u0013\u0001\u0013\u0003\u0013\u00fb\b\u0013\u0003\u0013\u00fd\b\u0013"+
		"\u0001\u0013\u0005\u0013\u0100\b\u0013\n\u0013\f\u0013\u0103\t\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0015\u0001\u0015\u0005\u0015\u010d\b\u0015\n\u0015\f\u0015\u0110\t\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0005\u0015\u0115\b\u0015\n\u0015"+
		"\f\u0015\u0118\t\u0015\u0001\u0015\u0005\u0015\u011b\b\u0015\n\u0015\f"+
		"\u0015\u011e\t\u0015\u0001\u0015\u0003\u0015\u0121\b\u0015\u0003\u0015"+
		"\u0123\b\u0015\u0001\u0015\u0005\u0015\u0126\b\u0015\n\u0015\f\u0015\u0129"+
		"\t\u0015\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0000"+
		"\u0001\u001e\u0017\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014"+
		"\u0016\u0018\u001a\u001c\u001e \"$&(*,\u0000\u0001\u0002\u0000\u0001\u0002"+
		"  \u014a\u00004\u0001\u0000\u0000\u0000\u0002G\u0001\u0000\u0000\u0000"+
		"\u0004N\u0001\u0000\u0000\u0000\u0006T\u0001\u0000\u0000\u0000\bW\u0001"+
		"\u0000\u0000\u0000\n]\u0001\u0000\u0000\u0000\fd\u0001\u0000\u0000\u0000"+
		"\u000em\u0001\u0000\u0000\u0000\u0010x\u0001\u0000\u0000\u0000\u0012|"+
		"\u0001\u0000\u0000\u0000\u0014\u0086\u0001\u0000\u0000\u0000\u0016\u008e"+
		"\u0001\u0000\u0000\u0000\u0018\u0093\u0001\u0000\u0000\u0000\u001a\u009a"+
		"\u0001\u0000\u0000\u0000\u001c\u009d\u0001\u0000\u0000\u0000\u001e\u00a6"+
		"\u0001\u0000\u0000\u0000 \u00c8\u0001\u0000\u0000\u0000\"\u00d4\u0001"+
		"\u0000\u0000\u0000$\u00e2\u0001\u0000\u0000\u0000&\u00e4\u0001\u0000\u0000"+
		"\u0000(\u0106\u0001\u0000\u0000\u0000*\u010a\u0001\u0000\u0000\u0000,"+
		"\u012c\u0001\u0000\u0000\u0000.3\u0005 \u0000\u0000/3\u0005\u0001\u0000"+
		"\u000003\u0005\u0002\u0000\u000013\u0003\u0002\u0001\u00002.\u0001\u0000"+
		"\u0000\u00002/\u0001\u0000\u0000\u000020\u0001\u0000\u0000\u000021\u0001"+
		"\u0000\u0000\u000036\u0001\u0000\u0000\u000042\u0001\u0000\u0000\u0000"+
		"45\u0001\u0000\u0000\u000057\u0001\u0000\u0000\u000064\u0001\u0000\u0000"+
		"\u000078\u0005\u0000\u0000\u00018\u0001\u0001\u0000\u0000\u00009=\u0003"+
		"\u0004\u0002\u0000:<\u0005 \u0000\u0000;:\u0001\u0000\u0000\u0000<?\u0001"+
		"\u0000\u0000\u0000=;\u0001\u0000\u0000\u0000=>\u0001\u0000\u0000\u0000"+
		">H\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000\u0000@D\u0003\u0006\u0003"+
		"\u0000AC\u0005 \u0000\u0000BA\u0001\u0000\u0000\u0000CF\u0001\u0000\u0000"+
		"\u0000DB\u0001\u0000\u0000\u0000DE\u0001\u0000\u0000\u0000EH\u0001\u0000"+
		"\u0000\u0000FD\u0001\u0000\u0000\u0000G9\u0001\u0000\u0000\u0000G@\u0001"+
		"\u0000\u0000\u0000H\u0003\u0001\u0000\u0000\u0000IO\u0003\f\u0006\u0000"+
		"JO\u0003\u000e\u0007\u0000KO\u0003\u0010\b\u0000LO\u0003\u001a\r\u0000"+
		"MO\u0003\u001e\u000f\u0000NI\u0001\u0000\u0000\u0000NJ\u0001\u0000\u0000"+
		"\u0000NK\u0001\u0000\u0000\u0000NL\u0001\u0000\u0000\u0000NM\u0001\u0000"+
		"\u0000\u0000O\u0005\u0001\u0000\u0000\u0000PU\u0003\b\u0004\u0000QU\u0003"+
		"\u0012\t\u0000RU\u0003\u0016\u000b\u0000SU\u0003\u0018\f\u0000TP\u0001"+
		"\u0000\u0000\u0000TQ\u0001\u0000\u0000\u0000TR\u0001\u0000\u0000\u0000"+
		"TS\u0001\u0000\u0000\u0000U\u0007\u0001\u0000\u0000\u0000VX\u0003\n\u0005"+
		"\u0000WV\u0001\u0000\u0000\u0000XY\u0001\u0000\u0000\u0000YW\u0001\u0000"+
		"\u0000\u0000YZ\u0001\u0000\u0000\u0000Z[\u0001\u0000\u0000\u0000[\\\u0003"+
		"\u0012\t\u0000\\\t\u0001\u0000\u0000\u0000]^\u0005\u0012\u0000\u0000^"+
		"`\u0003\u001e\u000f\u0000_a\u0005 \u0000\u0000`_\u0001\u0000\u0000\u0000"+
		"ab\u0001\u0000\u0000\u0000b`\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000"+
		"\u0000c\u000b\u0001\u0000\u0000\u0000de\u0005\u0004\u0000\u0000ej\u0005"+
		"\u001e\u0000\u0000fg\u0005\u001a\u0000\u0000gi\u0005\u001e\u0000\u0000"+
		"hf\u0001\u0000\u0000\u0000il\u0001\u0000\u0000\u0000jh\u0001\u0000\u0000"+
		"\u0000jk\u0001\u0000\u0000\u0000k\r\u0001\u0000\u0000\u0000lj\u0001\u0000"+
		"\u0000\u0000mn\u0005\u0003\u0000\u0000no\u0005\u001e\u0000\u0000op\u0005"+
		"\u0004\u0000\u0000pu\u0005\u001e\u0000\u0000qr\u0005\u001a\u0000\u0000"+
		"rt\u0005\u001e\u0000\u0000sq\u0001\u0000\u0000\u0000tw\u0001\u0000\u0000"+
		"\u0000us\u0001\u0000\u0000\u0000uv\u0001\u0000\u0000\u0000v\u000f\u0001"+
		"\u0000\u0000\u0000wu\u0001\u0000\u0000\u0000xy\u0005\u001e\u0000\u0000"+
		"yz\u0005\u000e\u0000\u0000z{\u0003\u001e\u000f\u0000{\u0011\u0001\u0000"+
		"\u0000\u0000|}\u0005\u0005\u0000\u0000}~\u0005\u001e\u0000\u0000~\u0080"+
		"\u0005\u0013\u0000\u0000\u007f\u0081\u0003\u0014\n\u0000\u0080\u007f\u0001"+
		"\u0000\u0000\u0000\u0080\u0081\u0001\u0000\u0000\u0000\u0081\u0082\u0001"+
		"\u0000\u0000\u0000\u0082\u0083\u0005\u0014\u0000\u0000\u0083\u0084\u0005"+
		"\u0019\u0000\u0000\u0084\u0085\u0003\u001c\u000e\u0000\u0085\u0013\u0001"+
		"\u0000\u0000\u0000\u0086\u008b\u0005\u001e\u0000\u0000\u0087\u0088\u0005"+
		"\u001a\u0000\u0000\u0088\u008a\u0005\u001e\u0000\u0000\u0089\u0087\u0001"+
		"\u0000\u0000\u0000\u008a\u008d\u0001\u0000\u0000\u0000\u008b\u0089\u0001"+
		"\u0000\u0000\u0000\u008b\u008c\u0001\u0000\u0000\u0000\u008c\u0015\u0001"+
		"\u0000\u0000\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008e\u008f\u0005"+
		"\u0007\u0000\u0000\u008f\u0090\u0003\u001e\u000f\u0000\u0090\u0091\u0005"+
		"\u0019\u0000\u0000\u0091\u0092\u0003\u001c\u000e\u0000\u0092\u0017\u0001"+
		"\u0000\u0000\u0000\u0093\u0094\u0005\b\u0000\u0000\u0094\u0095\u0005\u001e"+
		"\u0000\u0000\u0095\u0096\u0005\t\u0000\u0000\u0096\u0097\u0003\u001e\u000f"+
		"\u0000\u0097\u0098\u0005\u0019\u0000\u0000\u0098\u0099\u0003\u001c\u000e"+
		"\u0000\u0099\u0019\u0001\u0000\u0000\u0000\u009a\u009b\u0005\u0006\u0000"+
		"\u0000\u009b\u009c\u0003\u001e\u000f\u0000\u009c\u001b\u0001\u0000\u0000"+
		"\u0000\u009d\u009e\u0005 \u0000\u0000\u009e\u00a0\u0005\u0001\u0000\u0000"+
		"\u009f\u00a1\u0003\u0002\u0001\u0000\u00a0\u009f\u0001\u0000\u0000\u0000"+
		"\u00a1\u00a2\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001\u0000\u0000\u0000"+
		"\u00a2\u00a3\u0001\u0000\u0000\u0000\u00a3\u00a4\u0001\u0000\u0000\u0000"+
		"\u00a4\u00a5\u0005\u0002\u0000\u0000\u00a5\u001d\u0001\u0000\u0000\u0000"+
		"\u00a6\u00a7\u0006\u000f\uffff\uffff\u0000\u00a7\u00a8\u0003$\u0012\u0000"+
		"\u00a8\u00c5\u0001\u0000\u0000\u0000\u00a9\u00aa\n\u0004\u0000\u0000\u00aa"+
		"\u00ab\u0005\u000f\u0000\u0000\u00ab\u00c4\u0003\u001e\u000f\u0005\u00ac"+
		"\u00ad\n\u0003\u0000\u0000\u00ad\u00ae\u0005\u0010\u0000\u0000\u00ae\u00c4"+
		"\u0003\u001e\u000f\u0004\u00af\u00b0\n\u0002\u0000\u0000\u00b0\u00b1\u0005"+
		"\u0011\u0000\u0000\u00b1\u00c4\u0003\u001e\u000f\u0003\u00b2\u00b3\n\u0001"+
		"\u0000\u0000\u00b3\u00b4\u0005\r\u0000\u0000\u00b4\u00c4\u0003\u001e\u000f"+
		"\u0002\u00b5\u00b6\n\u0007\u0000\u0000\u00b6\u00b7\u0005\u001b\u0000\u0000"+
		"\u00b7\u00c4\u0005\u001e\u0000\u0000\u00b8\u00b9\n\u0006\u0000\u0000\u00b9"+
		"\u00bb\u0005\u0013\u0000\u0000\u00ba\u00bc\u0003 \u0010\u0000\u00bb\u00ba"+
		"\u0001\u0000\u0000\u0000\u00bb\u00bc\u0001\u0000\u0000\u0000\u00bc\u00bd"+
		"\u0001\u0000\u0000\u0000\u00bd\u00c4\u0005\u0014\u0000\u0000\u00be\u00bf"+
		"\n\u0005\u0000\u0000\u00bf\u00c0\u0005\u0017\u0000\u0000\u00c0\u00c1\u0003"+
		"\u001e\u000f\u0000\u00c1\u00c2\u0005\u0018\u0000\u0000\u00c2\u00c4\u0001"+
		"\u0000\u0000\u0000\u00c3\u00a9\u0001\u0000\u0000\u0000\u00c3\u00ac\u0001"+
		"\u0000\u0000\u0000\u00c3\u00af\u0001\u0000\u0000\u0000\u00c3\u00b2\u0001"+
		"\u0000\u0000\u0000\u00c3\u00b5\u0001\u0000\u0000\u0000\u00c3\u00b8\u0001"+
		"\u0000\u0000\u0000\u00c3\u00be\u0001\u0000\u0000\u0000\u00c4\u00c7\u0001"+
		"\u0000\u0000\u0000\u00c5\u00c3\u0001\u0000\u0000\u0000\u00c5\u00c6\u0001"+
		"\u0000\u0000\u0000\u00c6\u001f\u0001\u0000\u0000\u0000\u00c7\u00c5\u0001"+
		"\u0000\u0000\u0000\u00c8\u00cd\u0003\"\u0011\u0000\u00c9\u00ca\u0005\u001a"+
		"\u0000\u0000\u00ca\u00cc\u0003\"\u0011\u0000\u00cb\u00c9\u0001\u0000\u0000"+
		"\u0000\u00cc\u00cf\u0001\u0000\u0000\u0000\u00cd\u00cb\u0001\u0000\u0000"+
		"\u0000\u00cd\u00ce\u0001\u0000\u0000\u0000\u00ce!\u0001\u0000\u0000\u0000"+
		"\u00cf\u00cd\u0001\u0000\u0000\u0000\u00d0\u00d1\u0005\u001e\u0000\u0000"+
		"\u00d1\u00d2\u0005\u000e\u0000\u0000\u00d2\u00d5\u0003\u001e\u000f\u0000"+
		"\u00d3\u00d5\u0003\u001e\u000f\u0000\u00d4\u00d0\u0001\u0000\u0000\u0000"+
		"\u00d4\u00d3\u0001\u0000\u0000\u0000\u00d5#\u0001\u0000\u0000\u0000\u00d6"+
		"\u00e3\u0005\u001e\u0000\u0000\u00d7\u00e3\u0005\u001d\u0000\u0000\u00d8"+
		"\u00e3\u0005\u001c\u0000\u0000\u00d9\u00e3\u0005\n\u0000\u0000\u00da\u00e3"+
		"\u0005\u000b\u0000\u0000\u00db\u00e3\u0005\f\u0000\u0000\u00dc\u00e3\u0003"+
		"&\u0013\u0000\u00dd\u00e3\u0003*\u0015\u0000\u00de\u00df\u0005\u0013\u0000"+
		"\u0000\u00df\u00e0\u0003\u001e\u000f\u0000\u00e0\u00e1\u0005\u0014\u0000"+
		"\u0000\u00e1\u00e3\u0001\u0000\u0000\u0000\u00e2\u00d6\u0001\u0000\u0000"+
		"\u0000\u00e2\u00d7\u0001\u0000\u0000\u0000\u00e2\u00d8\u0001\u0000\u0000"+
		"\u0000\u00e2\u00d9\u0001\u0000\u0000\u0000\u00e2\u00da\u0001\u0000\u0000"+
		"\u0000\u00e2\u00db\u0001\u0000\u0000\u0000\u00e2\u00dc\u0001\u0000\u0000"+
		"\u0000\u00e2\u00dd\u0001\u0000\u0000\u0000\u00e2\u00de\u0001\u0000\u0000"+
		"\u0000\u00e3%\u0001\u0000\u0000\u0000\u00e4\u00e8\u0005\u0015\u0000\u0000"+
		"\u00e5\u00e7\u0003,\u0016\u0000\u00e6\u00e5\u0001\u0000\u0000\u0000\u00e7"+
		"\u00ea\u0001\u0000\u0000\u0000\u00e8\u00e6\u0001\u0000\u0000\u0000\u00e8"+
		"\u00e9\u0001\u0000\u0000\u0000\u00e9\u00fc\u0001\u0000\u0000\u0000\u00ea"+
		"\u00e8\u0001\u0000\u0000\u0000\u00eb\u00f6\u0003(\u0014\u0000\u00ec\u00f0"+
		"\u0005\u001a\u0000\u0000\u00ed\u00ef\u0003,\u0016\u0000\u00ee\u00ed\u0001"+
		"\u0000\u0000\u0000\u00ef\u00f2\u0001\u0000\u0000\u0000\u00f0\u00ee\u0001"+
		"\u0000\u0000\u0000\u00f0\u00f1\u0001\u0000\u0000\u0000\u00f1\u00f3\u0001"+
		"\u0000\u0000\u0000\u00f2\u00f0\u0001\u0000\u0000\u0000\u00f3\u00f5\u0003"+
		"(\u0014\u0000\u00f4\u00ec\u0001\u0000\u0000\u0000\u00f5\u00f8\u0001\u0000"+
		"\u0000\u0000\u00f6\u00f4\u0001\u0000\u0000\u0000\u00f6\u00f7\u0001\u0000"+
		"\u0000\u0000\u00f7\u00fa\u0001\u0000\u0000\u0000\u00f8\u00f6\u0001\u0000"+
		"\u0000\u0000\u00f9\u00fb\u0005\u001a\u0000\u0000\u00fa\u00f9\u0001\u0000"+
		"\u0000\u0000\u00fa\u00fb\u0001\u0000\u0000\u0000\u00fb\u00fd\u0001\u0000"+
		"\u0000\u0000\u00fc\u00eb\u0001\u0000\u0000\u0000\u00fc\u00fd\u0001\u0000"+
		"\u0000\u0000\u00fd\u0101\u0001\u0000\u0000\u0000\u00fe\u0100\u0003,\u0016"+
		"\u0000\u00ff\u00fe\u0001\u0000\u0000\u0000\u0100\u0103\u0001\u0000\u0000"+
		"\u0000\u0101\u00ff\u0001\u0000\u0000\u0000\u0101\u0102\u0001\u0000\u0000"+
		"\u0000\u0102\u0104\u0001\u0000\u0000\u0000\u0103\u0101\u0001\u0000\u0000"+
		"\u0000\u0104\u0105\u0005\u0016\u0000\u0000\u0105\'\u0001\u0000\u0000\u0000"+
		"\u0106\u0107\u0003\u001e\u000f\u0000\u0107\u0108\u0005\u0019\u0000\u0000"+
		"\u0108\u0109\u0003\u001e\u000f\u0000\u0109)\u0001\u0000\u0000\u0000\u010a"+
		"\u010e\u0005\u0017\u0000\u0000\u010b\u010d\u0003,\u0016\u0000\u010c\u010b"+
		"\u0001\u0000\u0000\u0000\u010d\u0110\u0001\u0000\u0000\u0000\u010e\u010c"+
		"\u0001\u0000\u0000\u0000\u010e\u010f\u0001\u0000\u0000\u0000\u010f\u0122"+
		"\u0001\u0000\u0000\u0000\u0110\u010e\u0001\u0000\u0000\u0000\u0111\u011c"+
		"\u0003\u001e\u000f\u0000\u0112\u0116\u0005\u001a\u0000\u0000\u0113\u0115"+
		"\u0003,\u0016\u0000\u0114\u0113\u0001\u0000\u0000\u0000\u0115\u0118\u0001"+
		"\u0000\u0000\u0000\u0116\u0114\u0001\u0000\u0000\u0000\u0116\u0117\u0001"+
		"\u0000\u0000\u0000\u0117\u0119\u0001\u0000\u0000\u0000\u0118\u0116\u0001"+
		"\u0000\u0000\u0000\u0119\u011b\u0003\u001e\u000f\u0000\u011a\u0112\u0001"+
		"\u0000\u0000\u0000\u011b\u011e\u0001\u0000\u0000\u0000\u011c\u011a\u0001"+
		"\u0000\u0000\u0000\u011c\u011d\u0001\u0000\u0000\u0000\u011d\u0120\u0001"+
		"\u0000\u0000\u0000\u011e\u011c\u0001\u0000\u0000\u0000\u011f\u0121\u0005"+
		"\u001a\u0000\u0000\u0120\u011f\u0001\u0000\u0000\u0000\u0120\u0121\u0001"+
		"\u0000\u0000\u0000\u0121\u0123\u0001\u0000\u0000\u0000\u0122\u0111\u0001"+
		"\u0000\u0000\u0000\u0122\u0123\u0001\u0000\u0000\u0000\u0123\u0127\u0001"+
		"\u0000\u0000\u0000\u0124\u0126\u0003,\u0016\u0000\u0125\u0124\u0001\u0000"+
		"\u0000\u0000\u0126\u0129\u0001\u0000\u0000\u0000\u0127\u0125\u0001\u0000"+
		"\u0000\u0000\u0127\u0128\u0001\u0000\u0000\u0000\u0128\u012a\u0001\u0000"+
		"\u0000\u0000\u0129\u0127\u0001\u0000\u0000\u0000\u012a\u012b\u0005\u0018"+
		"\u0000\u0000\u012b+\u0001\u0000\u0000\u0000\u012c\u012d\u0007\u0000\u0000"+
		"\u0000\u012d-\u0001\u0000\u0000\u0000 24=DGNTYbju\u0080\u008b\u00a2\u00bb"+
		"\u00c3\u00c5\u00cd\u00d4\u00e2\u00e8\u00f0\u00f6\u00fa\u00fc\u0101\u010e"+
		"\u0116\u011c\u0120\u0122\u0127";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}