package data_access;
import business.familias.IRepresentante;
import business.familias.RepresentanteFactory;
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

/**
 *
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 30.12.2014
 */
class RepresentanteDAO implements Map<Integer,IRepresentante> {

    public Connection conn;
    public MySQLParseTools parseTools;    

    public RepresentanteDAO() throws ConnectionErrorException{
        try {
            this.conn = (new MySQLManager()).getConnection();
            this.parseTools = new MySQLParseTools();
            } 
        catch (SQLException ex) {System.out.println("error_IRepresentante_bd");
            }
     
    
    }
    

    @Override
    @SuppressWarnings("empty-statement")
    public int size() {
        try {
            int i = 0;
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT Nr FROM IRepresentante");
            for (;rs.next();i++);
            return i;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public boolean isEmpty() {
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT Nr FROM IRepresentante");
            return rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}    
    }

    @Override
    public boolean containsKey(Object o) {
         try {
            Statement stm = conn.createStatement();
            String sql = "SELECT Nome FROM IRepresentante WHERE Nr='"+(int)o+"'";
            ResultSet rs = stm.executeQuery(sql);
            return rs.next();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }


    @Override
    public boolean containsValue(Object o) {
        try {
            if(o instanceof IRepresentante) {
            
                IRepresentante v = (IRepresentante)o;
                Statement stm = conn.createStatement();
                
                int N=this.size();
                for(int key=1; key<N; key++){
                    IRepresentante isv = this.get(key);
                    if(isv!=null){
                        if(v.equals(isv)) return true;
                    }
                }
                return false;
            } else return false;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
 
    @Override
    public IRepresentante get(Object o) {
        IRepresentante cand = new RepresentanteFactory().createRepresentante();
        try {
            Statement stm = conn.createStatement();
            String sql = "SELECT * FROM Representante WHERE nr='"+(int)o+"'";
            ResultSet rs = stm.executeQuery(sql);
            if (rs.next()){
                cand.setNr(rs.getInt(1));
                cand.setNome(rs.getString(2));
                cand.setDataNasc(parseTools.parseSQLDate(rs.getString(3)));
                cand.setEstadoCivil(rs.getString(4));
                cand.setProfissao(rs.getString(5));
                cand.setLocalidade(rs.getString(6));
                cand.setRua(rs.getString(7));
                cand.setCodPostal(rs.getString(8));
                cand.setNaturalidade(rs.getString(9));
                cand.setNacionalidade(rs.getString(10));
                cand.setEscolaridade(rs.getString(11));
                cand.setTelefone(rs.getString(12));
                cand.setTelemovel(rs.getString(13));
                cand.setRendimentoAgregado(rs.getFloat(14));
                return cand;
            }
        } catch (Exception e) {throw new NullPointerException(e.getMessage());}
    return cand;
    }
    
    

    @Override
    public IRepresentante put(Integer k, IRepresentante v) {
        try {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Representante WHERE Nr='"+k+"'");
            int i  = stm.executeUpdate(insert(k,v));
            IRepresentante c = new RepresentanteFactory().createRepresentante();
            c.setNr(c.getNr());
            c.setNome(c.getNome());
            c.setDataNasc(c.getDataNasc());
            c.setEstadoCivil(c.getEstadoCivil());
            c.setProfissao(c.getProfissao());
            c.setLocalidade(c.getLocalidade());
            c.setRua(c.getRua());
            c.setCodPostal(c.getCodPostal());
            c.setNaturalidade(c.getNaturalidade());
            c.setNacionalidade(c.getNacionalidade());
            c.setEscolaridade(c.getEscolaridade());
            c.setTelefone(c.getTelefone());
            c.setTelemovel(c.getTelemovel());
            c.setRendimentoAgregado(c.getRendimentoAgregado());
            return c;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    private String insert(Integer key, IRepresentante value) {
        List<Object> obj = new ArrayList<>();
        obj.add(value.getNr());
        obj.add(value.getNome());
        obj.add(parseTools.parseCalendar(value.getDataNasc()));
        obj.add(value.getEstadoCivil());
        obj.add(value.getProfissao());
        obj.add(value.getLocalidade());
        obj.add(value.getRua());
        obj.add(value.getCodPostal());
        obj.add(value.getNaturalidade());
        obj.add(value.getNacionalidade());
        obj.add(value.getEscolaridade());
        obj.add(value.getTelefone());
        obj.add(value.getTelemovel());
        obj.add(value.getRendimentoAgregado());
          
        return parseTools.createInsert("Representante", obj);
    }


    @Override
    public IRepresentante remove(Object o) {
       try {
            IRepresentante al = this.get(o);
            Statement stm = conn.createStatement();
            String sql = "DELETE '"+o+"' FROM IRepresentante";
            int i  = stm.executeUpdate(sql);
            return al;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }


    @Override
    public void putAll(Map<? extends Integer, ? extends IRepresentante> map) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
     public void clear () {
        try {
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Representante WHERE Representante.Nr>=0;");
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
    @Override
    public int hashCode() {return Arrays.hashCode(new Object[]{conn, parseTools});}


    @Override
    public Set<Integer> keySet() {
        try {
            Set<Integer> set = new HashSet<>();
            int n = this.generateRepresentanteKey();
            for(int i=0; i<n; i++){
                if(this.containsKey(i)){
                    set.add(i);
                }
            }
            return set;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }

    @Override
    public Collection<IRepresentante> values() {
       try {
            Set<IRepresentante> set = new HashSet<>();
            Set<Integer> keys = new HashSet<>(this.keySet());
            for(Integer key : keys)
                set.add(this.get(key));
            return set;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    } 

    @Override
    public Set<Entry<Integer, IRepresentante>> entrySet() {
     try {
            Set<Entry<Integer,IRepresentante>> set;
            Set<Integer> keys = new HashSet<>(this.keySet());            
            
            HashMap<Integer,IRepresentante> map = new HashMap<>();
            for(Integer key : keys){
                if(this.containsKey(key)){
                    map.put(key,this.get(key));
                }
            }
            return map.entrySet();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }   
    
    public int generateRepresentanteKey(){
       try {
            if(!this.isEmpty()){
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT MAX(Nr) FROM Representante;");
                if(rs.next()){
                    return(rs.getInt(1) + 1);
                }
            } else return 1;
        } catch (Exception e) {return 1;}
        
        return -1;
    }
    
    @Override
    public boolean equals(Object o) {
         if(this==o) return true;
        
        else if(this.getClass() != o.getClass()) return false;
        
        else{
             MembroDAO rdao = (MembroDAO) o;
            
            for(IRepresentante r : this.values()){
                if(!rdao.containsKey(r.getNr())) return false;
                else{
                    if(!r.equals(rdao.get(r.getNr()))) return false;
                }
            }
            return true;
        }
    }
    /**
     * Fechar a ligação à base de dados.
     */
        public void close(){
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(RepresentanteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
}