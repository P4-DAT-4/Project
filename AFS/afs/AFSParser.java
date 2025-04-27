// Generated from AFS.g4 by ANTLR 4.13.2

    package afs;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class AFSParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, BOOL=49, DOUBLE=50, INT=51, STRING=52, ID=53, 
		WS=54, NEWLINE=55;
	public static final int
		RULE_prog = 0, RULE_visStmt = 1, RULE_event = 2, RULE_def = 3, RULE_params = 4, 
		RULE_declStmt = 5, RULE_declBlock = 6, RULE_impStmt = 7, RULE_impBlock = 8, 
		RULE_declExpr = 9, RULE_expr = 10, RULE_listAccess = 11, RULE_funcCall = 12, 
		RULE_type = 13;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "visStmt", "event", "def", "params", "declStmt", "declBlock", 
			"impStmt", "impBlock", "declExpr", "expr", "listAccess", "funcCall", 
			"type"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'visualize'", "'{'", "'}'", "'do'", "';'", "'fn'", "'('", "')'", 
			"'img'", "'='", "','", "'if'", "'then'", "'else'", "'while'", "'return'", 
			"'place'", "'at'", "'scale'", "'by'", "'rotate'", "'around'", "'text'", 
			"'line'", "'to'", "'curve'", "'['", "']'", "'-'", "'!'", "'*'", "'/'", 
			"'+'", "'++'", "'<='", "'>='", "'=='", "'!='", "'<'", "'>'", "'||'", 
			"'&&'", "'bool'", "'int'", "'string'", "'double'", "'shape'", "'void'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, "BOOL", "DOUBLE", "INT", "STRING", "ID", "WS", "NEWLINE"
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
	public String getGrammarFileName() { return "AFS.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public AFSParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgContext extends ParserRuleContext {
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
	 
		public ProgContext() { }
		public void copyFrom(ProgContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MainContext extends ProgContext {
		public VisStmtContext visStmt() {
			return getRuleContext(VisStmtContext.class,0);
		}
		public TerminalNode EOF() { return getToken(AFSParser.EOF, 0); }
		public List<DefContext> def() {
			return getRuleContexts(DefContext.class);
		}
		public DefContext def(int i) {
			return getRuleContext(DefContext.class,i);
		}
		public MainContext(ProgContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterMain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitMain(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitMain(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			_localctx = new MainContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(31);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 554153994617408L) != 0)) {
				{
				{
				setState(28);
				def();
				}
				}
				setState(33);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(34);
			visStmt();
			setState(35);
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
	public static class VisStmtContext extends ParserRuleContext {
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public List<EventContext> event() {
			return getRuleContexts(EventContext.class);
		}
		public EventContext event(int i) {
			return getRuleContext(EventContext.class,i);
		}
		public VisStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_visStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterVisStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitVisStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitVisStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VisStmtContext visStmt() throws RecognitionException {
		VisStmtContext _localctx = new VisStmtContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_visStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			match(T__0);
			setState(38);
			funcCall();
			setState(39);
			match(T__1);
			setState(41); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(40);
				event();
				}
				}
				setState(43); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 17451450300891264L) != 0) );
			setState(45);
			match(T__2);
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
	public static class EventContext extends ParserRuleContext {
		public EventContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_event; }
	 
		public EventContext() { }
		public void copyFrom(EventContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EventFuncContext extends EventContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public EventFuncContext(EventContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterEventFunc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitEventFunc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitEventFunc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EventContext event() throws RecognitionException {
		EventContext _localctx = new EventContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_event);
		try {
			_localctx = new EventFuncContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			expr(0);
			setState(48);
			match(T__3);
			setState(49);
			funcCall();
			setState(50);
			match(T__4);
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
	public static class DefContext extends ParserRuleContext {
		public DefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_def; }
	 
		public DefContext() { }
		public void copyFrom(DefContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FnDefContext extends DefContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(AFSParser.ID, 0); }
		public ImpBlockContext impBlock() {
			return getRuleContext(ImpBlockContext.class,0);
		}
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public FnDefContext(DefContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterFnDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitFnDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitFnDef(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VarDefContext extends DefContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(AFSParser.ID, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public VarDefContext(DefContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterVarDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitVarDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitVarDef(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VisDefContext extends DefContext {
		public TerminalNode ID() { return getToken(AFSParser.ID, 0); }
		public DeclBlockContext declBlock() {
			return getRuleContext(DeclBlockContext.class,0);
		}
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public VisDefContext(DefContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterVisDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitVisDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitVisDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefContext def() throws RecognitionException {
		DefContext _localctx = new DefContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_def);
		int _la;
		try {
			setState(76);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				_localctx = new FnDefContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(52);
				match(T__5);
				setState(53);
				type();
				setState(54);
				match(ID);
				setState(55);
				match(T__6);
				setState(57);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 554153994616832L) != 0)) {
					{
					setState(56);
					params();
					}
				}

				setState(59);
				match(T__7);
				setState(60);
				impBlock();
				}
				break;
			case T__8:
				_localctx = new VisDefContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(62);
				match(T__8);
				setState(63);
				match(ID);
				setState(64);
				match(T__6);
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 554153994616832L) != 0)) {
					{
					setState(65);
					params();
					}
				}

				setState(68);
				match(T__7);
				setState(69);
				declBlock();
				}
				break;
			case T__26:
			case T__42:
			case T__43:
			case T__44:
			case T__45:
			case T__46:
			case T__47:
				_localctx = new VarDefContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(70);
				type();
				setState(71);
				match(ID);
				setState(72);
				match(T__9);
				setState(73);
				expr(0);
				setState(74);
				match(T__4);
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
	public static class ParamsContext extends ParserRuleContext {
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<TerminalNode> ID() { return getTokens(AFSParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(AFSParser.ID, i);
		}
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterParams(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitParams(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_params);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			type();
			setState(79);
			match(ID);
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__10) {
				{
				{
				setState(80);
				match(T__10);
				setState(81);
				type();
				setState(82);
				match(ID);
				}
				}
				setState(88);
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
	public static class DeclStmtContext extends ParserRuleContext {
		public DeclStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declStmt; }
	 
		public DeclStmtContext() { }
		public void copyFrom(DeclStmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DeclStmtIfContext extends DeclStmtContext {
		public DeclExprContext declExpr() {
			return getRuleContext(DeclExprContext.class,0);
		}
		public List<DeclBlockContext> declBlock() {
			return getRuleContexts(DeclBlockContext.class);
		}
		public DeclBlockContext declBlock(int i) {
			return getRuleContext(DeclBlockContext.class,i);
		}
		public DeclStmtIfContext(DeclStmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterDeclStmtIf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitDeclStmtIf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitDeclStmtIf(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DeclStmtFuncCallContext extends DeclStmtContext {
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public DeclStmtFuncCallContext(DeclStmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterDeclStmtFuncCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitDeclStmtFuncCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitDeclStmtFuncCall(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DeclStmtDefContext extends DeclStmtContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(AFSParser.ID, 0); }
		public DeclExprContext declExpr() {
			return getRuleContext(DeclExprContext.class,0);
		}
		public DeclStmtDefContext(DeclStmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterDeclStmtDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitDeclStmtDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitDeclStmtDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclStmtContext declStmt() throws RecognitionException {
		DeclStmtContext _localctx = new DeclStmtContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_declStmt);
		int _la;
		try {
			setState(108);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__26:
			case T__42:
			case T__43:
			case T__44:
			case T__45:
			case T__46:
			case T__47:
				_localctx = new DeclStmtDefContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(89);
				type();
				setState(90);
				match(ID);
				setState(91);
				match(T__9);
				setState(92);
				declExpr();
				setState(93);
				match(T__4);
				}
				break;
			case T__11:
				_localctx = new DeclStmtIfContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(95);
				match(T__11);
				setState(96);
				match(T__6);
				setState(97);
				declExpr();
				setState(98);
				match(T__7);
				setState(99);
				match(T__12);
				setState(100);
				declBlock();
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__13) {
					{
					setState(101);
					match(T__13);
					setState(102);
					declBlock();
					}
				}

				}
				break;
			case ID:
				_localctx = new DeclStmtFuncCallContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(105);
				funcCall();
				setState(106);
				match(T__4);
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
	public static class DeclBlockContext extends ParserRuleContext {
		public List<DeclStmtContext> declStmt() {
			return getRuleContexts(DeclStmtContext.class);
		}
		public DeclStmtContext declStmt(int i) {
			return getRuleContext(DeclStmtContext.class,i);
		}
		public DeclBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterDeclBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitDeclBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitDeclBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclBlockContext declBlock() throws RecognitionException {
		DeclBlockContext _localctx = new DeclBlockContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_declBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			match(T__1);
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9561353249361920L) != 0)) {
				{
				{
				setState(111);
				declStmt();
				}
				}
				setState(116);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(117);
			match(T__2);
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
	public static class ImpStmtContext extends ParserRuleContext {
		public ImpStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_impStmt; }
	 
		public ImpStmtContext() { }
		public void copyFrom(ImpStmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImpStmtFuncCallContext extends ImpStmtContext {
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public ImpStmtFuncCallContext(ImpStmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterImpStmtFuncCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitImpStmtFuncCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitImpStmtFuncCall(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImpStmtWhlContext extends ImpStmtContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ImpBlockContext impBlock() {
			return getRuleContext(ImpBlockContext.class,0);
		}
		public ImpStmtWhlContext(ImpStmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterImpStmtWhl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitImpStmtWhl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitImpStmtWhl(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImpStmtDefContext extends ImpStmtContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(AFSParser.ID, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ImpStmtDefContext(ImpStmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterImpStmtDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitImpStmtDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitImpStmtDef(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImpStmtIfContext extends ImpStmtContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<ImpBlockContext> impBlock() {
			return getRuleContexts(ImpBlockContext.class);
		}
		public ImpBlockContext impBlock(int i) {
			return getRuleContext(ImpBlockContext.class,i);
		}
		public ImpStmtIfContext(ImpStmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterImpStmtIf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitImpStmtIf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitImpStmtIf(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImpStmtListAssContext extends ImpStmtContext {
		public TerminalNode ID() { return getToken(AFSParser.ID, 0); }
		public ListAccessContext listAccess() {
			return getRuleContext(ListAccessContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ImpStmtListAssContext(ImpStmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterImpStmtListAss(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitImpStmtListAss(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitImpStmtListAss(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImpStmtAssContext extends ImpStmtContext {
		public TerminalNode ID() { return getToken(AFSParser.ID, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ImpStmtAssContext(ImpStmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterImpStmtAss(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitImpStmtAss(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitImpStmtAss(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImpStmtRtnContext extends ImpStmtContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ImpStmtRtnContext(ImpStmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterImpStmtRtn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitImpStmtRtn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitImpStmtRtn(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImpStmtContext impStmt() throws RecognitionException {
		ImpStmtContext _localctx = new ImpStmtContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_impStmt);
		int _la;
		try {
			setState(160);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				_localctx = new ImpStmtDefContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(119);
				type();
				setState(120);
				match(ID);
				setState(121);
				match(T__9);
				setState(122);
				expr(0);
				setState(123);
				match(T__4);
				}
				break;
			case 2:
				_localctx = new ImpStmtAssContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(125);
				match(ID);
				setState(126);
				match(T__9);
				setState(127);
				expr(0);
				setState(128);
				match(T__4);
				}
				break;
			case 3:
				_localctx = new ImpStmtListAssContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(130);
				match(ID);
				setState(131);
				listAccess();
				setState(132);
				match(T__9);
				setState(133);
				expr(0);
				setState(134);
				match(T__4);
				}
				break;
			case 4:
				_localctx = new ImpStmtIfContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(136);
				match(T__11);
				setState(137);
				match(T__6);
				setState(138);
				expr(0);
				setState(139);
				match(T__7);
				setState(140);
				match(T__12);
				setState(141);
				impBlock();
				setState(144);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__13) {
					{
					setState(142);
					match(T__13);
					setState(143);
					impBlock();
					}
				}

				}
				break;
			case 5:
				_localctx = new ImpStmtWhlContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(146);
				match(T__14);
				setState(147);
				match(T__6);
				setState(148);
				expr(0);
				setState(149);
				match(T__7);
				setState(150);
				match(T__3);
				setState(151);
				impBlock();
				}
				break;
			case 6:
				_localctx = new ImpStmtRtnContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(153);
				match(T__15);
				setState(154);
				expr(0);
				setState(155);
				match(T__4);
				}
				break;
			case 7:
				_localctx = new ImpStmtFuncCallContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(157);
				funcCall();
				setState(158);
				match(T__4);
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
	public static class ImpBlockContext extends ParserRuleContext {
		public List<ImpStmtContext> impStmt() {
			return getRuleContexts(ImpStmtContext.class);
		}
		public ImpStmtContext impStmt(int i) {
			return getRuleContext(ImpStmtContext.class,i);
		}
		public ImpBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_impBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterImpBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitImpBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitImpBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImpBlockContext impBlock() throws RecognitionException {
		ImpBlockContext _localctx = new ImpBlockContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_impBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			match(T__1);
			setState(166);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 9561353249460224L) != 0)) {
				{
				{
				setState(163);
				impStmt();
				}
				}
				setState(168);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(169);
			match(T__2);
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
	public static class DeclExprContext extends ParserRuleContext {
		public DeclExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declExpr; }
	 
		public DeclExprContext() { }
		public void copyFrom(DeclExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CurveExprContext extends DeclExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public CurveExprContext(DeclExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterCurveExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitCurveExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitCurveExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PlaceExprContext extends DeclExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public PlaceExprContext(DeclExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterPlaceExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitPlaceExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitPlaceExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImpExprContext extends DeclExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ImpExprContext(DeclExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterImpExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitImpExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitImpExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ScaleExprContext extends DeclExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ScaleExprContext(DeclExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterScaleExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitScaleExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitScaleExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TextExprContext extends DeclExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TextExprContext(DeclExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterTextExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitTextExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitTextExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LineExprContext extends DeclExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public LineExprContext(DeclExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterLineExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitLineExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitLineExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RotateExprContext extends DeclExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public RotateExprContext(DeclExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterRotateExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitRotateExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitRotateExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclExprContext declExpr() throws RecognitionException {
		DeclExprContext _localctx = new DeclExprContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_declExpr);
		int _la;
		try {
			setState(231);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
			case T__26:
			case T__28:
			case T__29:
			case BOOL:
			case DOUBLE:
			case INT:
			case STRING:
			case ID:
				_localctx = new ImpExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(171);
				expr(0);
				}
				break;
			case T__16:
				_localctx = new PlaceExprContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(172);
				match(T__16);
				setState(173);
				expr(0);
				setState(174);
				match(T__17);
				setState(175);
				match(T__6);
				setState(176);
				expr(0);
				setState(177);
				match(T__10);
				setState(178);
				expr(0);
				setState(179);
				match(T__7);
				}
				break;
			case T__18:
				_localctx = new ScaleExprContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(181);
				match(T__18);
				setState(182);
				expr(0);
				setState(183);
				match(T__19);
				setState(184);
				expr(0);
				}
				break;
			case T__20:
				_localctx = new RotateExprContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(186);
				match(T__20);
				setState(187);
				expr(0);
				setState(188);
				match(T__21);
				setState(189);
				expr(0);
				setState(190);
				match(T__19);
				setState(191);
				expr(0);
				}
				break;
			case T__22:
				_localctx = new TextExprContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(193);
				match(T__22);
				setState(194);
				expr(0);
				}
				break;
			case T__23:
				_localctx = new LineExprContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(195);
				match(T__23);
				setState(196);
				match(T__6);
				setState(197);
				expr(0);
				setState(198);
				match(T__10);
				setState(199);
				expr(0);
				setState(200);
				match(T__7);
				setState(210);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__24) {
					{
					{
					setState(201);
					match(T__24);
					setState(202);
					match(T__6);
					setState(203);
					expr(0);
					setState(204);
					match(T__10);
					setState(205);
					expr(0);
					setState(206);
					match(T__7);
					}
					}
					setState(212);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__25:
				_localctx = new CurveExprContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(213);
				match(T__25);
				setState(214);
				match(T__6);
				setState(215);
				expr(0);
				setState(216);
				match(T__10);
				setState(217);
				expr(0);
				setState(218);
				match(T__7);
				setState(228);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__24) {
					{
					{
					setState(219);
					match(T__24);
					setState(220);
					match(T__6);
					setState(221);
					expr(0);
					setState(222);
					match(T__10);
					setState(223);
					expr(0);
					setState(224);
					match(T__7);
					}
					}
					setState(230);
					_errHandler.sync(this);
					_la = _input.LA(1);
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
	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MulExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public MulExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterMulExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitMulExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitMulExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public AndExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitAndExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public SubExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterSubExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitSubExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitSubExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NegExprContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NegExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterNegExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitNegExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitNegExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GtExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public GtExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterGtExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitGtExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitGtExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AddExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public AddExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterAddExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitAddExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitAddExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringContext extends ExprContext {
		public TerminalNode STRING() { return getToken(AFSParser.STRING, 0); }
		public StringContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DoubleContext extends ExprContext {
		public TerminalNode DOUBLE() { return getToken(AFSParser.DOUBLE, 0); }
		public DoubleContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterDouble(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitDouble(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitDouble(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntContext extends ExprContext {
		public TerminalNode INT() { return getToken(AFSParser.INT, 0); }
		public IntContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitInt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitInt(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public OrExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitOrExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ConcatExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ConcatExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterConcatExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitConcatExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitConcatExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DivExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public DivExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterDivExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitDivExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitDivExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NEgExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public NEgExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterNEgExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitNEgExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitNEgExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BoolContext extends ExprContext {
		public TerminalNode BOOL() { return getToken(AFSParser.BOOL, 0); }
		public BoolContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitBool(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public EqExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterEqExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitEqExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitEqExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ListIndexExprContext extends ExprContext {
		public TerminalNode ID() { return getToken(AFSParser.ID, 0); }
		public ListAccessContext listAccess() {
			return getRuleContext(ListAccessContext.class,0);
		}
		public ListIndexExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterListIndexExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitListIndexExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitListIndexExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GEqExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public GEqExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterGEqExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitGEqExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitGEqExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LsExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public LsExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterLsExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitLsExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitLsExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ListExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ListExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterListExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitListExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitListExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotExprContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NotExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterNotExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitNotExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitNotExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IDContext extends ExprContext {
		public TerminalNode ID() { return getToken(AFSParser.ID, 0); }
		public IDContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterID(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitID(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitID(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenExprContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ParenExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterParenExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitParenExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LEqExprContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public LEqExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterLEqExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitLEqExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitLEqExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FuncCallExprContext extends ExprContext {
		public FuncCallContext funcCall() {
			return getRuleContext(FuncCallContext.class,0);
		}
		public FuncCallExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterFuncCallExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitFuncCallExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitFuncCallExpr(this);
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
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(261);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(234);
				match(T__6);
				setState(235);
				expr(0);
				setState(236);
				match(T__7);
				}
				break;
			case 2:
				{
				_localctx = new ListIndexExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(238);
				match(ID);
				setState(239);
				listAccess();
				}
				break;
			case 3:
				{
				_localctx = new ListExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(240);
				match(T__26);
				setState(241);
				expr(0);
				setState(246);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__10) {
					{
					{
					setState(242);
					match(T__10);
					setState(243);
					expr(0);
					}
					}
					setState(248);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(249);
				match(T__27);
				}
				break;
			case 4:
				{
				_localctx = new FuncCallExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(251);
				funcCall();
				}
				break;
			case 5:
				{
				_localctx = new NegExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(252);
				match(T__28);
				setState(253);
				expr(20);
				}
				break;
			case 6:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(254);
				match(T__29);
				setState(255);
				expr(19);
				}
				break;
			case 7:
				{
				_localctx = new IDContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(256);
				match(ID);
				}
				break;
			case 8:
				{
				_localctx = new IntContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(257);
				match(INT);
				}
				break;
			case 9:
				{
				_localctx = new BoolContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(258);
				match(BOOL);
				}
				break;
			case 10:
				{
				_localctx = new DoubleContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(259);
				match(DOUBLE);
				}
				break;
			case 11:
				{
				_localctx = new StringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(260);
				match(STRING);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(304);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(302);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
					case 1:
						{
						_localctx = new MulExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(263);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(264);
						match(T__30);
						setState(265);
						expr(19);
						}
						break;
					case 2:
						{
						_localctx = new DivExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(266);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(267);
						match(T__31);
						setState(268);
						expr(18);
						}
						break;
					case 3:
						{
						_localctx = new AddExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(269);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(270);
						match(T__32);
						setState(271);
						expr(17);
						}
						break;
					case 4:
						{
						_localctx = new SubExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(272);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(273);
						match(T__28);
						setState(274);
						expr(16);
						}
						break;
					case 5:
						{
						_localctx = new ConcatExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(275);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(276);
						match(T__33);
						setState(277);
						expr(15);
						}
						break;
					case 6:
						{
						_localctx = new LEqExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(278);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(279);
						match(T__34);
						setState(280);
						expr(14);
						}
						break;
					case 7:
						{
						_localctx = new GEqExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(281);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(282);
						match(T__35);
						setState(283);
						expr(13);
						}
						break;
					case 8:
						{
						_localctx = new EqExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(284);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(285);
						match(T__36);
						setState(286);
						expr(12);
						}
						break;
					case 9:
						{
						_localctx = new NEgExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(287);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(288);
						match(T__37);
						setState(289);
						expr(11);
						}
						break;
					case 10:
						{
						_localctx = new LsExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(290);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(291);
						match(T__38);
						setState(292);
						expr(10);
						}
						break;
					case 11:
						{
						_localctx = new GtExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(293);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(294);
						match(T__39);
						setState(295);
						expr(9);
						}
						break;
					case 12:
						{
						_localctx = new OrExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(296);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(297);
						match(T__40);
						setState(298);
						expr(8);
						}
						break;
					case 13:
						{
						_localctx = new AndExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(299);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(300);
						match(T__41);
						setState(301);
						expr(7);
						}
						break;
					}
					} 
				}
				setState(306);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
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
	public static class ListAccessContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ListAccessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listAccess; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterListAccess(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitListAccess(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitListAccess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListAccessContext listAccess() throws RecognitionException {
		ListAccessContext _localctx = new ListAccessContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_listAccess);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(311); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(307);
					match(T__26);
					setState(308);
					expr(0);
					setState(309);
					match(T__27);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(313); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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
	public static class FuncCallContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(AFSParser.ID, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public FuncCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterFuncCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitFuncCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitFuncCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncCallContext funcCall() throws RecognitionException {
		FuncCallContext _localctx = new FuncCallContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_funcCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(315);
			match(ID);
			setState(316);
			match(T__6);
			setState(325);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 17451450300891264L) != 0)) {
				{
				setState(317);
				expr(0);
				setState(322);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__10) {
					{
					{
					setState(318);
					match(T__10);
					setState(319);
					expr(0);
					}
					}
					setState(324);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(327);
			match(T__7);
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
	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VoidTypeContext extends TypeContext {
		public VoidTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterVoidType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitVoidType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitVoidType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BoolTypeContext extends TypeContext {
		public BoolTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterBoolType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitBoolType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitBoolType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringTypeContext extends TypeContext {
		public StringTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterStringType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitStringType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitStringType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DoubleTypeContext extends TypeContext {
		public DoubleTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterDoubleType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitDoubleType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitDoubleType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ShapeTypeContext extends TypeContext {
		public ShapeTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterShapeType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitShapeType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitShapeType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ListTypeContext extends TypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ListTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterListType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitListType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitListType(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntTypeContext extends TypeContext {
		public IntTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).enterIntType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AFSListener ) ((AFSListener)listener).exitIntType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AFSVisitor ) return ((AFSVisitor<? extends T>)visitor).visitIntType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_type);
		try {
			setState(339);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__42:
				_localctx = new BoolTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(329);
				match(T__42);
				}
				break;
			case T__43:
				_localctx = new IntTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(330);
				match(T__43);
				}
				break;
			case T__44:
				_localctx = new StringTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(331);
				match(T__44);
				}
				break;
			case T__45:
				_localctx = new DoubleTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(332);
				match(T__45);
				}
				break;
			case T__46:
				_localctx = new ShapeTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(333);
				match(T__46);
				}
				break;
			case T__26:
				_localctx = new ListTypeContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(334);
				match(T__26);
				setState(335);
				type();
				setState(336);
				match(T__27);
				}
				break;
			case T__47:
				_localctx = new VoidTypeContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(338);
				match(T__47);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 10:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 18);
		case 1:
			return precpred(_ctx, 17);
		case 2:
			return precpred(_ctx, 16);
		case 3:
			return precpred(_ctx, 15);
		case 4:
			return precpred(_ctx, 14);
		case 5:
			return precpred(_ctx, 13);
		case 6:
			return precpred(_ctx, 12);
		case 7:
			return precpred(_ctx, 11);
		case 8:
			return precpred(_ctx, 10);
		case 9:
			return precpred(_ctx, 9);
		case 10:
			return precpred(_ctx, 8);
		case 11:
			return precpred(_ctx, 7);
		case 12:
			return precpred(_ctx, 6);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u00017\u0156\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0001\u0000\u0005\u0000\u001e\b\u0000\n\u0000"+
		"\f\u0000!\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0004\u0001*\b\u0001\u000b\u0001\f\u0001"+
		"+\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0003\u0003:\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003C\b\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0003\u0003M\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004U\b\u0004\n\u0004\f\u0004"+
		"X\t\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005h\b\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0003\u0005m\b\u0005\u0001\u0006\u0001\u0006"+
		"\u0005\u0006q\b\u0006\n\u0006\f\u0006t\t\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0003\u0007\u0091\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007"+
		"\u00a1\b\u0007\u0001\b\u0001\b\u0005\b\u00a5\b\b\n\b\f\b\u00a8\t\b\u0001"+
		"\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0005\t\u00d1\b\t\n\t\f\t\u00d4\t\t\u0001\t\u0001\t"+
		"\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0005\t\u00e3\b\t\n\t\f\t\u00e6\t\t\u0003\t\u00e8\b"+
		"\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0005\n\u00f5\b\n\n\n\f\n\u00f8\t\n\u0001\n\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0003\n\u0106\b\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0005\n\u012f\b\n\n"+
		"\n\f\n\u0132\t\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0004"+
		"\u000b\u0138\b\u000b\u000b\u000b\f\u000b\u0139\u0001\f\u0001\f\u0001\f"+
		"\u0001\f\u0001\f\u0005\f\u0141\b\f\n\f\f\f\u0144\t\f\u0003\f\u0146\b\f"+
		"\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0003\r\u0154\b\r\u0001\r\u0000\u0001\u0014"+
		"\u000e\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018"+
		"\u001a\u0000\u0000\u0183\u0000\u001f\u0001\u0000\u0000\u0000\u0002%\u0001"+
		"\u0000\u0000\u0000\u0004/\u0001\u0000\u0000\u0000\u0006L\u0001\u0000\u0000"+
		"\u0000\bN\u0001\u0000\u0000\u0000\nl\u0001\u0000\u0000\u0000\fn\u0001"+
		"\u0000\u0000\u0000\u000e\u00a0\u0001\u0000\u0000\u0000\u0010\u00a2\u0001"+
		"\u0000\u0000\u0000\u0012\u00e7\u0001\u0000\u0000\u0000\u0014\u0105\u0001"+
		"\u0000\u0000\u0000\u0016\u0137\u0001\u0000\u0000\u0000\u0018\u013b\u0001"+
		"\u0000\u0000\u0000\u001a\u0153\u0001\u0000\u0000\u0000\u001c\u001e\u0003"+
		"\u0006\u0003\u0000\u001d\u001c\u0001\u0000\u0000\u0000\u001e!\u0001\u0000"+
		"\u0000\u0000\u001f\u001d\u0001\u0000\u0000\u0000\u001f \u0001\u0000\u0000"+
		"\u0000 \"\u0001\u0000\u0000\u0000!\u001f\u0001\u0000\u0000\u0000\"#\u0003"+
		"\u0002\u0001\u0000#$\u0005\u0000\u0000\u0001$\u0001\u0001\u0000\u0000"+
		"\u0000%&\u0005\u0001\u0000\u0000&\'\u0003\u0018\f\u0000\')\u0005\u0002"+
		"\u0000\u0000(*\u0003\u0004\u0002\u0000)(\u0001\u0000\u0000\u0000*+\u0001"+
		"\u0000\u0000\u0000+)\u0001\u0000\u0000\u0000+,\u0001\u0000\u0000\u0000"+
		",-\u0001\u0000\u0000\u0000-.\u0005\u0003\u0000\u0000.\u0003\u0001\u0000"+
		"\u0000\u0000/0\u0003\u0014\n\u000001\u0005\u0004\u0000\u000012\u0003\u0018"+
		"\f\u000023\u0005\u0005\u0000\u00003\u0005\u0001\u0000\u0000\u000045\u0005"+
		"\u0006\u0000\u000056\u0003\u001a\r\u000067\u00055\u0000\u000079\u0005"+
		"\u0007\u0000\u00008:\u0003\b\u0004\u000098\u0001\u0000\u0000\u00009:\u0001"+
		"\u0000\u0000\u0000:;\u0001\u0000\u0000\u0000;<\u0005\b\u0000\u0000<=\u0003"+
		"\u0010\b\u0000=M\u0001\u0000\u0000\u0000>?\u0005\t\u0000\u0000?@\u0005"+
		"5\u0000\u0000@B\u0005\u0007\u0000\u0000AC\u0003\b\u0004\u0000BA\u0001"+
		"\u0000\u0000\u0000BC\u0001\u0000\u0000\u0000CD\u0001\u0000\u0000\u0000"+
		"DE\u0005\b\u0000\u0000EM\u0003\f\u0006\u0000FG\u0003\u001a\r\u0000GH\u0005"+
		"5\u0000\u0000HI\u0005\n\u0000\u0000IJ\u0003\u0014\n\u0000JK\u0005\u0005"+
		"\u0000\u0000KM\u0001\u0000\u0000\u0000L4\u0001\u0000\u0000\u0000L>\u0001"+
		"\u0000\u0000\u0000LF\u0001\u0000\u0000\u0000M\u0007\u0001\u0000\u0000"+
		"\u0000NO\u0003\u001a\r\u0000OV\u00055\u0000\u0000PQ\u0005\u000b\u0000"+
		"\u0000QR\u0003\u001a\r\u0000RS\u00055\u0000\u0000SU\u0001\u0000\u0000"+
		"\u0000TP\u0001\u0000\u0000\u0000UX\u0001\u0000\u0000\u0000VT\u0001\u0000"+
		"\u0000\u0000VW\u0001\u0000\u0000\u0000W\t\u0001\u0000\u0000\u0000XV\u0001"+
		"\u0000\u0000\u0000YZ\u0003\u001a\r\u0000Z[\u00055\u0000\u0000[\\\u0005"+
		"\n\u0000\u0000\\]\u0003\u0012\t\u0000]^\u0005\u0005\u0000\u0000^m\u0001"+
		"\u0000\u0000\u0000_`\u0005\f\u0000\u0000`a\u0005\u0007\u0000\u0000ab\u0003"+
		"\u0012\t\u0000bc\u0005\b\u0000\u0000cd\u0005\r\u0000\u0000dg\u0003\f\u0006"+
		"\u0000ef\u0005\u000e\u0000\u0000fh\u0003\f\u0006\u0000ge\u0001\u0000\u0000"+
		"\u0000gh\u0001\u0000\u0000\u0000hm\u0001\u0000\u0000\u0000ij\u0003\u0018"+
		"\f\u0000jk\u0005\u0005\u0000\u0000km\u0001\u0000\u0000\u0000lY\u0001\u0000"+
		"\u0000\u0000l_\u0001\u0000\u0000\u0000li\u0001\u0000\u0000\u0000m\u000b"+
		"\u0001\u0000\u0000\u0000nr\u0005\u0002\u0000\u0000oq\u0003\n\u0005\u0000"+
		"po\u0001\u0000\u0000\u0000qt\u0001\u0000\u0000\u0000rp\u0001\u0000\u0000"+
		"\u0000rs\u0001\u0000\u0000\u0000su\u0001\u0000\u0000\u0000tr\u0001\u0000"+
		"\u0000\u0000uv\u0005\u0003\u0000\u0000v\r\u0001\u0000\u0000\u0000wx\u0003"+
		"\u001a\r\u0000xy\u00055\u0000\u0000yz\u0005\n\u0000\u0000z{\u0003\u0014"+
		"\n\u0000{|\u0005\u0005\u0000\u0000|\u00a1\u0001\u0000\u0000\u0000}~\u0005"+
		"5\u0000\u0000~\u007f\u0005\n\u0000\u0000\u007f\u0080\u0003\u0014\n\u0000"+
		"\u0080\u0081\u0005\u0005\u0000\u0000\u0081\u00a1\u0001\u0000\u0000\u0000"+
		"\u0082\u0083\u00055\u0000\u0000\u0083\u0084\u0003\u0016\u000b\u0000\u0084"+
		"\u0085\u0005\n\u0000\u0000\u0085\u0086\u0003\u0014\n\u0000\u0086\u0087"+
		"\u0005\u0005\u0000\u0000\u0087\u00a1\u0001\u0000\u0000\u0000\u0088\u0089"+
		"\u0005\f\u0000\u0000\u0089\u008a\u0005\u0007\u0000\u0000\u008a\u008b\u0003"+
		"\u0014\n\u0000\u008b\u008c\u0005\b\u0000\u0000\u008c\u008d\u0005\r\u0000"+
		"\u0000\u008d\u0090\u0003\u0010\b\u0000\u008e\u008f\u0005\u000e\u0000\u0000"+
		"\u008f\u0091\u0003\u0010\b\u0000\u0090\u008e\u0001\u0000\u0000\u0000\u0090"+
		"\u0091\u0001\u0000\u0000\u0000\u0091\u00a1\u0001\u0000\u0000\u0000\u0092"+
		"\u0093\u0005\u000f\u0000\u0000\u0093\u0094\u0005\u0007\u0000\u0000\u0094"+
		"\u0095\u0003\u0014\n\u0000\u0095\u0096\u0005\b\u0000\u0000\u0096\u0097"+
		"\u0005\u0004\u0000\u0000\u0097\u0098\u0003\u0010\b\u0000\u0098\u00a1\u0001"+
		"\u0000\u0000\u0000\u0099\u009a\u0005\u0010\u0000\u0000\u009a\u009b\u0003"+
		"\u0014\n\u0000\u009b\u009c\u0005\u0005\u0000\u0000\u009c\u00a1\u0001\u0000"+
		"\u0000\u0000\u009d\u009e\u0003\u0018\f\u0000\u009e\u009f\u0005\u0005\u0000"+
		"\u0000\u009f\u00a1\u0001\u0000\u0000\u0000\u00a0w\u0001\u0000\u0000\u0000"+
		"\u00a0}\u0001\u0000\u0000\u0000\u00a0\u0082\u0001\u0000\u0000\u0000\u00a0"+
		"\u0088\u0001\u0000\u0000\u0000\u00a0\u0092\u0001\u0000\u0000\u0000\u00a0"+
		"\u0099\u0001\u0000\u0000\u0000\u00a0\u009d\u0001\u0000\u0000\u0000\u00a1"+
		"\u000f\u0001\u0000\u0000\u0000\u00a2\u00a6\u0005\u0002\u0000\u0000\u00a3"+
		"\u00a5\u0003\u000e\u0007\u0000\u00a4\u00a3\u0001\u0000\u0000\u0000\u00a5"+
		"\u00a8\u0001\u0000\u0000\u0000\u00a6\u00a4\u0001\u0000\u0000\u0000\u00a6"+
		"\u00a7\u0001\u0000\u0000\u0000\u00a7\u00a9\u0001\u0000\u0000\u0000\u00a8"+
		"\u00a6\u0001\u0000\u0000\u0000\u00a9\u00aa\u0005\u0003\u0000\u0000\u00aa"+
		"\u0011\u0001\u0000\u0000\u0000\u00ab\u00e8\u0003\u0014\n\u0000\u00ac\u00ad"+
		"\u0005\u0011\u0000\u0000\u00ad\u00ae\u0003\u0014\n\u0000\u00ae\u00af\u0005"+
		"\u0012\u0000\u0000\u00af\u00b0\u0005\u0007\u0000\u0000\u00b0\u00b1\u0003"+
		"\u0014\n\u0000\u00b1\u00b2\u0005\u000b\u0000\u0000\u00b2\u00b3\u0003\u0014"+
		"\n\u0000\u00b3\u00b4\u0005\b\u0000\u0000\u00b4\u00e8\u0001\u0000\u0000"+
		"\u0000\u00b5\u00b6\u0005\u0013\u0000\u0000\u00b6\u00b7\u0003\u0014\n\u0000"+
		"\u00b7\u00b8\u0005\u0014\u0000\u0000\u00b8\u00b9\u0003\u0014\n\u0000\u00b9"+
		"\u00e8\u0001\u0000\u0000\u0000\u00ba\u00bb\u0005\u0015\u0000\u0000\u00bb"+
		"\u00bc\u0003\u0014\n\u0000\u00bc\u00bd\u0005\u0016\u0000\u0000\u00bd\u00be"+
		"\u0003\u0014\n\u0000\u00be\u00bf\u0005\u0014\u0000\u0000\u00bf\u00c0\u0003"+
		"\u0014\n\u0000\u00c0\u00e8\u0001\u0000\u0000\u0000\u00c1\u00c2\u0005\u0017"+
		"\u0000\u0000\u00c2\u00e8\u0003\u0014\n\u0000\u00c3\u00c4\u0005\u0018\u0000"+
		"\u0000\u00c4\u00c5\u0005\u0007\u0000\u0000\u00c5\u00c6\u0003\u0014\n\u0000"+
		"\u00c6\u00c7\u0005\u000b\u0000\u0000\u00c7\u00c8\u0003\u0014\n\u0000\u00c8"+
		"\u00d2\u0005\b\u0000\u0000\u00c9\u00ca\u0005\u0019\u0000\u0000\u00ca\u00cb"+
		"\u0005\u0007\u0000\u0000\u00cb\u00cc\u0003\u0014\n\u0000\u00cc\u00cd\u0005"+
		"\u000b\u0000\u0000\u00cd\u00ce\u0003\u0014\n\u0000\u00ce\u00cf\u0005\b"+
		"\u0000\u0000\u00cf\u00d1\u0001\u0000\u0000\u0000\u00d0\u00c9\u0001\u0000"+
		"\u0000\u0000\u00d1\u00d4\u0001\u0000\u0000\u0000\u00d2\u00d0\u0001\u0000"+
		"\u0000\u0000\u00d2\u00d3\u0001\u0000\u0000\u0000\u00d3\u00e8\u0001\u0000"+
		"\u0000\u0000\u00d4\u00d2\u0001\u0000\u0000\u0000\u00d5\u00d6\u0005\u001a"+
		"\u0000\u0000\u00d6\u00d7\u0005\u0007\u0000\u0000\u00d7\u00d8\u0003\u0014"+
		"\n\u0000\u00d8\u00d9\u0005\u000b\u0000\u0000\u00d9\u00da\u0003\u0014\n"+
		"\u0000\u00da\u00e4\u0005\b\u0000\u0000\u00db\u00dc\u0005\u0019\u0000\u0000"+
		"\u00dc\u00dd\u0005\u0007\u0000\u0000\u00dd\u00de\u0003\u0014\n\u0000\u00de"+
		"\u00df\u0005\u000b\u0000\u0000\u00df\u00e0\u0003\u0014\n\u0000\u00e0\u00e1"+
		"\u0005\b\u0000\u0000\u00e1\u00e3\u0001\u0000\u0000\u0000\u00e2\u00db\u0001"+
		"\u0000\u0000\u0000\u00e3\u00e6\u0001\u0000\u0000\u0000\u00e4\u00e2\u0001"+
		"\u0000\u0000\u0000\u00e4\u00e5\u0001\u0000\u0000\u0000\u00e5\u00e8\u0001"+
		"\u0000\u0000\u0000\u00e6\u00e4\u0001\u0000\u0000\u0000\u00e7\u00ab\u0001"+
		"\u0000\u0000\u0000\u00e7\u00ac\u0001\u0000\u0000\u0000\u00e7\u00b5\u0001"+
		"\u0000\u0000\u0000\u00e7\u00ba\u0001\u0000\u0000\u0000\u00e7\u00c1\u0001"+
		"\u0000\u0000\u0000\u00e7\u00c3\u0001\u0000\u0000\u0000\u00e7\u00d5\u0001"+
		"\u0000\u0000\u0000\u00e8\u0013\u0001\u0000\u0000\u0000\u00e9\u00ea\u0006"+
		"\n\uffff\uffff\u0000\u00ea\u00eb\u0005\u0007\u0000\u0000\u00eb\u00ec\u0003"+
		"\u0014\n\u0000\u00ec\u00ed\u0005\b\u0000\u0000\u00ed\u0106\u0001\u0000"+
		"\u0000\u0000\u00ee\u00ef\u00055\u0000\u0000\u00ef\u0106\u0003\u0016\u000b"+
		"\u0000\u00f0\u00f1\u0005\u001b\u0000\u0000\u00f1\u00f6\u0003\u0014\n\u0000"+
		"\u00f2\u00f3\u0005\u000b\u0000\u0000\u00f3\u00f5\u0003\u0014\n\u0000\u00f4"+
		"\u00f2\u0001\u0000\u0000\u0000\u00f5\u00f8\u0001\u0000\u0000\u0000\u00f6"+
		"\u00f4\u0001\u0000\u0000\u0000\u00f6\u00f7\u0001\u0000\u0000\u0000\u00f7"+
		"\u00f9\u0001\u0000\u0000\u0000\u00f8\u00f6\u0001\u0000\u0000\u0000\u00f9"+
		"\u00fa\u0005\u001c\u0000\u0000\u00fa\u0106\u0001\u0000\u0000\u0000\u00fb"+
		"\u0106\u0003\u0018\f\u0000\u00fc\u00fd\u0005\u001d\u0000\u0000\u00fd\u0106"+
		"\u0003\u0014\n\u0014\u00fe\u00ff\u0005\u001e\u0000\u0000\u00ff\u0106\u0003"+
		"\u0014\n\u0013\u0100\u0106\u00055\u0000\u0000\u0101\u0106\u00053\u0000"+
		"\u0000\u0102\u0106\u00051\u0000\u0000\u0103\u0106\u00052\u0000\u0000\u0104"+
		"\u0106\u00054\u0000\u0000\u0105\u00e9\u0001\u0000\u0000\u0000\u0105\u00ee"+
		"\u0001\u0000\u0000\u0000\u0105\u00f0\u0001\u0000\u0000\u0000\u0105\u00fb"+
		"\u0001\u0000\u0000\u0000\u0105\u00fc\u0001\u0000\u0000\u0000\u0105\u00fe"+
		"\u0001\u0000\u0000\u0000\u0105\u0100\u0001\u0000\u0000\u0000\u0105\u0101"+
		"\u0001\u0000\u0000\u0000\u0105\u0102\u0001\u0000\u0000\u0000\u0105\u0103"+
		"\u0001\u0000\u0000\u0000\u0105\u0104\u0001\u0000\u0000\u0000\u0106\u0130"+
		"\u0001\u0000\u0000\u0000\u0107\u0108\n\u0012\u0000\u0000\u0108\u0109\u0005"+
		"\u001f\u0000\u0000\u0109\u012f\u0003\u0014\n\u0013\u010a\u010b\n\u0011"+
		"\u0000\u0000\u010b\u010c\u0005 \u0000\u0000\u010c\u012f\u0003\u0014\n"+
		"\u0012\u010d\u010e\n\u0010\u0000\u0000\u010e\u010f\u0005!\u0000\u0000"+
		"\u010f\u012f\u0003\u0014\n\u0011\u0110\u0111\n\u000f\u0000\u0000\u0111"+
		"\u0112\u0005\u001d\u0000\u0000\u0112\u012f\u0003\u0014\n\u0010\u0113\u0114"+
		"\n\u000e\u0000\u0000\u0114\u0115\u0005\"\u0000\u0000\u0115\u012f\u0003"+
		"\u0014\n\u000f\u0116\u0117\n\r\u0000\u0000\u0117\u0118\u0005#\u0000\u0000"+
		"\u0118\u012f\u0003\u0014\n\u000e\u0119\u011a\n\f\u0000\u0000\u011a\u011b"+
		"\u0005$\u0000\u0000\u011b\u012f\u0003\u0014\n\r\u011c\u011d\n\u000b\u0000"+
		"\u0000\u011d\u011e\u0005%\u0000\u0000\u011e\u012f\u0003\u0014\n\f\u011f"+
		"\u0120\n\n\u0000\u0000\u0120\u0121\u0005&\u0000\u0000\u0121\u012f\u0003"+
		"\u0014\n\u000b\u0122\u0123\n\t\u0000\u0000\u0123\u0124\u0005\'\u0000\u0000"+
		"\u0124\u012f\u0003\u0014\n\n\u0125\u0126\n\b\u0000\u0000\u0126\u0127\u0005"+
		"(\u0000\u0000\u0127\u012f\u0003\u0014\n\t\u0128\u0129\n\u0007\u0000\u0000"+
		"\u0129\u012a\u0005)\u0000\u0000\u012a\u012f\u0003\u0014\n\b\u012b\u012c"+
		"\n\u0006\u0000\u0000\u012c\u012d\u0005*\u0000\u0000\u012d\u012f\u0003"+
		"\u0014\n\u0007\u012e\u0107\u0001\u0000\u0000\u0000\u012e\u010a\u0001\u0000"+
		"\u0000\u0000\u012e\u010d\u0001\u0000\u0000\u0000\u012e\u0110\u0001\u0000"+
		"\u0000\u0000\u012e\u0113\u0001\u0000\u0000\u0000\u012e\u0116\u0001\u0000"+
		"\u0000\u0000\u012e\u0119\u0001\u0000\u0000\u0000\u012e\u011c\u0001\u0000"+
		"\u0000\u0000\u012e\u011f\u0001\u0000\u0000\u0000\u012e\u0122\u0001\u0000"+
		"\u0000\u0000\u012e\u0125\u0001\u0000\u0000\u0000\u012e\u0128\u0001\u0000"+
		"\u0000\u0000\u012e\u012b\u0001\u0000\u0000\u0000\u012f\u0132\u0001\u0000"+
		"\u0000\u0000\u0130\u012e\u0001\u0000\u0000\u0000\u0130\u0131\u0001\u0000"+
		"\u0000\u0000\u0131\u0015\u0001\u0000\u0000\u0000\u0132\u0130\u0001\u0000"+
		"\u0000\u0000\u0133\u0134\u0005\u001b\u0000\u0000\u0134\u0135\u0003\u0014"+
		"\n\u0000\u0135\u0136\u0005\u001c\u0000\u0000\u0136\u0138\u0001\u0000\u0000"+
		"\u0000\u0137\u0133\u0001\u0000\u0000\u0000\u0138\u0139\u0001\u0000\u0000"+
		"\u0000\u0139\u0137\u0001\u0000\u0000\u0000\u0139\u013a\u0001\u0000\u0000"+
		"\u0000\u013a\u0017\u0001\u0000\u0000\u0000\u013b\u013c\u00055\u0000\u0000"+
		"\u013c\u0145\u0005\u0007\u0000\u0000\u013d\u0142\u0003\u0014\n\u0000\u013e"+
		"\u013f\u0005\u000b\u0000\u0000\u013f\u0141\u0003\u0014\n\u0000\u0140\u013e"+
		"\u0001\u0000\u0000\u0000\u0141\u0144\u0001\u0000\u0000\u0000\u0142\u0140"+
		"\u0001\u0000\u0000\u0000\u0142\u0143\u0001\u0000\u0000\u0000\u0143\u0146"+
		"\u0001\u0000\u0000\u0000\u0144\u0142\u0001\u0000\u0000\u0000\u0145\u013d"+
		"\u0001\u0000\u0000\u0000\u0145\u0146\u0001\u0000\u0000\u0000\u0146\u0147"+
		"\u0001\u0000\u0000\u0000\u0147\u0148\u0005\b\u0000\u0000\u0148\u0019\u0001"+
		"\u0000\u0000\u0000\u0149\u0154\u0005+\u0000\u0000\u014a\u0154\u0005,\u0000"+
		"\u0000\u014b\u0154\u0005-\u0000\u0000\u014c\u0154\u0005.\u0000\u0000\u014d"+
		"\u0154\u0005/\u0000\u0000\u014e\u014f\u0005\u001b\u0000\u0000\u014f\u0150"+
		"\u0003\u001a\r\u0000\u0150\u0151\u0005\u001c\u0000\u0000\u0151\u0154\u0001"+
		"\u0000\u0000\u0000\u0152\u0154\u00050\u0000\u0000\u0153\u0149\u0001\u0000"+
		"\u0000\u0000\u0153\u014a\u0001\u0000\u0000\u0000\u0153\u014b\u0001\u0000"+
		"\u0000\u0000\u0153\u014c\u0001\u0000\u0000\u0000\u0153\u014d\u0001\u0000"+
		"\u0000\u0000\u0153\u014e\u0001\u0000\u0000\u0000\u0153\u0152\u0001\u0000"+
		"\u0000\u0000\u0154\u001b\u0001\u0000\u0000\u0000\u0017\u001f+9BLVglr\u0090"+
		"\u00a0\u00a6\u00d2\u00e4\u00e7\u00f6\u0105\u012e\u0130\u0139\u0142\u0145"+
		"\u0153";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}