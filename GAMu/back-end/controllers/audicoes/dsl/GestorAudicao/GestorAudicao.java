import java.util.*;
import java.sql.*;

public class GestorAudicao {
    Connection con;
    Audicao audicao;
    
    public GestorAudicao () {
        this.con = null;
        this.audicao = null;
    }
    
    public void abrirConexao (String caminho, String user, String pass) 
    {
        String url = new StringBuilder().append("jdbc:mysql://").append(caminho.substring(1, caminho.length()-1)).append("?useUnicode=yes&characterEncoding=UTF-8").toString();
        String userx = user.substring(1, user.length()-1);
        String passx = pass.substring(1, pass.length()-1);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.con = DriverManager.getConnection(url, userx, passx);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void buscarDados (String tabela, int coluna, HashSet<String> dataSet) 
    {
        try {
            Statement st = this.con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM "+tabela+";");

            while (rs.next()) {
                dataSet.add(rs.getString(coluna));
            }

            st.close();
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fecharConexao () 
    {
        try {
            if (this.con != null) { this.con.close(); }      
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int horaMaior (int h1, int m1, int h2, int m2) {
        int maior;

        if ((h1 > h2) || ((h1 == h2) && (m1 > m2))) { maior = 1;}
        else { maior = 2;}
        
        return maior;
    }
    
    public String somaDuracaoHora (int hora, int min, int durHora, int durMin) {
        int h = hora + durHora;
        int m = min + durMin;
        String val;

        if (m > 59) {
            h++;
            m = m - 60;
        }
        if (h > 23) {
            h = h - 24;
        }

        val = h+":"+m;
        return val;
    }

    public boolean temQualificacoes(String prof, HashSet <String> instr) {
        boolean flag = false;
        try { 
            HashSet<String> instrs = new HashSet<>();
            Statement st = this.con.createStatement();
            ResultSet hb= st.executeQuery("SELECT id, habilitacoes FROM Professor WHERE idProfessor =\""+prof+"\";");
            hb.next();
            String[] part = hb.getString(2).split(" em ", 2);
            hb = st.executeQuery("SELECT idInst FROM Instrumento WHERE nome =\""+part[1]+"\";");
            hb.next();
            instrs.add(hb.getString(1));

            String query = "SELECT idCurso FROM ProfessorCurso WHERE idProfessor=\""+hb.getString(1)+"\"";
            ResultSet cursos = st.executeQuery("SELECT idCurso FROM Curso WHERE id=("+query+");");

            for (String i: instrs) {
                if (instr.contains(i)) { flag = true; }  
            }

            hb.close();
            cursos.close();
            st.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return flag;        
        }
    }
}