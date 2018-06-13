// Generated from Model.g4 by ANTLR 4.7.1

package dartagnan;

import porthosc.memorymodels.axioms.old.Acyclic;
import porthosc.memorymodels.axioms.old.Axiom;
import porthosc.memorymodels.axioms.old.Irreflexive;
import porthosc.memorymodels.relations.old.*;
import porthosc.memorymodels.wmm.old.WmmOld;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;


@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ModelParser extends Parser {
    static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
        new PredictionContextCache();
    public static final int
        T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9,
        T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, ST=15, PO=16, POLOC=17,
        RFE=18, RF=19, CO=20, AD=21, DD=22, CD=23, STHD=24, SLOC=25, MFENCE=26,
        SYNC=27, LWSYNC=28, ISYNC=29, ID=30, SET=31, E=32, W=33, R=34, NAME=35,
        WS=36, ML_COMMENT=37;
    public static final int
        RULE_mcm = 0, RULE_axiom = 1, RULE_reldef = 2, RULE_relation = 3, RULE_base = 4;
    public static final String[] ruleNames = {
        "mcm", "axiom", "reldef", "relation", "base"
    };

    private static final String[] _LITERAL_NAMES = {
        null, "'acyclic'", "'as'", "'irreflexive'", "'let'", "'='", "'('", "'|'",
        "')'", "'\\'", "'&'", "'+'", "'*'", "';'", "'x'", "'st'", "'po'", "'poloc'",
        "'rfe'", "'rf'", "'co'", "'ad'", "'dd'", "'cd'", "'sthd'", "'sloc'", "'mfence'",
        "'sync'", "'lwsync'", "'isync'", null, null, "'E'", "'W'", "'R'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
        null, null, null, null, null, null, null, null, null, null, null, null,
        null, null, null, "ST", "PO", "POLOC", "RFE", "RF", "CO", "AD", "DD",
        "CD", "STHD", "SLOC", "MFENCE", "SYNC", "LWSYNC", "ISYNC", "ID", "SET",
        "E", "W", "R", "NAME", "WS", "ML_COMMENT"
    };
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
    public String getGrammarFileName() { return "Model.g4"; }

    @Override
    public String[] getRuleNames() { return ruleNames; }

    @Override
    public String getSerializedATN() { return _serializedATN; }

    @Override
    public ATN getATN() { return _ATN; }


    String test="test";

    public ModelParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
    }
    public static class McmContext extends ParserRuleContext {
        public WmmOld value;
        public AxiomContext ax1;
        public ReldefContext r1;
        public List<AxiomContext> axiom() {
            return getRuleContexts(AxiomContext.class);
        }
        public AxiomContext axiom(int i) {
            return getRuleContext(AxiomContext.class,i);
        }
        public List<ReldefContext> reldef() {
            return getRuleContexts(ReldefContext.class);
        }
        public ReldefContext reldef(int i) {
            return getRuleContext(ReldefContext.class,i);
        }
        public McmContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() { return RULE_mcm; }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof ModelListener ) ((ModelListener)listener).enterMcm(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof ModelListener ) ((ModelListener)listener).exitMcm(this);
        }
    }

    public final McmContext mcm() throws RecognitionException {
        McmContext _localctx = new McmContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_mcm);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
            ((McmContext)_localctx).value =   new WmmOld();
            setState(17);
            _errHandler.sync(this);
            _la = _input.LA(1);
            do {
                {
                setState(17);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                case T__0:
                case T__2:
                    {
                    setState(11);
                    ((McmContext)_localctx).ax1 = axiom();
                    _localctx.value.addAxiom(((McmContext)_localctx).ax1.value);
                    }
                    break;
                case T__3:
                    {
                    setState(14);
                    ((McmContext)_localctx).r1 = reldef();
                    _localctx.value.addRel(((McmContext)_localctx).r1.value);
                    }
                    break;
                default:
                    throw new NoViableAltException(this);
                }
                }
                setState(19);
                _errHandler.sync(this);
                _la = _input.LA(1);
            } while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3))) != 0) );
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

    public static class AxiomContext extends ParserRuleContext {
        public Axiom value;
        public RelationContext m1;
        public RelationContext relation() {
            return getRuleContext(RelationContext.class,0);
        }
        public TerminalNode NAME() { return getToken(ModelParser.NAME, 0); }
        public AxiomContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() { return RULE_axiom; }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof ModelListener ) ((ModelListener)listener).enterAxiom(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof ModelListener ) ((ModelListener)listener).exitAxiom(this);
        }
    }

    public final AxiomContext axiom() throws RecognitionException {
        AxiomContext _localctx = new AxiomContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_axiom);
        int _la;
        try {
            setState(35);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
            case T__0:
                enterOuterAlt(_localctx, 1);
                {
                setState(21);
                match(T__0);
                setState(22);
                ((AxiomContext)_localctx).m1 = relation(0);
                ((AxiomContext)_localctx).value =   new Acyclic(((AxiomContext)_localctx).m1.value);
                setState(26);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la==T__1) {
                    {
                    setState(24);
                    match(T__1);
                    setState(25);
                    match(NAME);
                    }
                }

                }
                break;
            case T__2:
                enterOuterAlt(_localctx, 2);
                {
                setState(28);
                match(T__2);
                setState(29);
                ((AxiomContext)_localctx).m1 = relation(0);
                ((AxiomContext)_localctx).value =   new Irreflexive(((AxiomContext)_localctx).m1.value);
                setState(33);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la==T__1) {
                    {
                    setState(31);
                    match(T__1);
                    setState(32);
                    match(NAME);
                    }
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

    public static class ReldefContext extends ParserRuleContext {
        public Relation value;
        public Token n;
        public RelationContext m1;
        public TerminalNode NAME() { return getToken(ModelParser.NAME, 0); }
        public RelationContext relation() {
            return getRuleContext(RelationContext.class,0);
        }
        public ReldefContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() { return RULE_reldef; }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof ModelListener ) ((ModelListener)listener).enterReldef(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof ModelListener ) ((ModelListener)listener).exitReldef(this);
        }
    }

    public final ReldefContext reldef() throws RecognitionException {
        ReldefContext _localctx = new ReldefContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_reldef);
        try {
            enterOuterAlt(_localctx, 1);
            {
            setState(37);
            match(T__3);
            setState(38);
            ((ReldefContext)_localctx).n = match(NAME);
            setState(39);
            match(T__4);
            setState(40);
            ((ReldefContext)_localctx).m1 = relation(0);
            ((ReldefContext)_localctx).value = ((ReldefContext)_localctx).m1.value; _localctx.value.setName((((ReldefContext)_localctx).n!=null?((ReldefContext)_localctx).n.getText():null));
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

    public static class RelationContext extends ParserRuleContext {
        public Relation value;
        public RelationContext m1;
        public BaseContext b1;
        public RelationContext m2;
        public RelationContext m3;
        public BaseContext base() {
            return getRuleContext(BaseContext.class,0);
        }
        public List<RelationContext> relation() {
            return getRuleContexts(RelationContext.class);
        }
        public RelationContext relation(int i) {
            return getRuleContext(RelationContext.class,i);
        }
        public RelationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() { return RULE_relation; }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof ModelListener ) ((ModelListener)listener).enterRelation(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof ModelListener ) ((ModelListener)listener).exitRelation(this);
        }
    }

    public final RelationContext relation() throws RecognitionException {
        return relation(0);
    }

    private RelationContext relation(int _p) throws RecognitionException {
        ParserRuleContext _parentctx = _ctx;
        int _parentState = getState();
        RelationContext _localctx = new RelationContext(_ctx, _parentState);
        RelationContext _prevctx = _localctx;
        int _startState = 6;
        enterRecursionRule(_localctx, 6, RULE_relation, _p);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
            setState(97);
            _errHandler.sync(this);
            switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
            case 1:
                {
                setState(44);
                ((RelationContext)_localctx).b1 = base();
                ((RelationContext)_localctx).value = ((RelationContext)_localctx).b1.value;
                }
                break;
            case 2:
                {
                setState(47);
                match(T__5);
                {
                setState(48);
                ((RelationContext)_localctx).m1 = relation(0);
                setState(49);
                match(T__6);
                ((RelationContext)_localctx).value = ((RelationContext)_localctx).m1.value;
                }
                setState(58);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input,5,_ctx);
                while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
                    if ( _alt==1 ) {
                        {
                        {
                        setState(52);
                        ((RelationContext)_localctx).m2 = relation(0);
                        setState(53);
                        match(T__6);
                        ((RelationContext)_localctx).value = new RelUnion(_localctx.value, ((RelationContext)_localctx).m2.value);
                        }
                        }
                    }
                    setState(60);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input,5,_ctx);
                }
                setState(61);
                ((RelationContext)_localctx).m3 = relation(0);
                setState(62);
                match(T__7);
                ((RelationContext)_localctx).value = new RelUnion(_localctx.value, ((RelationContext)_localctx).m3.value);
                }
                break;
            case 3:
                {
                setState(65);
                match(T__5);
                setState(66);
                ((RelationContext)_localctx).m1 = relation(0);
                setState(67);
                match(T__8);
                setState(68);
                ((RelationContext)_localctx).m2 = relation(0);
                setState(69);
                match(T__7);
                ((RelationContext)_localctx).value = new RelUnion(((RelationContext)_localctx).m1.value, ((RelationContext)_localctx).m2.value);
                }
                break;
            case 4:
                {
                setState(72);
                match(T__5);
                setState(73);
                ((RelationContext)_localctx).m1 = relation(0);
                setState(74);
                match(T__9);
                setState(75);
                relation(0);
                setState(76);
                match(T__7);
                ((RelationContext)_localctx).value = new RelInterSect(((RelationContext)_localctx).m1.value, ((RelationContext)_localctx).m2.value);
                }
                break;
            case 5:
                {
                setState(79);
                match(T__5);
                {
                setState(80);
                ((RelationContext)_localctx).m1 = relation(0);
                setState(81);
                match(T__12);
                ((RelationContext)_localctx).value = ((RelationContext)_localctx).m1.value;
                }
                setState(90);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input,6,_ctx);
                while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
                    if ( _alt==1 ) {
                        {
                        {
                        setState(84);
                        ((RelationContext)_localctx).m2 = relation(0);
                        setState(85);
                        match(T__12);
                        ((RelationContext)_localctx).value = new RelComposition(_localctx.value, ((RelationContext)_localctx).m2.value);
                        }
                        }
                    }
                    setState(92);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input,6,_ctx);
                }
                setState(93);
                ((RelationContext)_localctx).m3 = relation(0);
                setState(94);
                match(T__7);
                ((RelationContext)_localctx).value = new RelComposition(_localctx.value, ((RelationContext)_localctx).m3.value);
                }
                break;
            }
            _ctx.stop = _input.LT(-1);
            setState(107);
            _errHandler.sync(this);
            _alt = getInterpreter().adaptivePredict(_input,9,_ctx);
            while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
                if ( _alt==1 ) {
                    if ( _parseListeners!=null ) triggerExitRuleEvent();
                    _prevctx = _localctx;
                    {
                    setState(105);
                    _errHandler.sync(this);
                    switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
                    case 1:
                        {
                        _localctx = new RelationContext(_parentctx, _parentState);
                        _localctx.m1 = _prevctx;
                        _localctx.m1 = _prevctx;
                        pushNewRecursionContext(_localctx, _startState, RULE_relation);
                        setState(99);
                        if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                        setState(100);
                        match(T__10);
                        ((RelationContext)_localctx).value = new RelTrans(((RelationContext)_localctx).m1.value);
                        }
                        break;
                    case 2:
                        {
                        _localctx = new RelationContext(_parentctx, _parentState);
                        _localctx.m1 = _prevctx;
                        _localctx.m1 = _prevctx;
                        pushNewRecursionContext(_localctx, _startState, RULE_relation);
                        setState(102);
                        if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                        setState(103);
                        match(T__11);
                        ((RelationContext)_localctx).value = new RelTransRef(((RelationContext)_localctx).m1.value);
                        }
                        break;
                    }
                    }
                }
                setState(109);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input,9,_ctx);
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

    public static class BaseContext extends ParserRuleContext {
        public Relation value;
        public Token n;
        public TerminalNode PO() { return getToken(ModelParser.PO, 0); }
        public TerminalNode RF() { return getToken(ModelParser.RF, 0); }
        public TerminalNode CO() { return getToken(ModelParser.CO, 0); }
        public TerminalNode AD() { return getToken(ModelParser.AD, 0); }
        public TerminalNode DD() { return getToken(ModelParser.DD, 0); }
        public TerminalNode CD() { return getToken(ModelParser.CD, 0); }
        public TerminalNode STHD() { return getToken(ModelParser.STHD, 0); }
        public TerminalNode SLOC() { return getToken(ModelParser.SLOC, 0); }
        public TerminalNode MFENCE() { return getToken(ModelParser.MFENCE, 0); }
        public TerminalNode SYNC() { return getToken(ModelParser.SYNC, 0); }
        public TerminalNode LWSYNC() { return getToken(ModelParser.LWSYNC, 0); }
        public TerminalNode ISYNC() { return getToken(ModelParser.ISYNC, 0); }
        public TerminalNode ID() { return getToken(ModelParser.ID, 0); }
        public List<TerminalNode> SET() { return getTokens(ModelParser.SET); }
        public TerminalNode SET(int i) {
            return getToken(ModelParser.SET, i);
        }
        public TerminalNode NAME() { return getToken(ModelParser.NAME, 0); }
        public BaseContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }
        @Override public int getRuleIndex() { return RULE_base; }
        @Override
        public void enterRule(ParseTreeListener listener) {
            if ( listener instanceof ModelListener ) ((ModelListener)listener).enterBase(this);
        }
        @Override
        public void exitRule(ParseTreeListener listener) {
            if ( listener instanceof ModelListener ) ((ModelListener)listener).exitBase(this);
        }
    }

    public final BaseContext base() throws RecognitionException {
        BaseContext _localctx = new BaseContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_base);
        try {
            setState(144);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
            case PO:
                enterOuterAlt(_localctx, 1);
                {
                setState(110);
                match(PO);
                ((BaseContext)_localctx).value = new BasicRelation("po");
                }
                break;
            case RF:
                enterOuterAlt(_localctx, 2);
                {
                setState(112);
                match(RF);
                ((BaseContext)_localctx).value = new BasicRelation("rf");
                }
                break;
            case CO:
                enterOuterAlt(_localctx, 3);
                {
                setState(114);
                match(CO);
                ((BaseContext)_localctx).value = new BasicRelation("co");
                }
                break;
            case AD:
                enterOuterAlt(_localctx, 4);
                {
                setState(116);
                match(AD);
                ((BaseContext)_localctx).value = new BasicRelation("po");
                }
                break;
            case DD:
                enterOuterAlt(_localctx, 5);
                {
                setState(118);
                match(DD);
                ((BaseContext)_localctx).value = new BasicRelation("dd");
                }
                break;
            case CD:
                enterOuterAlt(_localctx, 6);
                {
                setState(120);
                match(CD);
                ((BaseContext)_localctx).value = new BasicRelation("cd");
                }
                break;
            case STHD:
                enterOuterAlt(_localctx, 7);
                {
                setState(122);
                match(STHD);
                ((BaseContext)_localctx).value = new BasicRelation("sthd");
                }
                break;
            case SLOC:
                enterOuterAlt(_localctx, 8);
                {
                setState(124);
                match(SLOC);
                ((BaseContext)_localctx).value = new BasicRelation("slow");
                }
                break;
            case MFENCE:
                enterOuterAlt(_localctx, 9);
                {
                setState(126);
                match(MFENCE);
                ((BaseContext)_localctx).value = new BasicRelation("mfence");
                }
                break;
            case SYNC:
                enterOuterAlt(_localctx, 10);
                {
                setState(128);
                match(SYNC);
                ((BaseContext)_localctx).value = new BasicRelation("sync");
                }
                break;
            case LWSYNC:
                enterOuterAlt(_localctx, 11);
                {
                setState(130);
                match(LWSYNC);
                ((BaseContext)_localctx).value = new BasicRelation("lwsync");
                }
                break;
            case ISYNC:
                enterOuterAlt(_localctx, 12);
                {
                setState(132);
                match(ISYNC);
                ((BaseContext)_localctx).value = new BasicRelation("isync");
                }
                break;
            case ID:
                enterOuterAlt(_localctx, 13);
                {
                setState(134);
                match(ID);
                ((BaseContext)_localctx).value = new BasicRelation("po");
                }
                break;
            case T__5:
                enterOuterAlt(_localctx, 14);
                {
                setState(136);
                match(T__5);
                setState(137);
                match(SET);
                setState(138);
                match(T__13);
                setState(139);
                match(SET);
                setState(140);
                match(T__7);
                ((BaseContext)_localctx).value = new BasicRelation("po");
                }
                break;
            case NAME:
                enterOuterAlt(_localctx, 15);
                {
                setState(142);
                ((BaseContext)_localctx).n = match(NAME);
                ((BaseContext)_localctx).value = new RelDummy((((BaseContext)_localctx).n!=null?((BaseContext)_localctx).n.getText():null));
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
        case 3:
            return relation_sempred((RelationContext)_localctx, predIndex);
        }
        return true;
    }
    private boolean relation_sempred(RelationContext _localctx, int predIndex) {
        switch (predIndex) {
        case 0:
            return precpred(_ctx, 3);
        case 1:
            return precpred(_ctx, 2);
        }
        return true;
    }

    public static final String _serializedATN =
        "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\'\u0095\4\2\t\2\4"+
        "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\2\3\2\3\2\3\2\3\2\6\2\24\n\2"+
        "\r\2\16\2\25\3\3\3\3\3\3\3\3\3\3\5\3\35\n\3\3\3\3\3\3\3\3\3\3\3\5\3$\n"+
        "\3\5\3&\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
        "\5\3\5\3\5\3\5\3\5\7\5;\n\5\f\5\16\5>\13\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
        "\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
        "\5\3\5\3\5\7\5[\n\5\f\5\16\5^\13\5\3\5\3\5\3\5\3\5\5\5d\n\5\3\5\3\5\3"+
        "\5\3\5\3\5\3\5\7\5l\n\5\f\5\16\5o\13\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
        "\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
        "\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u0093\n\6\3\6\2\3\b\7\2\4\6\b\n"+
        "\2\2\2\u00aa\2\f\3\2\2\2\4%\3\2\2\2\6\'\3\2\2\2\bc\3\2\2\2\n\u0092\3\2"+
        "\2\2\f\23\b\2\1\2\r\16\5\4\3\2\16\17\b\2\1\2\17\24\3\2\2\2\20\21\5\6\4"+
        "\2\21\22\b\2\1\2\22\24\3\2\2\2\23\r\3\2\2\2\23\20\3\2\2\2\24\25\3\2\2"+
        "\2\25\23\3\2\2\2\25\26\3\2\2\2\26\3\3\2\2\2\27\30\7\3\2\2\30\31\5\b\5"+
        "\2\31\34\b\3\1\2\32\33\7\4\2\2\33\35\7%\2\2\34\32\3\2\2\2\34\35\3\2\2"+
        "\2\35&\3\2\2\2\36\37\7\5\2\2\37 \5\b\5\2 #\b\3\1\2!\"\7\4\2\2\"$\7%\2"+
        "\2#!\3\2\2\2#$\3\2\2\2$&\3\2\2\2%\27\3\2\2\2%\36\3\2\2\2&\5\3\2\2\2\'"+
        "(\7\6\2\2()\7%\2\2)*\7\7\2\2*+\5\b\5\2+,\b\4\1\2,\7\3\2\2\2-.\b\5\1\2"+
        "./\5\n\6\2/\60\b\5\1\2\60d\3\2\2\2\61\62\7\b\2\2\62\63\5\b\5\2\63\64\7"+
        "\t\2\2\64\65\b\5\1\2\65<\3\2\2\2\66\67\5\b\5\2\678\7\t\2\289\b\5\1\29"+
        ";\3\2\2\2:\66\3\2\2\2;>\3\2\2\2<:\3\2\2\2<=\3\2\2\2=?\3\2\2\2><\3\2\2"+
        "\2?@\5\b\5\2@A\7\n\2\2AB\b\5\1\2Bd\3\2\2\2CD\7\b\2\2DE\5\b\5\2EF\7\13"+
        "\2\2FG\5\b\5\2GH\7\n\2\2HI\b\5\1\2Id\3\2\2\2JK\7\b\2\2KL\5\b\5\2LM\7\f"+
        "\2\2MN\5\b\5\2NO\7\n\2\2OP\b\5\1\2Pd\3\2\2\2QR\7\b\2\2RS\5\b\5\2ST\7\17"+
        "\2\2TU\b\5\1\2U\\\3\2\2\2VW\5\b\5\2WX\7\17\2\2XY\b\5\1\2Y[\3\2\2\2ZV\3"+
        "\2\2\2[^\3\2\2\2\\Z\3\2\2\2\\]\3\2\2\2]_\3\2\2\2^\\\3\2\2\2_`\5\b\5\2"+
        "`a\7\n\2\2ab\b\5\1\2bd\3\2\2\2c-\3\2\2\2c\61\3\2\2\2cC\3\2\2\2cJ\3\2\2"+
        "\2cQ\3\2\2\2dm\3\2\2\2ef\f\5\2\2fg\7\r\2\2gl\b\5\1\2hi\f\4\2\2ij\7\16"+
        "\2\2jl\b\5\1\2ke\3\2\2\2kh\3\2\2\2lo\3\2\2\2mk\3\2\2\2mn\3\2\2\2n\t\3"+
        "\2\2\2om\3\2\2\2pq\7\22\2\2q\u0093\b\6\1\2rs\7\25\2\2s\u0093\b\6\1\2t"+
        "u\7\26\2\2u\u0093\b\6\1\2vw\7\27\2\2w\u0093\b\6\1\2xy\7\30\2\2y\u0093"+
        "\b\6\1\2z{\7\31\2\2{\u0093\b\6\1\2|}\7\32\2\2}\u0093\b\6\1\2~\177\7\33"+
        "\2\2\177\u0093\b\6\1\2\u0080\u0081\7\34\2\2\u0081\u0093\b\6\1\2\u0082"+
        "\u0083\7\35\2\2\u0083\u0093\b\6\1\2\u0084\u0085\7\36\2\2\u0085\u0093\b"+
        "\6\1\2\u0086\u0087\7\37\2\2\u0087\u0093\b\6\1\2\u0088\u0089\7 \2\2\u0089"+
        "\u0093\b\6\1\2\u008a\u008b\7\b\2\2\u008b\u008c\7!\2\2\u008c\u008d\7\20"+
        "\2\2\u008d\u008e\7!\2\2\u008e\u008f\7\n\2\2\u008f\u0093\b\6\1\2\u0090"+
        "\u0091\7%\2\2\u0091\u0093\b\6\1\2\u0092p\3\2\2\2\u0092r\3\2\2\2\u0092"+
        "t\3\2\2\2\u0092v\3\2\2\2\u0092x\3\2\2\2\u0092z\3\2\2\2\u0092|\3\2\2\2"+
        "\u0092~\3\2\2\2\u0092\u0080\3\2\2\2\u0092\u0082\3\2\2\2\u0092\u0084\3"+
        "\2\2\2\u0092\u0086\3\2\2\2\u0092\u0088\3\2\2\2\u0092\u008a\3\2\2\2\u0092"+
        "\u0090\3\2\2\2\u0093\13\3\2\2\2\r\23\25\34#%<\\ckm\u0092";
    public static final ATN _ATN =
        new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}