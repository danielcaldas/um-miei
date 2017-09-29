// Generated from audicao.g4 by ANTLR 4.4

    import java.io.*;
    import java.util.*;
    import java.lang.*;
    import java.sql.*;
    import java.util.logging.Logger;
    import java.util.logging.Level;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class audicaoParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__29=1, T__28=2, T__27=3, T__26=4, T__25=5, T__24=6, T__23=7, T__22=8, 
		T__21=9, T__20=10, T__19=11, T__18=12, T__17=13, T__16=14, T__15=15, T__14=16, 
		T__13=17, T__12=18, T__11=19, T__10=20, T__9=21, T__8=22, T__7=23, T__6=24, 
		T__5=25, T__4=26, T__3=27, T__2=28, T__1=29, T__0=30, ID=31, INT=32, WS=33, 
		STRING=34;
	public static final String[] tokenNames = {
		"<INVALID>", "'/'", "'ALUNOS'", "'OBRA_BD'", "'OBRAS'", "'TEMA'", "'TITULO'", 
		"';'", "'DADOS'", "'DURACAO'", "'HORAINICIO'", "'HORAFIM'", "'PROFESSORES'", 
		"','", "'.'", "'ATUACAO_BD'", "'LOCAL'", "'DATA'", "'CODIGO'", "'ATUACAO'", 
		"'INSTRUMENTOS_BD'", "':'", "'INSTRUMENTOS'", "'ALUNO_BD'", "'AUDICAO'", 
		"'BD'", "'PROFESSOR_BD'", "'SUBTITULO'", "'ATUACOES'", "'-'", "'ACOMPANHADO'", 
		"ID", "INT", "WS", "STRING"
	};
	public static final int
		RULE_audicao = 0, RULE_dados = 1, RULE_bd = 2, RULE_caminhobd = 3, RULE_user = 4, 
		RULE_passwd = 5, RULE_metaInfo = 6, RULE_data = 7, RULE_hora = 8, RULE_dur = 9, 
		RULE_atuacao = 10;
	public static final String[] ruleNames = {
		"audicao", "dados", "bd", "caminhobd", "user", "passwd", "metaInfo", "data", 
		"hora", "dur", "atuacao"
	};

	@Override
	public String getGrammarFileName() { return "audicao.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    public class Dados {
	        HashSet<String> atuacao, instr, obra, prof, aluno, audicao;   
	        
	        public Dados() {
	            atuacao = new HashSet<>();  
	            instr = new HashSet<>();
	            obra = new HashSet<>();
	            prof = new HashSet<>();
	            aluno = new HashSet<>();
	            audicao = new HashSet<>();             
	        }
	    }  
	    
	    public String retiraAspas (String exp) {
	        String nExp = null;
	        if (exp != null) {
	            nExp = exp.substring(1, exp.length()-1);
	        }    
	        return nExp;
	    }

	public audicaoParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class AudicaoContext extends ParserRuleContext {
		public DadosContext d;
		public BdContext b;
		public MetaInfoContext metaInfo;
		public AtuacaoContext atuacao;
		public BdContext bd() {
			return getRuleContext(BdContext.class,0);
		}
		public MetaInfoContext metaInfo() {
			return getRuleContext(MetaInfoContext.class,0);
		}
		public AtuacaoContext atuacao(int i) {
			return getRuleContext(AtuacaoContext.class,i);
		}
		public DadosContext dados() {
			return getRuleContext(DadosContext.class,0);
		}
		public List<AtuacaoContext> atuacao() {
			return getRuleContexts(AtuacaoContext.class);
		}
		public AudicaoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_audicao; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).enterAudicao(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).exitAudicao(this);
		}
	}

	public final AudicaoContext audicao() throws RecognitionException {
		AudicaoContext _localctx = new AudicaoContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_audicao);
		 GestorAudicao gestAud = new GestorAudicao(); 
		            Dados info = new Dados();
		            boolean erro = false; boolean flagBD = false;
		            int durAud = -1; int durAts = 0;
		          
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			switch (_input.LA(1)) {
			case T__22:
				{
				setState(22); ((AudicaoContext)_localctx).d = dados(info);
				}
				break;
			case T__5:
				{
				setState(23); ((AudicaoContext)_localctx).b = bd(info, gestAud);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			 if ((((AudicaoContext)_localctx).d!=null?_input.getText(((AudicaoContext)_localctx).d.start,((AudicaoContext)_localctx).d.stop):null) != null) {
			                    if (!((AudicaoContext)_localctx).d.erroOut) {   
			                        info = ((AudicaoContext)_localctx).d.infoOut;
			                    } else {
			                        erro = true; 
			                    }
			                } else {
			                    if (!((AudicaoContext)_localctx).b.erroOut) {
			                        info = ((AudicaoContext)_localctx).b.infoOut;
			                        gestAud = ((AudicaoContext)_localctx).b.gestAudOut;
			                        flagBD = true;
			                    } else {
			                        erro = true; 
			                    }
			                }
			              
			setState(27); match(T__6);
			setState(28); ((AudicaoContext)_localctx).metaInfo = metaInfo(info, gestAud);
			 if (!((AudicaoContext)_localctx).metaInfo.erroOut) {
			                    info = ((AudicaoContext)_localctx).metaInfo.infoOut;
			                    gestAud = ((AudicaoContext)_localctx).metaInfo.gestAudOut;
			                    durAud = ((AudicaoContext)_localctx).metaInfo.durAud;
			                } else {
			                    erro = true; 
			                }
			              
			setState(30); match(T__2);
			setState(31); ((AudicaoContext)_localctx).atuacao = atuacao(info, gestAud, durAts);
			 if (!((AudicaoContext)_localctx).atuacao.erroOut) {
			                    info = ((AudicaoContext)_localctx).atuacao.infoOut;
			                    gestAud = ((AudicaoContext)_localctx).atuacao.gestAudOut;
			                    durAts = ((AudicaoContext)_localctx).atuacao.durAtsOut;
			                } else {
			                    erro = true; 
			                }
			              
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__11) {
				{
				{
				setState(33); ((AudicaoContext)_localctx).atuacao = atuacao(info, gestAud, durAts);
				 if (!((AudicaoContext)_localctx).atuacao.erroOut) {
				                    info = ((AudicaoContext)_localctx).atuacao.infoOut;
				                    gestAud = ((AudicaoContext)_localctx).atuacao.gestAudOut;
				                    durAts = ((AudicaoContext)_localctx).atuacao.durAtsOut;
				                } else {
				                    erro = true; 
				                }
				              
				}
				}
				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			 if ((durAud > -1) && (durAts > 0)) {
			                       if (durAts > durAud) {
			                            erro = true;
			                            System.out.println("ERRO: A duração total da audição definida ultrapassa o limite definido!");
			                       }
			                   }
			                
			                    if (!erro) {
			                       gestAud.audicao.criaXML();
			                       if (gestAud.con != null) { gestAud.audicao.criaHTML(gestAud.con); }
			                    }

			                    gestAud.fecharConexao();               
			                 
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

	public static class DadosContext extends ParserRuleContext {
		public Dados infoIn;
		public Dados infoOut;
		public boolean erroOut;
		public Token at1;
		public Token at2;
		public Token inst1;
		public Token inst2;
		public Token ob1;
		public Token ob2;
		public Token pr1;
		public Token pr2;
		public Token al1;
		public Token al2;
		public List<TerminalNode> ID() { return getTokens(audicaoParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(audicaoParser.ID, i);
		}
		public DadosContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public DadosContext(ParserRuleContext parent, int invokingState, Dados infoIn) {
			super(parent, invokingState);
			this.infoIn = infoIn;
		}
		@Override public int getRuleIndex() { return RULE_dados; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).enterDados(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).exitDados(this);
		}
	}

	public final DadosContext dados(Dados infoIn) throws RecognitionException {
		DadosContext _localctx = new DadosContext(_ctx, getState(), infoIn);
		enterRule(_localctx, 2, RULE_dados);
		 boolean erro = false; 
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43); match(T__22);
			setState(56);
			_la = _input.LA(1);
			if (_la==T__15) {
				{
				setState(44); match(T__15);
				setState(45); ((DadosContext)_localctx).at1 = match(ID);
				 _localctx.infoIn.atuacao.add((((DadosContext)_localctx).at1!=null?((DadosContext)_localctx).at1.getText():null)); 
				setState(52);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__17) {
					{
					{
					setState(47); match(T__17);
					setState(48); ((DadosContext)_localctx).at2 = match(ID);
					 if (_localctx.infoIn.atuacao.contains((((DadosContext)_localctx).at2!=null?((DadosContext)_localctx).at2.getText():null))) { 
					                                erro = true;
					                                System.out.println("ERRO: A atuação com identificador "+(((DadosContext)_localctx).at2!=null?((DadosContext)_localctx).at2.getText():null)+" já foi definida!");
					                            } else { _localctx.infoIn.atuacao.add((((DadosContext)_localctx).at2!=null?((DadosContext)_localctx).at2.getText():null));} 
					}
					}
					setState(54);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(55); match(T__23);
				}
			}

			setState(58); match(T__10);
			setState(59); ((DadosContext)_localctx).inst1 = match(ID);
			 _localctx.infoIn.instr.add((((DadosContext)_localctx).inst1!=null?((DadosContext)_localctx).inst1.getText():null)); 
			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(61); match(T__17);
				setState(62); ((DadosContext)_localctx).inst2 = match(ID);
				 if (_localctx.infoIn.instr.contains((((DadosContext)_localctx).inst2!=null?((DadosContext)_localctx).inst2.getText():null))) {
				                                erro = true;
				                                System.out.println("ERRO: O instrumento com identificador "+(((DadosContext)_localctx).inst2!=null?((DadosContext)_localctx).inst2.getText():null)+" já foi definido!");
				                              } else { _localctx.infoIn.instr.add((((DadosContext)_localctx).inst2!=null?((DadosContext)_localctx).inst2.getText():null));} 
				}
				}
				setState(68);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(69); match(T__23);
			setState(70); match(T__27);
			setState(71); ((DadosContext)_localctx).ob1 = match(ID);
			 _localctx.infoIn.obra.add((((DadosContext)_localctx).ob1!=null?((DadosContext)_localctx).ob1.getText():null)); 
			setState(78);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(73); match(T__17);
				setState(74); ((DadosContext)_localctx).ob2 = match(ID);
				 if (_localctx.infoIn.obra.contains((((DadosContext)_localctx).ob2!=null?((DadosContext)_localctx).ob2.getText():null))) {
				                                erro = true;
				                                System.out.println("ERRO: A obra com identificador "+(((DadosContext)_localctx).ob2!=null?((DadosContext)_localctx).ob2.getText():null)+" já foi definida!");
				                            } else { _localctx.infoIn.obra.add((((DadosContext)_localctx).ob2!=null?((DadosContext)_localctx).ob2.getText():null));} 
				}
				}
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(81); match(T__23);
			setState(82); match(T__4);
			setState(83); ((DadosContext)_localctx).pr1 = match(ID);
			 _localctx.infoIn.prof.add((((DadosContext)_localctx).pr1!=null?((DadosContext)_localctx).pr1.getText():null)); 
			setState(90);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(85); match(T__17);
				setState(86); ((DadosContext)_localctx).pr2 = match(ID);
				 if (_localctx.infoIn.prof.contains((((DadosContext)_localctx).pr2!=null?((DadosContext)_localctx).pr2.getText():null))) {
				                                erro = true;
				                                System.out.println("ERRO: O professor com identificador "+(((DadosContext)_localctx).pr2!=null?((DadosContext)_localctx).pr2.getText():null)+" já foi definido!");
				                            } else { _localctx.infoIn.prof.add((((DadosContext)_localctx).pr2!=null?((DadosContext)_localctx).pr2.getText():null));} 
				}
				}
				setState(92);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(93); match(T__23);
			setState(94); match(T__7);
			setState(95); ((DadosContext)_localctx).al1 = match(ID);
			 _localctx.infoIn.aluno.add((((DadosContext)_localctx).al1!=null?((DadosContext)_localctx).al1.getText():null)); 
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(97); match(T__17);
				setState(98); ((DadosContext)_localctx).al2 = match(ID);
				 if (_localctx.infoIn.aluno.contains((((DadosContext)_localctx).al2!=null?((DadosContext)_localctx).al2.getText():null))) { 
				                                erro = true;
				                                System.out.println("ERRO: O aluno com identificador "+(((DadosContext)_localctx).al2!=null?((DadosContext)_localctx).al2.getText():null)+" já foi definido!");
				                            } else { _localctx.infoIn.aluno.add((((DadosContext)_localctx).al2!=null?((DadosContext)_localctx).al2.getText():null));} 
				}
				}
				setState(104);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(105); match(T__23);
			 ((DadosContext)_localctx).erroOut =  erro;
			                ((DadosContext)_localctx).infoOut =  _localctx.infoIn;
			              
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

	public static class BdContext extends ParserRuleContext {
		public Dados infoIn;
		public GestorAudicao gestAudIn;
		public Dados infoOut;
		public GestorAudicao gestAudOut;
		public boolean erroOut;
		public CaminhobdContext caminhobd;
		public UserContext user;
		public PasswdContext passwd;
		public UserContext user() {
			return getRuleContext(UserContext.class,0);
		}
		public CaminhobdContext caminhobd() {
			return getRuleContext(CaminhobdContext.class,0);
		}
		public PasswdContext passwd() {
			return getRuleContext(PasswdContext.class,0);
		}
		public BdContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public BdContext(ParserRuleContext parent, int invokingState, Dados infoIn, GestorAudicao gestAudIn) {
			super(parent, invokingState);
			this.infoIn = infoIn;
			this.gestAudIn = gestAudIn;
		}
		@Override public int getRuleIndex() { return RULE_bd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).enterBd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).exitBd(this);
		}
	}

	public final BdContext bd(Dados infoIn,GestorAudicao gestAudIn) throws RecognitionException {
		BdContext _localctx = new BdContext(_ctx, getState(), infoIn, gestAudIn);
		enterRule(_localctx, 4, RULE_bd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108); match(T__5);
			setState(109); ((BdContext)_localctx).caminhobd = caminhobd();
			setState(110); ((BdContext)_localctx).user = user();
			setState(111); ((BdContext)_localctx).passwd = passwd();
			 boolean erro = false;
			                _localctx.gestAudIn.abrirConexao((((BdContext)_localctx).caminhobd!=null?_input.getText(((BdContext)_localctx).caminhobd.start,((BdContext)_localctx).caminhobd.stop):null), (((BdContext)_localctx).user!=null?_input.getText(((BdContext)_localctx).user.start,((BdContext)_localctx).user.stop):null), (((BdContext)_localctx).passwd!=null?_input.getText(((BdContext)_localctx).passwd.start,((BdContext)_localctx).passwd.stop):null));
			                if (_localctx.gestAudIn.con != null) {
			                    _localctx.gestAudIn.buscarDados("Atuacao", 2, _localctx.infoIn.atuacao);
			                    _localctx.gestAudIn.buscarDados("Instrumento", 2, _localctx.infoIn.instr);
			                    _localctx.gestAudIn.buscarDados("Obra", 2, _localctx.infoIn.obra);
			                    _localctx.gestAudIn.buscarDados("Professor", 2, _localctx.infoIn.prof);
			                    _localctx.gestAudIn.buscarDados("Aluno", 2, _localctx.infoIn.aluno);
			                    _localctx.gestAudIn.buscarDados("Audicao", 2, _localctx.infoIn.audicao);
			                } else {
			                    System.out.println("ERRO: Problema a estabelecer ligação à base de dados!");
			                    erro = true;
			                }
			                ((BdContext)_localctx).erroOut =  erro;
			                ((BdContext)_localctx).infoOut =  _localctx.infoIn;
			                ((BdContext)_localctx).gestAudOut =  _localctx.gestAudIn;
			              
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

	public static class CaminhobdContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(audicaoParser.STRING, 0); }
		public CaminhobdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caminhobd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).enterCaminhobd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).exitCaminhobd(this);
		}
	}

	public final CaminhobdContext caminhobd() throws RecognitionException {
		CaminhobdContext _localctx = new CaminhobdContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_caminhobd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114); match(STRING);
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

	public static class UserContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(audicaoParser.STRING, 0); }
		public UserContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_user; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).enterUser(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).exitUser(this);
		}
	}

	public final UserContext user() throws RecognitionException {
		UserContext _localctx = new UserContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_user);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116); match(STRING);
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

	public static class PasswdContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(audicaoParser.STRING, 0); }
		public PasswdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_passwd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).enterPasswd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).exitPasswd(this);
		}
	}

	public final PasswdContext passwd() throws RecognitionException {
		PasswdContext _localctx = new PasswdContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_passwd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118); match(STRING);
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

	public static class MetaInfoContext extends ParserRuleContext {
		public Dados infoIn;
		public GestorAudicao gestAudIn;
		public Dados infoOut;
		public GestorAudicao gestAudOut;
		public boolean erroOut;
		public int durAud;
		public Token id;
		public Token tit;
		public Token sub;
		public Token t;
		public DataContext dt;
		public HoraContext hini;
		public HoraContext hfim;
		public DurContext d;
		public Token l;
		public DataContext data() {
			return getRuleContext(DataContext.class,0);
		}
		public TerminalNode ID() { return getToken(audicaoParser.ID, 0); }
		public HoraContext hora(int i) {
			return getRuleContext(HoraContext.class,i);
		}
		public TerminalNode STRING(int i) {
			return getToken(audicaoParser.STRING, i);
		}
		public List<TerminalNode> STRING() { return getTokens(audicaoParser.STRING); }
		public DurContext dur() {
			return getRuleContext(DurContext.class,0);
		}
		public List<HoraContext> hora() {
			return getRuleContexts(HoraContext.class);
		}
		public MetaInfoContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public MetaInfoContext(ParserRuleContext parent, int invokingState, Dados infoIn, GestorAudicao gestAudIn) {
			super(parent, invokingState);
			this.infoIn = infoIn;
			this.gestAudIn = gestAudIn;
		}
		@Override public int getRuleIndex() { return RULE_metaInfo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).enterMetaInfo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).exitMetaInfo(this);
		}
	}

	public final MetaInfoContext metaInfo(Dados infoIn,GestorAudicao gestAudIn) throws RecognitionException {
		MetaInfoContext _localctx = new MetaInfoContext(_ctx, getState(), infoIn, gestAudIn);
		enterRule(_localctx, 12, RULE_metaInfo);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120); match(T__12);
			setState(121); ((MetaInfoContext)_localctx).id = match(ID);
			setState(122); match(T__17);
			setState(123); match(T__24);
			setState(124); ((MetaInfoContext)_localctx).tit = match(STRING);
			setState(125); match(T__17);
			setState(129);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(126); match(T__3);
				setState(127); ((MetaInfoContext)_localctx).sub = match(STRING);
				setState(128); match(T__17);
				}
			}

			setState(131); match(T__25);
			setState(132); ((MetaInfoContext)_localctx).t = match(STRING);
			setState(133); match(T__17);
			setState(134); match(T__13);
			setState(135); ((MetaInfoContext)_localctx).dt = data();
			setState(136); match(T__17);
			setState(137); match(T__20);
			setState(138); ((MetaInfoContext)_localctx).hini = hora();
			setState(139); match(T__17);
			setState(148);
			_la = _input.LA(1);
			if (_la==T__21 || _la==T__19) {
				{
				setState(144);
				switch (_input.LA(1)) {
				case T__19:
					{
					setState(140); match(T__19);
					setState(141); ((MetaInfoContext)_localctx).hfim = hora();
					}
					break;
				case T__21:
					{
					setState(142); match(T__21);
					setState(143); ((MetaInfoContext)_localctx).d = dur();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(146); match(T__17);
				}
			}

			setState(150); match(T__14);
			setState(151); ((MetaInfoContext)_localctx).l = match(STRING);
			 boolean erro = false;
			                String id = (((MetaInfoContext)_localctx).id!=null?((MetaInfoContext)_localctx).id.getText():null);
			                ((MetaInfoContext)_localctx).durAud =  -1;
			                int durH, durM;
			                if (_localctx.infoIn.audicao.contains(id)) {
			                    erro = true;
			                    System.out.println("ERRO: A audição "+id+" já existe!");
			                    _localctx.gestAudIn.audicao = new Audicao(null, null, null, null, null, null, null, null, null);
			                } else {
			                    String tit = retiraAspas((((MetaInfoContext)_localctx).tit!=null?((MetaInfoContext)_localctx).tit.getText():null));
			                    String sub = null;
			                    if ((((MetaInfoContext)_localctx).sub!=null?((MetaInfoContext)_localctx).sub.getText():null) != null) {sub = retiraAspas((((MetaInfoContext)_localctx).sub!=null?((MetaInfoContext)_localctx).sub.getText():null)); }
			                    String t = retiraAspas((((MetaInfoContext)_localctx).t!=null?((MetaInfoContext)_localctx).t.getText():null));
			                    String dt = ((MetaInfoContext)_localctx).dt.val;
			                    String hini = ((MetaInfoContext)_localctx).hini.val;
			                    String hfim = null;
			                    if ((((MetaInfoContext)_localctx).hfim!=null?_input.getText(((MetaInfoContext)_localctx).hfim.start,((MetaInfoContext)_localctx).hfim.stop):null) != null) { 
			                        hfim = ((MetaInfoContext)_localctx).hfim.val; 
			                        int maior = _localctx.gestAudIn.horaMaior(((MetaInfoContext)_localctx).hini.hr, ((MetaInfoContext)_localctx).hini.min, ((MetaInfoContext)_localctx).hfim.hr, ((MetaInfoContext)_localctx).hfim.min);
			                        if (maior == 1) {
			                            System.out.println("AVISO: A hora de início é maior que a hora de fim!");
			                        } 
			                        /* Diferenca entre horas */
			                        if (((MetaInfoContext)_localctx).hini.hr > ((MetaInfoContext)_localctx).hfim.hr) {
			                            durH = 24 - (((MetaInfoContext)_localctx).hini.hr - ((MetaInfoContext)_localctx).hfim.hr);
			                            
			                            if (((MetaInfoContext)_localctx).hini.min < ((MetaInfoContext)_localctx).hfim.min) {
			                                durM = ((MetaInfoContext)_localctx).hfim.min - ((MetaInfoContext)_localctx).hini.min;
			                            } else {
			                                durH--;
			                                durM = 60 - (((MetaInfoContext)_localctx).hini.min - ((MetaInfoContext)_localctx).hfim.min);
			                            }
			                        } else if (((MetaInfoContext)_localctx).hini.hr < ((MetaInfoContext)_localctx).hfim.hr)  {
			                            durH = ((MetaInfoContext)_localctx).hfim.hr - ((MetaInfoContext)_localctx).hini.hr;
			                            
			                            if (((MetaInfoContext)_localctx).hini.min < ((MetaInfoContext)_localctx).hfim.min) {
			                                durM = ((MetaInfoContext)_localctx).hfim.min - ((MetaInfoContext)_localctx).hini.min;
			                            } else {
			                                durH--;
			                                durM = 60 - (((MetaInfoContext)_localctx).hini.min - ((MetaInfoContext)_localctx).hfim.min);
			                            }
			                        } else {
			                            if (((MetaInfoContext)_localctx).hfim.min > ((MetaInfoContext)_localctx).hini.min) {
			                                durH = 0;
			                                durM = ((MetaInfoContext)_localctx).hfim.min - ((MetaInfoContext)_localctx).hini.min;
			                            } else {
			                                durH = 23;
			                                durM = 60 - (((MetaInfoContext)_localctx).hini.min - ((MetaInfoContext)_localctx).hfim.min);
			                            }
			                        }
			                        ((MetaInfoContext)_localctx).durAud =  (durH*60*60) + (durM*60);
			                    }
			                    String d = null;
			                    if ((((MetaInfoContext)_localctx).d!=null?_input.getText(((MetaInfoContext)_localctx).d.start,((MetaInfoContext)_localctx).d.stop):null) != null) {
			                        d = ((MetaInfoContext)_localctx).d.val;
			                        durH = ((MetaInfoContext)_localctx).d.hr;
			                        durM = ((MetaInfoContext)_localctx).d.min;
			                        ((MetaInfoContext)_localctx).durAud =  (durH*60*60) + (durM*60);
			                    }
			                    
			                    String l = retiraAspas((((MetaInfoContext)_localctx).l!=null?((MetaInfoContext)_localctx).l.getText():null));
			                    _localctx.gestAudIn.audicao = new Audicao(id, tit, sub, t, dt, hini, hfim, d, l);
			                }
			                
			                ((MetaInfoContext)_localctx).erroOut =  erro;
			                ((MetaInfoContext)_localctx).infoOut =  _localctx.infoIn;
			                ((MetaInfoContext)_localctx).gestAudOut =  _localctx.gestAudIn;
			              
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

	public static class DataContext extends ParserRuleContext {
		public String val;
		public Token d;
		public Token m;
		public Token a;
		public TerminalNode INT(int i) {
			return getToken(audicaoParser.INT, i);
		}
		public List<TerminalNode> INT() { return getTokens(audicaoParser.INT); }
		public DataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_data; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).enterData(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).exitData(this);
		}
	}

	public final DataContext data() throws RecognitionException {
		DataContext _localctx = new DataContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_data);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154); ((DataContext)_localctx).d = match(INT);
			setState(155);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__29) | (1L << T__16) | (1L << T__1))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(156); ((DataContext)_localctx).m = match(INT);
			setState(157);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__29) | (1L << T__16) | (1L << T__1))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(158); ((DataContext)_localctx).a = match(INT);
			 boolean flag = false;
			                if ((((DataContext)_localctx).d!=null?Integer.valueOf(((DataContext)_localctx).d.getText()):0) < 1 || (((DataContext)_localctx).d!=null?Integer.valueOf(((DataContext)_localctx).d.getText()):0)>31) {
			                    flag = true;
			                    System.out.println("ERRO: O dia da data "+(((DataContext)_localctx).d!=null?Integer.valueOf(((DataContext)_localctx).d.getText()):0)+"/"+(((DataContext)_localctx).m!=null?Integer.valueOf(((DataContext)_localctx).m.getText()):0)+"/"+(((DataContext)_localctx).a!=null?Integer.valueOf(((DataContext)_localctx).a.getText()):0)+" não é válido!"); 
			                }
			                if ((((DataContext)_localctx).m!=null?Integer.valueOf(((DataContext)_localctx).m.getText()):0) < 1 || (((DataContext)_localctx).m!=null?Integer.valueOf(((DataContext)_localctx).m.getText()):0)>12) {
			                    flag = true;
			                    System.out.println("ERRO: O mês da data "+(((DataContext)_localctx).d!=null?Integer.valueOf(((DataContext)_localctx).d.getText()):0)+"/"+(((DataContext)_localctx).m!=null?Integer.valueOf(((DataContext)_localctx).m.getText()):0)+"/"+(((DataContext)_localctx).a!=null?Integer.valueOf(((DataContext)_localctx).a.getText()):0)+" não é válido!"); 
			                }
			                if ((((DataContext)_localctx).a!=null?Integer.valueOf(((DataContext)_localctx).a.getText()):0) < 1) {
			                    flag = true;
			                    System.out.println("ERRO: O ano da data "+(((DataContext)_localctx).d!=null?Integer.valueOf(((DataContext)_localctx).d.getText()):0)+"/"+(((DataContext)_localctx).m!=null?Integer.valueOf(((DataContext)_localctx).m.getText()):0)+"/"+(((DataContext)_localctx).a!=null?Integer.valueOf(((DataContext)_localctx).a.getText()):0)+" não é válido!"); 
			                } 
			                if (!flag) {
			                    ((DataContext)_localctx).val =  (((DataContext)_localctx).d!=null?((DataContext)_localctx).d.getText():null)+"/"+(((DataContext)_localctx).m!=null?((DataContext)_localctx).m.getText():null)+"/"+(((DataContext)_localctx).a!=null?((DataContext)_localctx).a.getText():null);
			                } else {
			                    ((DataContext)_localctx).val =  null;
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

	public static class HoraContext extends ParserRuleContext {
		public String val;
		public int hr;
		public int min;
		public Token h;
		public Token m;
		public TerminalNode INT(int i) {
			return getToken(audicaoParser.INT, i);
		}
		public List<TerminalNode> INT() { return getTokens(audicaoParser.INT); }
		public HoraContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hora; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).enterHora(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).exitHora(this);
		}
	}

	public final HoraContext hora() throws RecognitionException {
		HoraContext _localctx = new HoraContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_hora);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161); ((HoraContext)_localctx).h = match(INT);
			setState(162); match(T__9);
			setState(163); ((HoraContext)_localctx).m = match(INT);
			 if (((((HoraContext)_localctx).h!=null?Integer.valueOf(((HoraContext)_localctx).h.getText()):0) > 23) || ((((HoraContext)_localctx).m!=null?Integer.valueOf(((HoraContext)_localctx).m.getText()):0) > 59)) {
			                    System.out.println("ERRO: A hora "+(((HoraContext)_localctx).h!=null?Integer.valueOf(((HoraContext)_localctx).h.getText()):0)+":"+(((HoraContext)_localctx).m!=null?Integer.valueOf(((HoraContext)_localctx).m.getText()):0)+" não é válida!");
			                    ((HoraContext)_localctx).val =  null;
			                } else {
			                    ((HoraContext)_localctx).val =  (((HoraContext)_localctx).h!=null?((HoraContext)_localctx).h.getText():null)+":"+(((HoraContext)_localctx).m!=null?((HoraContext)_localctx).m.getText():null);    
			                    ((HoraContext)_localctx).hr =  (((HoraContext)_localctx).h!=null?Integer.valueOf(((HoraContext)_localctx).h.getText()):0);
			                    ((HoraContext)_localctx).min =  (((HoraContext)_localctx).m!=null?Integer.valueOf(((HoraContext)_localctx).m.getText()):0);
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

	public static class DurContext extends ParserRuleContext {
		public String val;
		public int hr;
		public int min;
		public Token h;
		public Token m;
		public TerminalNode INT(int i) {
			return getToken(audicaoParser.INT, i);
		}
		public List<TerminalNode> INT() { return getTokens(audicaoParser.INT); }
		public DurContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dur; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).enterDur(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).exitDur(this);
		}
	}

	public final DurContext dur() throws RecognitionException {
		DurContext _localctx = new DurContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_dur);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166); ((DurContext)_localctx).h = match(INT);
			setState(167); match(T__9);
			setState(168); ((DurContext)_localctx).m = match(INT);
			 if ((((DurContext)_localctx).m!=null?Integer.valueOf(((DurContext)_localctx).m.getText()):0) > 59) {
			                    System.out.println("ERRO: A duração "+(((DurContext)_localctx).h!=null?Integer.valueOf(((DurContext)_localctx).h.getText()):0)+":"+(((DurContext)_localctx).m!=null?Integer.valueOf(((DurContext)_localctx).m.getText()):0)+" não é válida!");
			                    ((DurContext)_localctx).val =  null;
			                } else {
			                    ((DurContext)_localctx).val =  (((DurContext)_localctx).h!=null?((DurContext)_localctx).h.getText():null)+":"+(((DurContext)_localctx).m!=null?((DurContext)_localctx).m.getText():null);    
			                    ((DurContext)_localctx).hr =  (((DurContext)_localctx).h!=null?Integer.valueOf(((DurContext)_localctx).h.getText()):0);
			                    ((DurContext)_localctx).min =  (((DurContext)_localctx).m!=null?Integer.valueOf(((DurContext)_localctx).m.getText()):0);
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

	public static class AtuacaoContext extends ParserRuleContext {
		public Dados infoIn;
		public GestorAudicao gestAudIn;
		public int durAtsIn;
		public Dados infoOut;
		public GestorAudicao gestAudOut;
		public boolean erroOut;
		public int durAtsOut;
		public Token ID;
		public Token idAt;
		public List<TerminalNode> ID() { return getTokens(audicaoParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(audicaoParser.ID, i);
		}
		public AtuacaoContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public AtuacaoContext(ParserRuleContext parent, int invokingState, Dados infoIn, GestorAudicao gestAudIn, int durAtsIn) {
			super(parent, invokingState);
			this.infoIn = infoIn;
			this.gestAudIn = gestAudIn;
			this.durAtsIn = durAtsIn;
		}
		@Override public int getRuleIndex() { return RULE_atuacao; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).enterAtuacao(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof audicaoListener ) ((audicaoListener)listener).exitAtuacao(this);
		}
	}

	public final AtuacaoContext atuacao(Dados infoIn,GestorAudicao gestAudIn,int durAtsIn) throws RecognitionException {
		AtuacaoContext _localctx = new AtuacaoContext(_ctx, getState(), infoIn, gestAudIn, durAtsIn);
		enterRule(_localctx, 20, RULE_atuacao);
		 HashSet <String> i = new HashSet<>();
		                HashSet <String> o = new HashSet<>();
		                HashSet <String> p = new HashSet<>();
		                HashSet <String> a = new HashSet<>();
		                HashSet <String> ac = new HashSet<>();
		                boolean erro = false;
		              
		int _la;
		try {
			setState(246);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(171); match(T__11);
				setState(172); ((AtuacaoContext)_localctx).ID = match(ID);
				setState(173); match(T__16);
				 if (_localctx.infoIn.atuacao.contains((((AtuacaoContext)_localctx).ID!=null?((AtuacaoContext)_localctx).ID.getText():null)) == false) {
				                    erro = true;
				                    System.out.println ("ERRO: A atuação "+(((AtuacaoContext)_localctx).ID!=null?((AtuacaoContext)_localctx).ID.getText():null)+" não existe!");
				                } else {
				                    _localctx.gestAudIn.audicao.addAtuacao((((AtuacaoContext)_localctx).ID!=null?((AtuacaoContext)_localctx).ID.getText():null));
				                    if (_localctx.gestAudIn.con != null){
				                        try {
				                            Statement st = _localctx.gestAudIn.con.createStatement();
				                            ResultSet rs = st.executeQuery("SELECT duracao FROM Atuacao WHERE idAtuacao='"+(((AtuacaoContext)_localctx).ID!=null?((AtuacaoContext)_localctx).ID.getText():null)+"';");
				                            rs.next();
				                            _localctx.durAtsIn += Integer.parseInt(rs.getString(1));
				                            rs.close();
				                            st.close();
				                        } catch (Exception ex) {
				                            ex.printStackTrace();
				                        }
				                    }
				                }
				                ((AtuacaoContext)_localctx).erroOut =  erro;
				                ((AtuacaoContext)_localctx).infoOut =  _localctx.infoIn;
				                ((AtuacaoContext)_localctx).gestAudOut =  _localctx.gestAudIn;
				                ((AtuacaoContext)_localctx).durAtsOut =  _localctx.durAtsIn;
				              
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(175); match(T__11);
				setState(176); match(T__12);
				setState(177); ((AtuacaoContext)_localctx).idAt = match(ID);
				setState(178); match(T__23);
				 if (_localctx.infoIn.atuacao.contains((((AtuacaoContext)_localctx).idAt!=null?((AtuacaoContext)_localctx).idAt.getText():null)) == true) {
				                    erro = true;
				                    System.out.println ("ERRO: A atuação "+(((AtuacaoContext)_localctx).idAt!=null?((AtuacaoContext)_localctx).idAt.getText():null)+" já existe!");
				                } else {
				                    _localctx.gestAudIn.audicao.addAtuacao((((AtuacaoContext)_localctx).idAt!=null?((AtuacaoContext)_localctx).idAt.getText():null));
				                }
				              
				setState(180); match(T__8);
				setState(181); ((AtuacaoContext)_localctx).ID = match(ID);
				 i.add((((AtuacaoContext)_localctx).ID!=null?((AtuacaoContext)_localctx).ID.getText():null)); 
				setState(188);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__17) {
					{
					{
					setState(183); match(T__17);
					setState(184); ((AtuacaoContext)_localctx).ID = match(ID);
					 i.add((((AtuacaoContext)_localctx).ID!=null?((AtuacaoContext)_localctx).ID.getText():null)); 
					}
					}
					setState(190);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(191); match(T__23);
				 boolean flag = false;
				                for (String x: i) {
				                    if (!_localctx.infoIn.instr.contains(x)) {
				                        System.out.println ("ERRO: O instrumento "+x+" não existe!");
				                        erro = true; flag = true;
				                    }
				                }
				                if (!flag) {
				                    _localctx.gestAudIn.audicao.instr.put((((AtuacaoContext)_localctx).idAt!=null?((AtuacaoContext)_localctx).idAt.getText():null), i);
				                }
				              
				setState(193); match(T__26);
				setState(194); ((AtuacaoContext)_localctx).ID = match(ID);
				 o.add((((AtuacaoContext)_localctx).ID!=null?((AtuacaoContext)_localctx).ID.getText():null)); 
				setState(201);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__17) {
					{
					{
					setState(196); match(T__17);
					setState(197); ((AtuacaoContext)_localctx).ID = match(ID);
					 o.add((((AtuacaoContext)_localctx).ID!=null?((AtuacaoContext)_localctx).ID.getText():null)); 
					}
					}
					setState(203);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(204); match(T__23);
				 flag = false;
				                for (String x: o) {
				                    if (!_localctx.infoIn.obra.contains(x)) {
				                        System.out.println ("ERRO: A obra "+x+" não existe!");
				                        erro = true; flag = true;
				                    } else if (_localctx.gestAudIn.con != null){
				                        try {
				                            Statement st = _localctx.gestAudIn.con.createStatement();
				                            ResultSet rs = st.executeQuery("SELECT duracao FROM Obra WHERE idObra='"+x+"';");
				                            rs.next();
				                            _localctx.durAtsIn += Integer.parseInt(rs.getString(1));
				                            rs.close();
				                            st.close();
				                        } catch (Exception ex) {
				                            ex.printStackTrace();
				                        }

				                        String selObra = "SELECT id FROM Obra WHERE idObra=\""+x+"\"";
				                        for (String inst: i) {
				                            String selInst = "SELECT id FROM Instrumento WHERE idInst=\""+inst+"\"";
				                            String query = "SELECT id FROM Partitura WHERE (idObra=("+selObra+") AND idInst=("+selInst+"));";
				                            
				                            try {
				                                Statement st = _localctx.gestAudIn.con.createStatement();
				                                ResultSet rs = st.executeQuery(query);
				                                if (!rs.next()) {
				                                    System.out.println("AVISO: Não existem partituras da obra "+x+" para o instrumento "+inst+"!");
				                                }
				                                st.close();
				                                rs.close();
				                            } catch (Exception ex) {
				                                ex.printStackTrace();
				                            }
				                        }    
				                    }
				                }
				                if (!flag) {
				                    _localctx.gestAudIn.audicao.obras.put((((AtuacaoContext)_localctx).idAt!=null?((AtuacaoContext)_localctx).idAt.getText():null), o);
				                }
				              
				setState(206); match(T__18);
				setState(207); ((AtuacaoContext)_localctx).ID = match(ID);
				 p.add((((AtuacaoContext)_localctx).ID!=null?((AtuacaoContext)_localctx).ID.getText():null)); 
				setState(214);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__17) {
					{
					{
					setState(209); match(T__17);
					setState(210); ((AtuacaoContext)_localctx).ID = match(ID);
					 p.add((((AtuacaoContext)_localctx).ID!=null?((AtuacaoContext)_localctx).ID.getText():null)); 
					}
					}
					setState(216);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(217); match(T__23);
				 flag = false;
				                for (String x: p) {
				                    if (!_localctx.infoIn.prof.contains(x)) {
				                        System.out.println ("ERRO: O professor "+x+" não existe!");
				                        flag = true; erro = true;
				                    } else if (_localctx.gestAudIn.con != null){
				                        if (_localctx.gestAudIn.temQualificacoes(x, i) == false) {
				                            System.out.println ("AVISO: O professor "+x+" pode não ter qualificações para dirigir a atuação "+(((AtuacaoContext)_localctx).ID!=null?((AtuacaoContext)_localctx).ID.getText():null)+"!");
				                        }
				                    }
				                }
				                if (!flag) {
				                    _localctx.gestAudIn.audicao.profs.put((((AtuacaoContext)_localctx).idAt!=null?((AtuacaoContext)_localctx).idAt.getText():null), p);
				                }
				              
				setState(219); match(T__28);
				setState(220); ((AtuacaoContext)_localctx).ID = match(ID);
				 a.add((((AtuacaoContext)_localctx).ID!=null?((AtuacaoContext)_localctx).ID.getText():null)); 
				setState(227);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__17) {
					{
					{
					setState(222); match(T__17);
					setState(223); ((AtuacaoContext)_localctx).ID = match(ID);
					 a.add((((AtuacaoContext)_localctx).ID!=null?((AtuacaoContext)_localctx).ID.getText():null)); 
					}
					}
					setState(229);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				 flag = false;
				                for (String x: a) {
				                    if (!_localctx.infoIn.aluno.contains(x)) {
				                        System.out.println ("ERRO: O aluno "+x+" não existe!");
				                        erro = true; flag = true;
				                    }
				                }
				                if (!flag) {
				                    _localctx.gestAudIn.audicao.alunos.put((((AtuacaoContext)_localctx).idAt!=null?((AtuacaoContext)_localctx).idAt.getText():null), a);
				                }
				              
				setState(242);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(231); match(T__0);
					setState(232); ((AtuacaoContext)_localctx).ID = match(ID);
					 ac.add((((AtuacaoContext)_localctx).ID!=null?((AtuacaoContext)_localctx).ID.getText():null)); 
					setState(239);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__17) {
						{
						{
						setState(234); match(T__17);
						setState(235); ((AtuacaoContext)_localctx).ID = match(ID);
						 ac.add((((AtuacaoContext)_localctx).ID!=null?((AtuacaoContext)_localctx).ID.getText():null)); 
						}
						}
						setState(241);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(244); match(T__16);
				 flag = false;
				                for (String x: ac) {
				                    if (!_localctx.infoIn.aluno.contains(x) && !_localctx.infoIn.prof.contains(x)) {
				                        System.out.println ("ERRO: O acompanhante "+x+" não existe!");
				                        erro = true; flag = true;
				                    } 
				                    if (a.contains(x)) {
				                        System.out.println ("ERRO: O aluno "+x+" não pode ser acompanhante.");
				                        erro = true; flag = true;
				                    }
				                }
				                if (!flag) {
				                    _localctx.gestAudIn.audicao.acomp.put((((AtuacaoContext)_localctx).idAt!=null?((AtuacaoContext)_localctx).idAt.getText():null), ac);
				                }
				                
				                ((AtuacaoContext)_localctx).erroOut =  erro;
				                ((AtuacaoContext)_localctx).infoOut =  _localctx.infoIn;
				                ((AtuacaoContext)_localctx).gestAudOut =  _localctx.gestAudIn;
				                ((AtuacaoContext)_localctx).durAtsOut =  _localctx.durAtsIn;
				              
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

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3$\u00fb\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\3\2\3\2\5\2\33\n\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\7\2\'\n\2\f\2\16\2*\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3\65\n"+
		"\3\f\3\16\38\13\3\3\3\5\3;\n\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3C\n\3\f\3\16"+
		"\3F\13\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3O\n\3\f\3\16\3R\13\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\7\3[\n\3\f\3\16\3^\13\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\7\3g\n\3\f\3\16\3j\13\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3"+
		"\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u0084\n\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u0093\n\b\3\b\3\b\5"+
		"\b\u0097\n\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3"+
		"\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\7\f\u00bd\n\f\f\f\16\f\u00c0\13\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\7\f\u00ca\n\f\f\f\16\f\u00cd\13\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\7\f\u00d7\n\f\f\f\16\f\u00da\13\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\7\f\u00e4\n\f\f\f\16\f\u00e7\13\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\7\f\u00f0\n\f\f\f\16\f\u00f3\13\f\5\f\u00f5\n\f\3\f\3\f\5\f\u00f9\n"+
		"\f\3\f\2\2\r\2\4\6\b\n\f\16\20\22\24\26\2\3\5\2\3\3\20\20\37\37\u0101"+
		"\2\32\3\2\2\2\4-\3\2\2\2\6n\3\2\2\2\bt\3\2\2\2\nv\3\2\2\2\fx\3\2\2\2\16"+
		"z\3\2\2\2\20\u009c\3\2\2\2\22\u00a3\3\2\2\2\24\u00a8\3\2\2\2\26\u00f8"+
		"\3\2\2\2\30\33\5\4\3\2\31\33\5\6\4\2\32\30\3\2\2\2\32\31\3\2\2\2\33\34"+
		"\3\2\2\2\34\35\b\2\1\2\35\36\7\32\2\2\36\37\5\16\b\2\37 \b\2\1\2 !\7\36"+
		"\2\2!\"\5\26\f\2\"(\b\2\1\2#$\5\26\f\2$%\b\2\1\2%\'\3\2\2\2&#\3\2\2\2"+
		"\'*\3\2\2\2(&\3\2\2\2()\3\2\2\2)+\3\2\2\2*(\3\2\2\2+,\b\2\1\2,\3\3\2\2"+
		"\2-:\7\n\2\2./\7\21\2\2/\60\7!\2\2\60\66\b\3\1\2\61\62\7\17\2\2\62\63"+
		"\7!\2\2\63\65\b\3\1\2\64\61\3\2\2\2\658\3\2\2\2\66\64\3\2\2\2\66\67\3"+
		"\2\2\2\679\3\2\2\28\66\3\2\2\29;\7\t\2\2:.\3\2\2\2:;\3\2\2\2;<\3\2\2\2"+
		"<=\7\26\2\2=>\7!\2\2>D\b\3\1\2?@\7\17\2\2@A\7!\2\2AC\b\3\1\2B?\3\2\2\2"+
		"CF\3\2\2\2DB\3\2\2\2DE\3\2\2\2EG\3\2\2\2FD\3\2\2\2GH\7\t\2\2HI\7\5\2\2"+
		"IJ\7!\2\2JP\b\3\1\2KL\7\17\2\2LM\7!\2\2MO\b\3\1\2NK\3\2\2\2OR\3\2\2\2"+
		"PN\3\2\2\2PQ\3\2\2\2QS\3\2\2\2RP\3\2\2\2ST\7\t\2\2TU\7\34\2\2UV\7!\2\2"+
		"V\\\b\3\1\2WX\7\17\2\2XY\7!\2\2Y[\b\3\1\2ZW\3\2\2\2[^\3\2\2\2\\Z\3\2\2"+
		"\2\\]\3\2\2\2]_\3\2\2\2^\\\3\2\2\2_`\7\t\2\2`a\7\31\2\2ab\7!\2\2bh\b\3"+
		"\1\2cd\7\17\2\2de\7!\2\2eg\b\3\1\2fc\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2"+
		"\2\2ik\3\2\2\2jh\3\2\2\2kl\7\t\2\2lm\b\3\1\2m\5\3\2\2\2no\7\33\2\2op\5"+
		"\b\5\2pq\5\n\6\2qr\5\f\7\2rs\b\4\1\2s\7\3\2\2\2tu\7$\2\2u\t\3\2\2\2vw"+
		"\7$\2\2w\13\3\2\2\2xy\7$\2\2y\r\3\2\2\2z{\7\24\2\2{|\7!\2\2|}\7\17\2\2"+
		"}~\7\b\2\2~\177\7$\2\2\177\u0083\7\17\2\2\u0080\u0081\7\35\2\2\u0081\u0082"+
		"\7$\2\2\u0082\u0084\7\17\2\2\u0083\u0080\3\2\2\2\u0083\u0084\3\2\2\2\u0084"+
		"\u0085\3\2\2\2\u0085\u0086\7\7\2\2\u0086\u0087\7$\2\2\u0087\u0088\7\17"+
		"\2\2\u0088\u0089\7\23\2\2\u0089\u008a\5\20\t\2\u008a\u008b\7\17\2\2\u008b"+
		"\u008c\7\f\2\2\u008c\u008d\5\22\n\2\u008d\u0096\7\17\2\2\u008e\u008f\7"+
		"\r\2\2\u008f\u0093\5\22\n\2\u0090\u0091\7\13\2\2\u0091\u0093\5\24\13\2"+
		"\u0092\u008e\3\2\2\2\u0092\u0090\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u0095"+
		"\7\17\2\2\u0095\u0097\3\2\2\2\u0096\u0092\3\2\2\2\u0096\u0097\3\2\2\2"+
		"\u0097\u0098\3\2\2\2\u0098\u0099\7\22\2\2\u0099\u009a\7$\2\2\u009a\u009b"+
		"\b\b\1\2\u009b\17\3\2\2\2\u009c\u009d\7\"\2\2\u009d\u009e\t\2\2\2\u009e"+
		"\u009f\7\"\2\2\u009f\u00a0\t\2\2\2\u00a0\u00a1\7\"\2\2\u00a1\u00a2\b\t"+
		"\1\2\u00a2\21\3\2\2\2\u00a3\u00a4\7\"\2\2\u00a4\u00a5\7\27\2\2\u00a5\u00a6"+
		"\7\"\2\2\u00a6\u00a7\b\n\1\2\u00a7\23\3\2\2\2\u00a8\u00a9\7\"\2\2\u00a9"+
		"\u00aa\7\27\2\2\u00aa\u00ab\7\"\2\2\u00ab\u00ac\b\13\1\2\u00ac\25\3\2"+
		"\2\2\u00ad\u00ae\7\25\2\2\u00ae\u00af\7!\2\2\u00af\u00b0\7\20\2\2\u00b0"+
		"\u00f9\b\f\1\2\u00b1\u00b2\7\25\2\2\u00b2\u00b3\7\24\2\2\u00b3\u00b4\7"+
		"!\2\2\u00b4\u00b5\7\t\2\2\u00b5\u00b6\b\f\1\2\u00b6\u00b7\7\30\2\2\u00b7"+
		"\u00b8\7!\2\2\u00b8\u00be\b\f\1\2\u00b9\u00ba\7\17\2\2\u00ba\u00bb\7!"+
		"\2\2\u00bb\u00bd\b\f\1\2\u00bc\u00b9\3\2\2\2\u00bd\u00c0\3\2\2\2\u00be"+
		"\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c1\3\2\2\2\u00c0\u00be\3\2"+
		"\2\2\u00c1\u00c2\7\t\2\2\u00c2\u00c3\b\f\1\2\u00c3\u00c4\7\6\2\2\u00c4"+
		"\u00c5\7!\2\2\u00c5\u00cb\b\f\1\2\u00c6\u00c7\7\17\2\2\u00c7\u00c8\7!"+
		"\2\2\u00c8\u00ca\b\f\1\2\u00c9\u00c6\3\2\2\2\u00ca\u00cd\3\2\2\2\u00cb"+
		"\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00ce\3\2\2\2\u00cd\u00cb\3\2"+
		"\2\2\u00ce\u00cf\7\t\2\2\u00cf\u00d0\b\f\1\2\u00d0\u00d1\7\16\2\2\u00d1"+
		"\u00d2\7!\2\2\u00d2\u00d8\b\f\1\2\u00d3\u00d4\7\17\2\2\u00d4\u00d5\7!"+
		"\2\2\u00d5\u00d7\b\f\1\2\u00d6\u00d3\3\2\2\2\u00d7\u00da\3\2\2\2\u00d8"+
		"\u00d6\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00db\3\2\2\2\u00da\u00d8\3\2"+
		"\2\2\u00db\u00dc\7\t\2\2\u00dc\u00dd\b\f\1\2\u00dd\u00de\7\4\2\2\u00de"+
		"\u00df\7!\2\2\u00df\u00e5\b\f\1\2\u00e0\u00e1\7\17\2\2\u00e1\u00e2\7!"+
		"\2\2\u00e2\u00e4\b\f\1\2\u00e3\u00e0\3\2\2\2\u00e4\u00e7\3\2\2\2\u00e5"+
		"\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e8\3\2\2\2\u00e7\u00e5\3\2"+
		"\2\2\u00e8\u00f4\b\f\1\2\u00e9\u00ea\7 \2\2\u00ea\u00eb\7!\2\2\u00eb\u00f1"+
		"\b\f\1\2\u00ec\u00ed\7\17\2\2\u00ed\u00ee\7!\2\2\u00ee\u00f0\b\f\1\2\u00ef"+
		"\u00ec\3\2\2\2\u00f0\u00f3\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f1\u00f2\3\2"+
		"\2\2\u00f2\u00f5\3\2\2\2\u00f3\u00f1\3\2\2\2\u00f4\u00e9\3\2\2\2\u00f4"+
		"\u00f5\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f6\u00f7\7\20\2\2\u00f7\u00f9\b"+
		"\f\1\2\u00f8\u00ad\3\2\2\2\u00f8\u00b1\3\2\2\2\u00f9\27\3\2\2\2\24\32"+
		"(\66:DP\\h\u0083\u0092\u0096\u00be\u00cb\u00d8\u00e5\u00f1\u00f4\u00f8";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}