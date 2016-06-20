package data_access;

import business.doacoes.EventoFactory;
import  business.doacoes.IEvento;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

    /**
    * Implementação de um Data Acess Object para gerir instancias da classe Doador.
    * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
    * @version 2015.01.04
    */
class EventoDAO implements Map<Integer,IEvento> {

        public Connection conn;
        public MySQLParseTools parseTools;

       /** 
        * Construtor que fornece conexão à tabela Eventos na base de dados da Habitat
        * @throws ConnectionErrorException 
        */
        public EventoDAO () throws ConnectionErrorException {
            try {
                parseTools = new MySQLParseTools();
                this.conn = (new MySQLManager()).getConnection();
            } catch (SQLException e) {System.out.println ("error_eventos_bd");}
        }

        @Override
        public void clear() {
            try {
                Statement stm = conn.createStatement();
                stm.executeUpdate("DELETE FROM Eventos WHERE Nr>=0");
            }
            catch (Exception e) {throw new NullPointerException(e.getMessage());}
        }

        @Override
        public boolean containsKey(Object key) {
            try {
                Statement stm = conn.createStatement();
                String sql = "SELECT Nome FROM Eventos WHERE Nr='"+(int)key+"'";
                ResultSet rs = stm.executeQuery(sql);
                return rs.next();
            }
            catch (Exception e) {throw new NullPointerException(e.getMessage());}
        }

        @Override
        public boolean containsValue(Object value) {
            try {
                if(value instanceof IEvento) {

                    IEvento e = (IEvento)value;
                    Statement stm = conn.createStatement();

                    Set<Integer> chaves = this.keySet();
                    for(int i: chaves){
                        IEvento ie = this.get(i);
                        if(ie!=null){
                            if(e.equals(ie)) return true;
                        }
                    }
                    return false;
                } else return false;
            }
            catch (Exception e) {throw new NullPointerException(e.getMessage());}
        }

        @Override
        public Set<Entry<Integer,IEvento>> entrySet() {
        try {
            Set<Entry<Integer,IEvento>> set;
            Set<Integer> keys = new HashSet(this.keySet());            
            
            HashMap<Integer,IEvento> map = new HashMap();
            for(Integer key : keys){
                if(this.containsKey(key)){
                    map.put(key,this.get(key));
                }
            }
            return map.entrySet();
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
        
        public IEvento get(Object key) {
            IEvento ev = new EventoFactory().createEvento();

            try {
                float total =  0;
                Statement stm = conn.createStatement();
                String sql = "SELECT  FROM Eventos WHERE Nr="+(int)key;
                ResultSet rs = stm.executeQuery(sql);
                if (rs.next()){
                    ev = new EventoFactory().createEvento();
                    ev.setNr(rs.getInt("Nr"));
                    ev.setDesignacao(rs.getString("Nome"));
                    ev.setNrPessoas(rs.getInt("NrPessoas"));
                    ev.setDataRealizacao(parseTools.parseSQLDate(rs.getString("DataRealizacao")));
                    ev.setNotas(rs.getString("Notas"));
                    
                    Set<Integer> donativos = new HashSet<>();
                    sql = "Select NrRecibo, Valor from Donativos WHERE Evento='"+ev.getNr()+"'";
                    rs = stm.executeQuery(sql);
                    while (rs.next())
                    {
                        donativos.add(rs.getInt("NrRecibo"));
                        total += rs.getFloat("Valor");
                    }
                    ev.setDonativos(donativos);
                    ev.setTotalAngariado(total);
         
                    return ev;
                }
            } catch (Exception e) {throw new NullPointerException(e.getMessage());}

            return ev;
        }

        @Override
        public boolean isEmpty() {
            try {
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT Nome FROM Eventos");
                return !rs.next();
            }
            catch (Exception e) {throw new NullPointerException(e.getMessage());}
        }

        @Override
        public Set<Integer> keySet() {
        try {
            Set<Integer> set = new HashSet();
            int n = this.generateEventoKey();
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
        public IEvento put(Integer key, IEvento value) 
        {
            try {            
                Statement stm = conn.createStatement();
                stm.executeUpdate("DELETE FROM Eventos WHERE Nr='"+key+"'");
                int i  = stm.executeUpdate(insert(key,value));
                IEvento e = new EventoFactory().createEvento();
                e.setNr(value.getNr());
                e.setDesignacao(value.getDesignacao());
                e.setDataRealizacao(value.getDataRealizacao());
                e.setNrPessoas(value.getNrPessoas());
                e.setNotas(value.getNotas());
                return e;
            }
            catch (Exception ex) {throw new NullPointerException(ex.getMessage());}
        }
        
        /*Método auxiliar de inserção na base de dados*/
        private String insert(Integer key, IEvento value)
        {       
            String dataR = parseTools.parseCalendar(value.getDataRealizacao());
        
            ArrayList<Object> valores = new ArrayList();
            valores.add(key);
            valores.add(value.getDesignacao());
            valores.add(value.getNrPessoas());
            valores.add(dataR);
            valores.add(value.getNotas());
        
            String sql = parseTools.createInsert("Eventos", valores);
            return sql;
        }
    

        @Override
        public void putAll(Map<? extends Integer, ? extends IEvento> m) {
            throw new UnsupportedOperationException("Not supported yet."); 
        }

        @Override 
        public IEvento remove(Object key) {
            try {
                IEvento ev = this.get(key);
                Statement stm = conn.createStatement();
                String sql = "DELETE FROM Doadores WHERE Id =" +(int)key;
                int i  = stm.executeUpdate(sql);
                return ev;
            }
            catch (Exception e) {throw new NullPointerException(e.getMessage());}
        }
        /**
        * @Override
        * @SuppressWarnings(empty-statement)
        */
        public int size() {
            try {
                int i = 0;
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT Nr FROM Eventos");
                for (;rs.next();i++);
                return i;
            }
            catch (Exception e) {throw new NullPointerException(e.getMessage());}
        }

        @Override
        public Collection<IEvento> values() {
        try {
            Set<IEvento> set = new HashSet();
            Set<Integer> keys = new HashSet(this.keySet());
            for(Integer key : keys)
                set.add(this.get(key));
            return set;
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
    }
    
        @Override
        public boolean equals(Object o){
            if(this==o) return true;
        
            else if(this.getClass() != o.getClass()) return false;
        
            else{
                EventoDAO edao = (EventoDAO) o;
            
                for(IEvento e : this.values()){
                    if(!edao.containsKey(e.getNr())) return false;
                    else{
                        if(!e.equals(edao.get(e.getNr()))) return false;
                    }
                }
                return true;
            }
        }

    @Override
    public int hashCode() {
        int hash = Arrays.hashCode(new Object[]{conn, parseTools});
        for(IEvento e : this.values())
            hash+=e.hashCode();
        return hash;
    }
    
    /**
    *  Procura a maior chave dum evento existente na base de dados e retorna
    *  esse valor incrementado em uma unidade
    *  @return Chave que identificará univocamente no sistema um evento. 
    */ 
    public int generateEventoKey(){
       try {
            if(!this.isEmpty()){
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT MAX(Nr) FROM Eventos");
                if(rs.next()){
                    return(rs.getInt(1) + 1);
                }
            } else return 1;
        } catch (Exception e) {return 1;}
        
        return -1;
    }
    
     /**
     * Fechar a ligação à base de dados.
     */
    public void close(){
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
