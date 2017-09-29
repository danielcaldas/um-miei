/*
* @version 1.1
* DSL para definir audicoes.
*
* ----------- Lista de validacoes semanticas: -----------
* [Feito] - Validacao da data e hora;
* [Feito] - Verificar se ID da atuacao existe na BD;
* [Feito] - Verificar se obras utilizadas existem;
* [Feito] - Verificar se professores existem;
* [Feito] - Verificar se obras existem na base de dados;
*
* [Feito] - Verificar se professores estao qualificados para dirigir atuacao (habilitacoes + cursos leciona); 
* [Feito] - Verificar se existem pautas para as obras a serem executadas, caso nao existam o sistema deve dar um aviso;
* [Feito] - Verificar se aluno na lista ALUNOS nao aparece na lista ACOMPANHADO;
*
* ----------- Lista de resultados finais: -----------
* [Feito] - (Audicao e novas atuacoes) Deve gerar o XML e guardar num ficheiro, de acordo com um Schema pre definido pelo grupo;
* 
*/

grammar audicao;

@header{
    import java.io.*;
    import java.util.*;
    import java.lang.*;
    import java.sql.*;
    import java.util.logging.Logger;
    import java.util.logging.Level;
}

@members {
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
}

audicao 
    @init { GestorAudicao gestAud = new GestorAudicao(); 
            Dados info = new Dados();
            boolean erro = false; boolean flagBD = false;
            int durAud = -1; int durAts = 0;
          } 
            : (d=dados[info] | b=bd[info, gestAud]) 
              { if ($d.text != null) {
                    if (!$d.erroOut) {   
                        info = $d.infoOut;
                    } else {
                        erro = true; 
                    }
                } else {
                    if (!$b.erroOut) {
                        info = $b.infoOut;
                        gestAud = $b.gestAudOut;
                        flagBD = true;
                    } else {
                        erro = true; 
                    }
                }
              }
              
              'AUDICAO' metaInfo[info, gestAud] 
              { if (!$metaInfo.erroOut) {
                    info = $metaInfo.infoOut;
                    gestAud = $metaInfo.gestAudOut;
                    durAud = $metaInfo.durAud;
                } else {
                    erro = true; 
                }
              }
              
              'ATUACOES' atuacao[info, gestAud, durAts]
              { if (!$atuacao.erroOut) {
                    info = $atuacao.infoOut;
                    gestAud = $atuacao.gestAudOut;
                    durAts = $atuacao.durAtsOut;
                } else {
                    erro = true; 
                }
              }
              
              (atuacao[info, gestAud, durAts] 
              { if (!$atuacao.erroOut) {
                    info = $atuacao.infoOut;
                    gestAud = $atuacao.gestAudOut;
                    durAts = $atuacao.durAtsOut;
                } else {
                    erro = true; 
                }
              }
              
              )* { if ((durAud > -1) && (durAts > 0)) {
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
            ;

dados [Dados infoIn]   
      returns [Dados infoOut, boolean erroOut]    
      @init { boolean erro = false; } 
            : 'DADOS' ('ATUACAO_BD' at1=ID { $infoIn.atuacao.add($at1.text); } 
              (',' at2=ID { if ($infoIn.atuacao.contains($at2.text)) { 
                                erro = true;
                                System.out.println("ERRO: A atuação com identificador "+$at2.text+" já foi definida!");
                            } else { $infoIn.atuacao.add($at2.text);} }
              )*';')?
              'INSTRUMENTOS_BD' inst1=ID { $infoIn.instr.add($inst1.text); }
              (',' inst2=ID { if ($infoIn.instr.contains($inst2.text)) {
                                erro = true;
                                System.out.println("ERRO: O instrumento com identificador "+$inst2.text+" já foi definido!");
                              } else { $infoIn.instr.add($inst2.text);} }
              )*';'
              'OBRA_BD' ob1=ID { $infoIn.obra.add($ob1.text); }
              (',' ob2=ID { if ($infoIn.obra.contains($ob2.text)) {
                                erro = true;
                                System.out.println("ERRO: A obra com identificador "+$ob2.text+" já foi definida!");
                            } else { $infoIn.obra.add($ob2.text);} }
              )*';'
              'PROFESSOR_BD' pr1=ID { $infoIn.prof.add($pr1.text); }
              (',' pr2=ID { if ($infoIn.prof.contains($pr2.text)) {
                                erro = true;
                                System.out.println("ERRO: O professor com identificador "+$pr2.text+" já foi definido!");
                            } else { $infoIn.prof.add($pr2.text);} }
              )*';'
              'ALUNO_BD' al1=ID { $infoIn.aluno.add($al1.text); }
              (',' al2=ID { if ($infoIn.aluno.contains($al2.text)) { 
                                erro = true;
                                System.out.println("ERRO: O aluno com identificador "+$al2.text+" já foi definido!");
                            } else { $infoIn.aluno.add($al2.text);} }        
              )* ';'
              { $erroOut = erro;
                $infoOut = $infoIn;
              }
            ;

bd [Dados infoIn, GestorAudicao gestAudIn]
   returns [Dados infoOut, GestorAudicao gestAudOut, boolean erroOut]
            : 'BD' caminhobd user passwd
              { boolean erro = false;
                $gestAudIn.abrirConexao($caminhobd.text, $user.text, $passwd.text);
                if ($gestAudIn.con != null) {
                    $gestAudIn.buscarDados("Atuacao", 2, $infoIn.atuacao);
                    $gestAudIn.buscarDados("Instrumento", 2, $infoIn.instr);
                    $gestAudIn.buscarDados("Obra", 2, $infoIn.obra);
                    $gestAudIn.buscarDados("Professor", 2, $infoIn.prof);
                    $gestAudIn.buscarDados("Aluno", 2, $infoIn.aluno);
                    $gestAudIn.buscarDados("Audicao", 2, $infoIn.audicao);
                } else {
                    System.out.println("ERRO: Problema a estabelecer ligação à base de dados!");
                    erro = true;
                }
                $erroOut = erro;
                $infoOut = $infoIn;
                $gestAudOut = $gestAudIn;
              }
            ;
caminhobd   : STRING
            ;
user        : STRING
            ;
passwd      : STRING
            ;
metaInfo [Dados infoIn, GestorAudicao gestAudIn]
         returns [Dados infoOut, GestorAudicao gestAudOut, boolean erroOut, int durAud]
            : 'CODIGO' id=ID ',' 'TITULO' tit=STRING ',' ('SUBTITULO' sub=STRING ',')? 'TEMA' t=STRING ','
              'DATA' dt=data ',' 'HORAINICIO' hini=hora ',' ( ('HORAFIM' hfim=hora | 'DURACAO' d=dur) ',')? 'LOCAL' l=STRING
              { boolean erro = false;
                String id = $id.text;
                $durAud = -1;
                int durH, durM;
                if ($infoIn.audicao.contains(id)) {
                    erro = true;
                    System.out.println("ERRO: A audição "+id+" já existe!");
                    $gestAudIn.audicao = new Audicao(null, null, null, null, null, null, null, null, null);
                } else {
                    String tit = retiraAspas($tit.text);
                    String sub = null;
                    if ($sub.text != null) {sub = retiraAspas($sub.text); }
                    String t = retiraAspas($t.text);
                    String dt = $dt.val;
                    String hini = $hini.val;
                    String hfim = null;
                    if ($hfim.text != null) { 
                        hfim = $hfim.val; 
                        int maior = $gestAudIn.horaMaior($hini.hr, $hini.min, $hfim.hr, $hfim.min);
                        if (maior == 1) {
                            System.out.println("AVISO: A hora de início é maior que a hora de fim!");
                        } 
                        /* Diferenca entre horas */
                        if ($hini.hr > $hfim.hr) {
                            durH = 24 - ($hini.hr - $hfim.hr);
                            
                            if ($hini.min < $hfim.min) {
                                durM = $hfim.min - $hini.min;
                            } else {
                                durH--;
                                durM = 60 - ($hini.min - $hfim.min);
                            }
                        } else if ($hini.hr < $hfim.hr)  {
                            durH = $hfim.hr - $hini.hr;
                            
                            if ($hini.min < $hfim.min) {
                                durM = $hfim.min - $hini.min;
                            } else {
                                durH--;
                                durM = 60 - ($hini.min - $hfim.min);
                            }
                        } else {
                            if ($hfim.min > $hini.min) {
                                durH = 0;
                                durM = $hfim.min - $hini.min;
                            } else {
                                durH = 23;
                                durM = 60 - ($hini.min - $hfim.min);
                            }
                        }
                        $durAud = (durH*60*60) + (durM*60);
                    }
                    String d = null;
                    if ($d.text != null) {
                        d = $d.val;
                        durH = $d.hr;
                        durM = $d.min;
                        $durAud = (durH*60*60) + (durM*60);
                    }
                    
                    String l = retiraAspas($l.text);
                    $gestAudIn.audicao = new Audicao(id, tit, sub, t, dt, hini, hfim, d, l);
                }
                
                $erroOut = erro;
                $infoOut = $infoIn;
                $gestAudOut = $gestAudIn;
              }  
            ;
data    
    returns [String val]
            : d=INT ('/'|'-'|'.') m=INT ('/'|'-'|'.') a=INT
              { boolean flag = false;
                if ($d.int < 1 || $d.int>31) {
                    flag = true;
                    System.out.println("ERRO: O dia da data "+$d.int+"/"+$m.int+"/"+$a.int+" não é válido!"); 
                }
                if ($m.int < 1 || $m.int>12) {
                    flag = true;
                    System.out.println("ERRO: O mês da data "+$d.int+"/"+$m.int+"/"+$a.int+" não é válido!"); 
                }
                if ($a.int < 1) {
                    flag = true;
                    System.out.println("ERRO: O ano da data "+$d.int+"/"+$m.int+"/"+$a.int+" não é válido!"); 
                } 
                if (!flag) {
                    $val = $d.text+"/"+$m.text+"/"+$a.text;
                } else {
                    $val = null;
                }
              }
            ;
hora 
    returns [String val, int hr, int min]
            : h=INT ':' m=INT
              { if (($h.int > 23) || ($m.int > 59)) {
                    System.out.println("ERRO: A hora "+$h.int+":"+$m.int+" não é válida!");
                    $val = null;
                } else {
                    $val = $h.text+":"+$m.text;    
                    $hr = $h.int;
                    $min = $m.int;
                }
              }
            ;

dur     
    returns [String val, int hr, int min]
            : h=INT ':' m=INT
             { if ($m.int > 59) {
                    System.out.println("ERRO: A duração "+$h.int+":"+$m.int+" não é válida!");
                    $val = null;
                } else {
                    $val = $h.text+":"+$m.text;    
                    $hr = $h.int;
                    $min = $m.int;
                }
              } 
            ;

atuacao [Dados infoIn, GestorAudicao gestAudIn, int durAtsIn]
        returns [Dados infoOut, GestorAudicao gestAudOut, boolean erroOut, int durAtsOut]
        @init { HashSet <String> i = new HashSet<>();
                HashSet <String> o = new HashSet<>();
                HashSet <String> p = new HashSet<>();
                HashSet <String> a = new HashSet<>();
                HashSet <String> ac = new HashSet<>();
                boolean erro = false;
              }    
            : 'ATUACAO' ID '.' 
              { if ($infoIn.atuacao.contains($ID.text) == false) {
                    erro = true;
                    System.out.println ("ERRO: A atuação "+$ID.text+" não existe!");
                } else {
                    $gestAudIn.audicao.addAtuacao($ID.text);
                    if ($gestAudIn.con != null){
                        try {
                            Statement st = $gestAudIn.con.createStatement();
                            ResultSet rs = st.executeQuery("SELECT duracao FROM Atuacao WHERE idAtuacao='"+$ID.text+"';");
                            rs.next();
                            $durAtsIn += Integer.parseInt(rs.getString(1));
                            rs.close();
                            st.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                $erroOut = erro;
                $infoOut = $infoIn;
                $gestAudOut = $gestAudIn;
                $durAtsOut = $durAtsIn;
              }
            | 'ATUACAO' 'CODIGO' idAt=ID ';'
              { if ($infoIn.atuacao.contains($idAt.text) == true) {
                    erro = true;
                    System.out.println ("ERRO: A atuação "+$idAt.text+" já existe!");
                } else {
                    $gestAudIn.audicao.addAtuacao($idAt.text);
                }
              }

              'INSTRUMENTOS' ID { i.add($ID.text); } (',' ID { i.add($ID.text); } )* ';'
              { boolean flag = false;
                for (String x: i) {
                    if (!$infoIn.instr.contains(x)) {
                        System.out.println ("ERRO: O instrumento "+x+" não existe!");
                        erro = true; flag = true;
                    }
                }
                if (!flag) {
                    $gestAudIn.audicao.instr.put($idAt.text, i);
                }
              }

              'OBRAS' ID { o.add($ID.text); } (',' ID { o.add($ID.text); } )* ';'
              { flag = false;
                for (String x: o) {
                    if (!$infoIn.obra.contains(x)) {
                        System.out.println ("ERRO: A obra "+x+" não existe!");
                        erro = true; flag = true;
                    } else if ($gestAudIn.con != null){
                        try {
                            Statement st = $gestAudIn.con.createStatement();
                            ResultSet rs = st.executeQuery("SELECT duracao FROM Obra WHERE idObra='"+x+"';");
                            rs.next();
                            $durAtsIn += Integer.parseInt(rs.getString(1));
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
                                Statement st = $gestAudIn.con.createStatement();
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
                    $gestAudIn.audicao.obras.put($idAt.text, o);
                }
              }

              'PROFESSORES' ID { p.add($ID.text); } (',' ID { p.add($ID.text); } )* ';'
              { flag = false;
                for (String x: p) {
                    if (!$infoIn.prof.contains(x)) {
                        System.out.println ("ERRO: O professor "+x+" não existe!");
                        flag = true; erro = true;
                    } else if ($gestAudIn.con != null){
                        if ($gestAudIn.temQualificacoes(x, i) == false) {
                            System.out.println ("AVISO: O professor "+x+" pode não ter qualificações para dirigir a atuação "+$ID.text+"!");
                        }
                    }
                }
                if (!flag) {
                    $gestAudIn.audicao.profs.put($idAt.text, p);
                }
              }

              'ALUNOS' ID { a.add($ID.text); } (',' ID { a.add($ID.text); } )*
              { flag = false;
                for (String x: a) {
                    if (!$infoIn.aluno.contains(x)) {
                        System.out.println ("ERRO: O aluno "+x+" não existe!");
                        erro = true; flag = true;
                    }
                }
                if (!flag) {
                    $gestAudIn.audicao.alunos.put($idAt.text, a);
                }
              }

              ('ACOMPANHADO' ID { ac.add($ID.text); } (',' ID { ac.add($ID.text); })*)? '.'
              { flag = false;
                for (String x: ac) {
                    if (!$infoIn.aluno.contains(x) && !$infoIn.prof.contains(x)) {
                        System.out.println ("ERRO: O acompanhante "+x+" não existe!");
                        erro = true; flag = true;
                    } 
                    if (a.contains(x)) {
                        System.out.println ("ERRO: O aluno "+x+" não pode ser acompanhante.");
                        erro = true; flag = true;
                    }
                }
                if (!flag) {
                    $gestAudIn.audicao.acomp.put($idAt.text, ac);
                }
                
                $erroOut = erro;
                $infoOut = $infoIn;
                $gestAudOut = $gestAudIn;
                $durAtsOut = $durAtsIn;
              }
            ;

/*--------------- Lexer ---------------------------------------*/
ID      : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'-')*
        ;

INT     : '0'..'9'+
        ;

WS      :   [ \t\r\n]  -> skip
        ;
    
STRING  :  '"' ( ESC_SEQ | ~('"') )* '"'
        ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;
 
fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;
fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') 
    ;
