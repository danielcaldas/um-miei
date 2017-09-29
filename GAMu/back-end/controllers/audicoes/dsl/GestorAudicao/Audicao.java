import java.util.*;
import java.io.*;
import java.sql.*;

public class Audicao {
    String codigo, titulo, subtitulo, tema;
    String horaI, horaF, dur, data, local;
    HashSet<String> atuacoes;
    HashMap<String, HashSet<String>> instr; //<idAtuacao, idsInstrumentos> 
    HashMap<String, HashSet<String>> obras;
    HashMap<String, HashSet<String>> profs;
    HashMap<String, HashSet<String>> alunos; 
    HashMap<String, HashSet<String>> acomp; 
    
    public Audicao (String cod, String tit, String subTit, String tema, String dt, String hi, String hf, String dur, String loc) {
        this.codigo = cod; 
        this.titulo = tit; 
        this.subtitulo = subTit; 
        this.tema = tema; 
        this.horaI = hi; 
        this.horaF = hf; 
        this.dur = dur; 
        this.data = dt; 
        this.local = loc; 
        this.atuacoes = new HashSet<>();
        this.instr = new HashMap<>();
        this.obras = new HashMap<>();
        this.profs = new HashMap<>();
        this.alunos = new HashMap<>();
        this.acomp = new HashMap<>();
    }
 
    public void addAtuacao (String id) {
        atuacoes.add(id);
    }
    
    private File testaExiste (String nome, String extensao) {
        File fich = new File("./FicheirosGerados/"+nome+extensao);
        int cont = 2;
        if (fich.exists()) {
            fich = new File("./FicheirosGerados/"+nome+"_v"+cont+extensao);
            while(fich.exists()) {
                cont++;
                fich = new File("./FicheirosGerados/"+nome+"_v"+cont+extensao);
            }  
        }
        return fich;
    }
    
    public void criaXML() {      
        /* ---------------Ficheiro Audicao---------------------- */
        try {
            File nomesFich = new File("files.txt"); //lista ficheiros gerados
            if (nomesFich.exists()) {nomesFich.delete();}
            nomesFich.createNewFile();
            nomesFich.setExecutable(true);
            nomesFich.setReadable(true);
            nomesFich.setWritable(true);
            PrintWriter fich_nomes = new PrintWriter(new FileWriter(nomesFich.getAbsoluteFile(),true));
            
            File fichXML = testaExiste("audicao_"+this.codigo, ".xml");
            fichXML.createNewFile();
            fichXML.setExecutable(true);
            fichXML.setReadable(true);
            fichXML.setWritable(true);
            PrintWriter fich_audXML = new PrintWriter(new FileWriter(fichXML.getAbsoluteFile(),true));
                        
            /* ------------------------ META INFO ------------------------ */
            fich_audXML.println("<audicao id="+this.codigo+">");
            fich_audXML.println("\t<titulo>"+this.titulo+"</titulo>");
            if (this.subtitulo != null) {
                fich_audXML.println("\t<subtitulo>"+this.subtitulo+"</subtitulo>");
            }
            fich_audXML.println("\t<tema>"+this.tema+"</tema>");
            fich_audXML.println("\t<data>"+this.data+"</data>");
            fich_audXML.println("\t<horaini>"+this.horaI+"</horaini>");
            if (this.horaF != null) {
                fich_audXML.println("\t<horafim>"+this.horaF+"</horafim>");
            }
            fich_audXML.println("\t<local>"+this.local+"</local>");
         
            /* ------------------------ ATUACOES ------------------------ */
            fich_audXML.println("\t<atuacoes>");
            
            for(String at: this.atuacoes) {
                fich_audXML.println("\t\t<atuacao>"+at+"</atuacao>");
                if (this.alunos.containsKey(at)){
                    String f = geraXML_atuacao(at);
                    fich_nomes.println(f);
                }
            }
            fich_audXML.println("\t</atuacoes>");
            fich_audXML.println("</audicao>");
            
            fich_audXML.close();
            System.out.println("Ficheiro "+fichXML.getName()+" criado (ver pasta \"FicheirosGerados\")!");
            fich_nomes.println(fichXML.getName());
            fich_nomes.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private String geraXML_atuacao(String idAt){
        try {
            File fichXML = testaExiste("atuacao_"+idAt, ".xml");
            fichXML.createNewFile();
            fichXML.setExecutable(true);
            fichXML.setReadable(true);
            fichXML.setWritable(true);
            PrintWriter fich_atu = new PrintWriter(new FileWriter(fichXML.getAbsoluteFile(),true));
            
            fich_atu.println("<atuacao id=\""+idAt+"\">");
            fich_atu.println("\t<instrumentos>");
            for (String inst: this.instr.get(idAt)) {
                fich_atu.println("\t\t<instrumento>"+inst+"</instrumento>");
            }
            fich_atu.println("\t</instrumentos>");
            fich_atu.println("\t<obras>");
            for (String ob: this.obras.get(idAt)) {
                fich_atu.println("\t\t<obra>"+ob+"</obra>");
            }
            fich_atu.println("\t</obras>");
            fich_atu.println("\t<alunos>");
            for (String alu: this.alunos.get(idAt)) {
                fich_atu.println("\t\t<aluno acompanhante=\"0\">"+alu+"</aluno>");
            }
            fich_atu.println("\t</alunos>");
            fich_atu.println("\t<acompanhantes>");
            for (String aco: this.acomp.get(idAt)) {
                fich_atu.println("\t\t<acompanhante acompanhante=\"1\">"+aco+"</acompanhante>");
            }
            fich_atu.println("\t</acompanhantes>");
            fich_atu.println("</atuacao>");
            
            fich_atu.close();
            System.out.println("Ficheiro "+fichXML.getName()+" criado (ver pasta \"FicheirosGerados\")!");
            
            return(fichXML.getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public void criaHTML (Connection con) {
        try {  
            File fichHTML = testaExiste("audicao_"+this.codigo, ".html");
            fichHTML.createNewFile();
            fichHTML.setExecutable(true);
            fichHTML.setReadable(true);
            fichHTML.setWritable(true);
            
            /* ------------------------ INICIO HTML ------------------------ */
            InputStream inStream = new FileInputStream("GestorAudicao/inicio.txt");
            OutputStream outStream = new FileOutputStream(fichHTML);
            byte[] buffer = new byte[1024];

            int length;
            while ((length = inStream.read(buffer)) > 0){
                outStream.write(buffer, 0, length);
            }
            if (inStream != null) inStream.close();
            if (outStream != null) outStream.close();

            /* ------------------------ META INFO ------------------------ */
            PrintWriter fich_audHTML = new PrintWriter(new FileWriter(fichHTML.getAbsoluteFile(),true));
            fich_audHTML.println("\t\t<title>"+this.titulo+"</title>");
            fich_audHTML.println("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html;charset=iso-8859-1\"/>");
            fich_audHTML.println("\t</head>\n\t<body>\n\t\t<div id=\"container\">\n\t\t\t<div id=\"page_title\">");
            fich_audHTML.println("\t\t\t\t<h1>"+this.titulo+"</h1>\n\t\t\t</div>");
            fich_audHTML.println("\t\t\t<div id=\"left_column\">\n\t\t\t\t<div id=\"page_image\">&nbsp;</div>\n\t\t\t</div>");
            fich_audHTML.println("\t\t\t<div id=\"right_column\" style=\"overflow-y: auto;\">");
            fich_audHTML.println("\t\t\t\t<div id=\"audicao\" style=\"text-align: center;\">");
            
            if (this.subtitulo != null) {
                fich_audHTML.println("\t\t\t\t\t<h2>"+this.subtitulo+"</h2>\n\t\t\t\t\t<br/>");
            }
            fich_audHTML.println("\t\t\t\t\t<h2>"+this.tema+"</h2>\n\t\t\t\t\t<br/>");
            fich_audHTML.println("\t\t\t\t\t<h2>"+this.data+"</h2>\n\t\t\t\t\t<br/>");
            fich_audHTML.println("\t\t\t\t\t<h2>"+this.horaI+"</h2>\n\t\t\t\t\t<br/>");
            fich_audHTML.println("\t\t\t\t\t<h2>"+this.local+"</h2>\n\t\t\t\t\t<br/>");
            fich_audHTML.println("\t\t\t\t\t<br/>\n\t\t\t\t\t<h2>Programa</h2>");
            fich_audHTML.println("\t\t\t\t\t<br/>\n\t\t\t\t</div>");
            
            /* ------------------------ ATUACOES ------------------------ */
            fich_audHTML.println("\t\t\t\t<div id=\"atuacao\" style=\"margin-left: 2em;\">");
            fich_audHTML.println("\t\t\t\t\t<ul>");

            for(String at: this.atuacoes) {
                fich_audHTML.println("\t\t\t\t\t<li>");
                Statement st = con.createStatement();
                ResultSet rs = null;
                
                if (this.alunos.containsKey(at)){
                    st = con.createStatement();
                    
                    fich_audHTML.print("\t\t\t\t\t\t<p>");
                    for(String id: this.alunos.get(at)) {
                        rs = st.executeQuery("SELECT nome FROM Aluno WHERE idAluno='"+id+"';");
                        rs.next();
                        fich_audHTML.print(" "+rs.getString(1)+",");
                    }
                    fich_audHTML.println("\t\t\t\t\t\t</p>");
                    
                    fich_audHTML.print("\t\t\t\t\t\t<p><b>Interpreta(m):</b> ");
                    for(String id: this.obras.get(at)) {
                        rs = st.executeQuery("SELECT nome, idCompositor FROM Obra WHERE idObra='"+id+"';");
                        rs.next();
                        fich_audHTML.print(" "+rs.getString(1));
                        rs = st.executeQuery("SELECT nome FROM Compositor WHERE id='"+rs.getString(2)+"';");
                        rs.next();
                        fich_audHTML.print("("+rs.getString(1)+"),");
                    }
                    fich_audHTML.println("\t\t\t\t\t\t</p>");
                    
                    fich_audHTML.print("\t\t\t\t\t\t<p><b>A dirigir:</b> ");
                    for(String id: this.profs.get(at)) {
                        rs = st.executeQuery("SELECT nome FROM Professor WHERE idProfessor='"+id+"';");
                        rs.next();
                        fich_audHTML.print(" Professor "+rs.getString(1)+",");
                    }
                    fich_audHTML.println("\t\t\t\t\t\t</p>");
                    
                    fich_audHTML.print("\t\t\t\t\t\t<p><b>A acompanhar:</b> ");
                    for(String id: this.acomp.get(at)) {
                        if (id.startsWith("P")) {
                            rs = st.executeQuery("SELECT nome FROM Professor WHERE idProfessor='"+id+"';");
                            rs.next();
                            fich_audHTML.print(" Professor "+rs.getString(1)+",");
                        } else {
                            rs = st.executeQuery("SELECT nome FROM Aluno WHERE idAluno='"+id+"';");
                            rs.next();
                            fich_audHTML.print(" "+rs.getString(1)+",");
                        }  
                    }
                    fich_audHTML.println("\t\t\t\t\t\t</p>");
                    
                    fich_audHTML.print("\t\t\t\t\t\t<p>(");
                    for(String id: this.instr.get(at)) {
                        rs = st.executeQuery("SELECT nome FROM Instrumento WHERE idInst='"+id+"';");
                        rs.next();
                        fich_audHTML.print(" "+rs.getString(1)+",");
                    }
                    fich_audHTML.print("\t\t\t\t\t\t)</p>\n");
                    
                    
                } else {
                    /* Caso atuacao ja exista na BD */
                    st = con.createStatement();
                    
                    rs = st.executeQuery("SELECT id FROM Atuacao WHERE idAtuacao='"+at+"';");
                    rs.next();
                    String idAt = rs.getString(1);
                    
                    fich_audHTML.print("\t\t\t\t\t\t<p>");
                    rs = st.executeQuery("SELECT Aluno.idAluno, Aluno.nome FROM AtuacaoAluno "+
                            "INNER JOIN Aluno ON Aluno.id=AtuacaoAluno.idAluno "+
                            "WHERE AtuacaoAluno.acompanhante=0 and AtuacaoAluno.idAtuacao="+idAt+" order by Aluno.nome;");
                    while (rs.next()) {
                        fich_audHTML.print(" "+rs.getString(2)+",");
                    }
                    fich_audHTML.println("\t\t\t\t\t\t</p>");
                    
                    fich_audHTML.print("\t\t\t\t\t\t<p><b>Interpreta(m):</b> ");
                    rs = st.executeQuery("SELECT Obra.idObra, Obra.nome, Compositor.idComp, Compositor.nome FROM AtuacaoObra "+
                            "INNER JOIN Obra ON AtuacaoObra.idObra=Obra.id "+
                            "INNER JOIN Compositor ON Obra.idCompositor=Compositor.id "+
                            "WHERE AtuacaoObra.idAtuacao="+idAt+";");
                    while (rs.next()) {
                        fich_audHTML.print(" "+rs.getString(2)+" ("+rs.getString(4)+"),");
                    }
                    fich_audHTML.println("\t\t\t\t\t\t</p>");
                    
                    fich_audHTML.print("\t\t\t\t\t\t<p><b>A dirigir:</b> ");
                    rs = st.executeQuery("SELECT Professor.idProfessor, Professor.nome FROM AtuacaoProfessor "+
                            "INNER JOIN Professor ON Professor.id=AtuacaoProfessor.idProfessor "+
                            "WHERE AtuacaoProfessor.acompanhante=0 and AtuacaoProfessor.idAtuacao="+idAt+" order by Professor.nome;");
                    while (rs.next()) {
                        fich_audHTML.print(" "+rs.getString(2)+",");
                    }
                    fich_audHTML.println("\t\t\t\t\t\t</p>");
                    
                    fich_audHTML.print("\t\t\t\t\t\t<p><b>A acompanhar:</b> ");
                    rs = st.executeQuery("SELECT Professor.idProfessor, Professor.nome FROM AtuacaoProfessor "+
                            "INNER JOIN Professor ON Professor.id=AtuacaoProfessor.idProfessor "+
                            "WHERE AtuacaoProfessor.acompanhante=1 and AtuacaoProfessor.idAtuacao="+idAt+" order by Professor.nome;");
                    while (rs.next()) {
                        fich_audHTML.print(" Professor "+rs.getString(2)+",");
                    }                    
                    rs = st.executeQuery("SELECT Aluno.idAluno, Aluno.nome FROM AtuacaoAluno "+
                            "INNER JOIN Aluno ON Aluno.id=AtuacaoAluno.idAluno "+
                            "WHERE AtuacaoAluno.acompanhante=0 and AtuacaoAluno.idAtuacao="+idAt+" order by Aluno.nome;");
                    while (rs.next()) {
                        fich_audHTML.print(" "+rs.getString(2)+",");
                    }
                    fich_audHTML.println("\t\t\t\t\t\t</p>");
                    
                    fich_audHTML.print("\t\t\t\t\t\t<p>(");
                    rs = st.executeQuery("SELECT Aluno.instrumento FROM AtuacaoAluno "+
                            "INNER JOIN Aluno ON Aluno.id=AtuacaoAluno.idAluno "+
                            "WHERE AtuacaoAluno.acompanhante=0 and AtuacaoAluno.idAtuacao="+idAt+" order by Aluno.nome;");
                    while (rs.next()) {
                        fich_audHTML.print(" "+rs.getString(1)+",");
                    }
                    fich_audHTML.print("\t\t\t\t\t\t)</p>\n");
                }
                st.close();
                rs.close();
                fich_audHTML.println("\t\t\t\t\t<li>");
                fich_audHTML.println("\t\t\t\t\t<br/><hr/>");
            }
            fich_audHTML.println("\t\t\t\t\t</ul>");
            fich_audHTML.println("\t\t\t\t</div>");
            
            /* ------------------------ FIM HTML ------------------------ */
            fich_audHTML.println("\t\t\t</div>");
            fich_audHTML.println("\t\t</div>");
            fich_audHTML.println("\t\t<div id=\"page_footer\">Web Design by GAMu</div>");
            fich_audHTML.println("\t</body>\n</html>");
            
            fich_audHTML.close();
            System.out.println("Ficheiro "+fichHTML.getName()+" criado (ver pasta \"FicheirosGerados\")!");
            File nomesFich = new File("files.txt");
            PrintWriter fich_nomes = new PrintWriter(new FileWriter(nomesFich.getAbsoluteFile(),true));
            fich_nomes.println(fichHTML.getName());
            fich_nomes.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}