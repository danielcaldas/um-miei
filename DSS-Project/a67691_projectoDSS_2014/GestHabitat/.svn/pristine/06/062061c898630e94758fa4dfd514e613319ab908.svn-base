package data_access;

import business.recursoshumanos.IVoluntario;
import business.recursoshumanos.VoluntarioFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**Implementação de um Data Acess Object para gerir instancias da classe Voluntario.
 * 
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.12.29
 */

class VoluntarioDAO implements Map<Integer,IVoluntario> {

    public Connection conn;
    private MySQLParseTools parseTools;
    
    /**
     * Construtor que fornece conexão à tabela Voluntários na base de dados da Habitat.
     * @throws ConnectionErrorException 
     */
    public VoluntarioDAO() throws ConnectionErrorException{
        try {
            parseTools = new MySQLParseTools();
            this.conn = (new MySQLManager()).getConnection();
        } catch (SQLException ex) {System.out.println("error_voluntarios_bd");}
    }
    
    @Override
    @SuppressWarnings("empty-statement")
    public int size() {
        try {
            int i = 0;
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT Nr FROM Voluntarios");
            for (;rs.next();i++);
            return i;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public boolean isEmpty() {
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT Nome FROM Voluntarios");
            return !rs.next();
        } catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public boolean containsKey(Object key) {
        try {
            Statement stm = conn.createStatement();
            String sql = "SELECT Nome FROM Voluntarios WHERE Nr="+(int)key+";";
            ResultSet rs = stm.executeQuery(sql);
            return rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public boolean containsValue(Object value) {
        try {
            if(value instanceof IVoluntario) {
            
                IVoluntario v = (IVoluntario)value;
                Statement stm = conn.createStatement();
                
                for(Integer key : this.keySet()){
                     if(v.equals(this.get(key))) return true;
                }
                return false;
            } else return false;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public IVoluntario get(Object key) {
        VoluntarioFactory vfactory = new VoluntarioFactory();
        IVoluntario v = vfactory.createVoluntario();
        
        try {
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM Voluntarios WHERE Nr="+(int)key+";";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()){
                String r=null;
                v = vfactory.createVoluntario();
                
                v.setNr(parseTools.getInteger(rs,"Nr"));
                
                r=rs.getString(2);
                if(r!=null) v.setNome(r);
                else v.setNome("");
                                
                r=rs.getString(3);
                if(r!=null) v.setDatanasc(parseTools.parseSQLDate(r));
                else v.setDatanasc(new GregorianCalendar());                
                
                v.setLocalidade(rs.getString(4));
                v.setRua(rs.getString(5));
                v.setCodPostal(rs.getString(6));
                v.setHabilitacoes(rs.getString(7));
                v.setTelem(rs.getString(8));
                v.setTelef(rs.getString(9));
                v.setProfissao(rs.getString(10));
                v.setEmail(rs.getString(11));
                v.setObs(rs.getString(12));
                v.setDataInicioVol(parseTools.parseSQLDate(rs.getString(13)));

                List<String> linguas = new ArrayList<>();
                sql = "SELECT * FROM Linguas WHERE Voluntario="+(int)key+";";
                rs = stm.executeQuery(sql);
                for(;rs.next();){
                    linguas.add(rs.getString(2));
                }
                v.setLinguas(linguas);

                Map<Integer,Integer> horas = new HashMap<>();
                sql = "SELECT Projeto, HorasVoluntariado FROM ProjetosVoluntarios Where Voluntario="+(int)key+";";
                rs = stm.executeQuery(sql);
                for(;rs.next();){
                    horas.put(parseTools.getInteger(rs,"Projeto"),parseTools.getInteger(rs,"HorasVoluntariado"));
                }
                v.setHorasProjetos(horas);
                
                return v;
            }
        } catch (Exception e) {throw new NullPointerException("cenas");}
        
        return v;
    }

    @Override
    public IVoluntario put(Integer key, IVoluntario value) {
        try {            
            Statement stm = conn.createStatement();
            this.remove(key);
            int i  = stm.executeUpdate(insert(key,value));
            
            for(String l : value.getLinguas()){
                if(l.equals("")) break;
                String sql = ("INSERT INTO Linguas (Nome, Voluntario) values ('"+l+"', "+key+");");
                stm.executeUpdate(sql);
            }
            
            if(value.getHorasVoluntariado()>0){
                for(Map.Entry<Integer,Integer> entry : value.getHorasProjetos().entrySet()){
                    String sql = ("INSERT INTO ProjetosVoluntarios (Projeto, Voluntario, HorasVoluntariado)"
                            + " values ("+entry.getKey()+", "+key+", "+entry.getValue()+");");
                    stm.executeUpdate(sql);
                }
            }
                
            return value;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    /*Método auxiliar de inserção na base de dados*/
    private String insert(Integer key, IVoluntario value) throws SQLException{       
        String datanasc;
        if(value.getDatanasc()!=null){
            datanasc = parseTools.parseCalendar(value.getDatanasc());
        } else datanasc="NULL";
        
        String datainic;
        if(value.getDataInicioVol()!=null){
            datainic = parseTools.parseCalendar(value.getDataInicioVol());
        } else datainic="NULL";
        
        ArrayList<Object> valores = new ArrayList<>();
        valores.add(key);
        
        if(value.getNome()!=null) valores.add(value.getNome());
        else valores.add("NULL");        
        
        valores.add(datanasc);
        
        if(value.getLocalidade()!=null) valores.add(value.getLocalidade());
        else valores.add("NULL");
        
        if(value.getRua()!=null) valores.add(value.getRua());
        else valores.add("NULL");
        
        if(value.getCodPostal()!=null) valores.add(value.getCodPostal());
        else valores.add("NULL");
        
        if(value.getHabilitacoes()!=null) valores.add(value.getHabilitacoes());
        else valores.add("NULL");
        
        if(value.getTelem()!=null) valores.add(value.getTelem());
        else valores.add("NULL");
        
        if(value.getTelef()!=null) valores.add(value.getTelef());
        else valores.add("NULL");
        
        if(value.getProfissao()!=null) valores.add(value.getProfissao());
        else valores.add("NULL");
        
        if(value.getEmail()!=null) valores.add(value.getEmail());
        else valores.add("NULL");
        
        if(value.getObs()!=null) valores.add(value.getObs());
        else valores.add("NULL");
        
        valores.add(datainic);
        
        Statement stm = conn.createStatement();
        String sql = ("SELECT Equipa FROM Voluntarios WHERE Nr="+key+";");
        ResultSet rs = stm.executeQuery(sql);
        if(rs.next()) valores.add(rs.getInt(1));
        else valores.add("NULL");
        
        return parseTools.createInsert("Voluntarios", valores);
    }

    @Override
    public IVoluntario remove(Object key) {
        try {            
            Statement stm = conn.createStatement();
            IVoluntario v = this.get((int)key);
            stm.executeUpdate("DELETE FROM Linguas WHERE Voluntario="+(int)key+";");
            stm.executeUpdate("update equipa set chefe=null where chefe="+(int)key+";");
            stm.executeUpdate("delete from projetosvoluntarios where voluntario="+(int)key+";");
            stm.executeUpdate("DELETE FROM Voluntarios WHERE Nr="+key+";");
            return new VoluntarioFactory().createVoluntario();
        }
        catch (Exception e) {throw new NullPointerException();}
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends IVoluntario> m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        try {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Voluntarios WHERE Voluntarios.Nr>=0;");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    /*ALTERAR*/
    @Override
    public Set<Integer> keySet() {
        try {
            Set<Integer> set = new HashSet<>();
            int n = this.generateVoluntarioKey();
            for(int i=0; i<n; i++){
                if(this.containsKey(i)){
                    set.add(i);
                }
            }
            return set;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    /*ALTERAR*/
    @Override
    public Collection<IVoluntario> values() {
        try {
            Set<IVoluntario> set = new HashSet<>();
            Set<Integer> keys = new HashSet<>(this.keySet());
            for(Integer key : keys)
                set.add(this.get(key));
            return set;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    /*ALTERAR*/
    @Override
    public Set<Entry<Integer,IVoluntario>> entrySet() {
        try {
            Set<Entry<Integer,IVoluntario>> set;
            Set<Integer> keys = new HashSet<>(this.keySet());            
            
            HashMap<Integer,IVoluntario> map = new HashMap<>();
            for(Integer key : keys){
                if(this.containsKey(key)){
                    map.put(key,this.get(key));
                }
            }
            return map.entrySet();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        
        else if(this.getClass() != o.getClass()) return false;
        
        else{
            VoluntarioDAO vdao = (VoluntarioDAO) o;
            
            for(IVoluntario v : this.values()){
                if(!vdao.containsKey(v.getNr())) return false;
                else{
                    if(!v.equals(vdao.get(v.getNr()))) return false;
                }
            }
            return true;
        }
    }

    @Override
    public int hashCode() {
        int hash = Arrays.hashCode(new Object[]{conn, parseTools});
        for(IVoluntario v : this.values())
            hash+=v.hashCode();
        return hash;
    }
    
    /*ALTERAR*/
    /**
     * Procura a maior chave de voluntário existente na base de dados e retorna
     * esse valor incrementado em uma unidade
     * @return Chave que identificará univocamente no sistema um voluntário. 
     */
    public int generateVoluntarioKey(){
       try {
            if(!this.isEmpty()){
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT MAX(Nr) FROM Voluntarios;");
                if(rs.next()){
                    return(rs.getInt(1) + 1);
                }
            } else return 1;
        } catch (Exception e) {return 1;}
        
        return -1;
    }
    
    /**
     * Selecionar voluntarios de uma determinada equipa ou sem equipa. (depende do input)
     * @param id, id da equipa
     * @return se id==-1 retorna voluntarios sem equipa, caso contrário retorna voluntarios da equipa 'id'
     */
    public Collection<IVoluntario> getVoluntariosDeEquipa(int id) {
        Collection<IVoluntario> vols = new HashSet<>();
        VoluntarioFactory vfactory = new VoluntarioFactory();
        IVoluntario v;
        
        try {
            Statement stm = conn.createStatement();
            String sql;
            if(id!=-1){
                sql = "SELECT * FROM Voluntarios WHERE Equipa="+id+";";
            }else{
                sql = "SELECT * FROM Voluntarios WHERE Equipa IS NULL;";
            }
            ResultSet rs = stm.executeQuery(sql);            
            while(rs.next()){
                v = vfactory.createVoluntario();
                v.setNr(rs.getInt(1));
                v.setNome(rs.getString(2));
                v.setDatanasc(parseTools.parseSQLDate(rs.getString(3)));
                v.setLocalidade(rs.getString(4));
                v.setRua(rs.getString(5));
                v.setCodPostal(rs.getString(6));
                v.setHabilitacoes(rs.getString(7));
                v.setTelem(rs.getString(8));
                v.setTelef(rs.getString(9));
                v.setProfissao(rs.getString(10));
                v.setEmail(rs.getString(11));
                v.setObs(rs.getString(12));
                v.setDataInicioVol(parseTools.parseSQLDate(rs.getString(13)));
                
                List<String> linguas = new ArrayList<>();
                sql = "SELECT * FROM Linguas WHERE Voluntario="+(int)v.getNr()+";";
                rs = stm.executeQuery(sql);
                for(;rs.next();){
                    linguas.add(rs.getString(2));
                }
                v.setLinguas(linguas);
                
                Map<Integer,Integer> horas = new HashMap<>();
                sql = "SELECT Projeto, HorasVoluntariado FROM HorasVoluntariado Where Voluntario="+v.getNr()+";";
                rs = stm.executeQuery(sql);
                for(;rs.next();){
                    horas.put(rs.getInt(1),rs.getInt(2));
                }
                v.setHorasProjetos(horas);
                
                vols.add(v);
            }
            return vols;
        } catch (Exception e) {throw new NullPointerException();}
    }
    
    /**
     * Inserir horas de voluntariado num dado voluntário;
     * @param nrproj id do proejto em que o voluntário trabalhou
     * @param vid id do voluntário
     * @param h nº de horas de trabalho
     */
    public void addHorasVoluntariado(int nrproj, int vid, int h) {
        try {            
            Statement stm = conn.createStatement();
            String sql=("SELECT HorasVoluntariado FROM ProjetosVoluntarios"
                    + " WHERE Voluntario="+vid+" AND Projeto="+nrproj+";");
            
            ResultSet rs = stm.executeQuery(sql);
            
            if(!rs.next()){
                sql = ("INSERT ProjetosVoluntarios (Projeto,Voluntario,HorasVoluntariado)"
                        + " values("+nrproj+","+vid+","+h+");");          
            } else{
                h+=rs.getInt(1); 
                sql = ("UPDATE ProjetosVoluntarios SET HorasVoluntariado="+h
                        + " WHERE Voluntario="+vid+" AND Projeto="+nrproj+";");
            }
            stm.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(VoluntarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Fechar a ligação à base de dados.
     */
    public void close(){
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(VoluntarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
