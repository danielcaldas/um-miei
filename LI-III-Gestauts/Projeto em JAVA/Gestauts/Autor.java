 


/**
 *
 * @author jdc
 * @version 23/05/2014
 */

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
public class Autor implements Serializable{
    
    //Variáveis de instância
    private String nome;
    private int totalSolo;
    private int totalNSolo;
    private TreeMap<String,Integer> coauts; //KEY:Nome do coautor  VALUE:Nº de coautorias com o autor
    
    
    //Construtores
    public Autor(){
        this.nome = "";
        this.totalNSolo = 0;
        this.totalSolo=0;
        this.coauts = new TreeMap<>();
    }
    
    public Autor(String nome, int solo, int nsolo, TreeMap<String,Integer> coauts){
        this.nome = nome;
        this.totalSolo=solo;
        this.totalNSolo = nsolo;
        this.coauts = new TreeMap<>();
        
        for(Map.Entry<String,Integer> entry : coauts.entrySet()){
            this.coauts.put(entry.getKey(),entry.getValue());
        }
    }
    
    //Construtor extra, útil para inserções
    public Autor(String nome, int solo, int nsolo){
        this.nome = nome;
        this.totalSolo = solo;
        this.totalNSolo = nsolo;
        
        this.coauts = new TreeMap<>();
    }
    
    public Autor(Autor a){
        this(a.getNome(),a.getSolo(),a.getNSolo(),a.getCoAutores());
    }
    
    
    //Métodos de instância
    
    //gets e sets
    
    public String getNome(){return this.nome;}
    public int getSolo(){return this.totalSolo;}
    public int getNSolo(){return this.totalNSolo;}
    public int getTotal(){return this.totalNSolo+this.totalSolo;}
    public TreeMap<String,Integer> getCoAutores(){
        TreeMap<String,Integer> aux = new TreeMap<>();
        
        for(Map.Entry<String,Integer> entry : this.coauts.entrySet())
            aux.put(entry.getKey(),entry.getValue());
        
        return aux;
    }

    
    public TreeSet<String> getNomesCoAutores(){
        TreeSet<String> aux = new TreeSet<>();
        
        for(String s : this.coauts.keySet())
            aux.add(s);
        
        return aux;
    }
    
    public void setNome(String nome){this.nome = nome;}
    public void addSolo(int solo){this.totalSolo += solo;}
    public void addNSolo(int nsolo){this.totalNSolo += nsolo;}
    public void setCoAutores(TreeMap<String,Integer> aux){
        
        this.coauts = new TreeMap<>();
        for(Map.Entry<String,Integer> entry : aux.entrySet())
            this.coauts.put(entry.getKey(),entry.getValue());
    }
    //Métodos para incrementos dos contadores de publicações
    public void incTotalSolo(){this.totalSolo++;}
    public void incTotalNSolo(){this.totalNSolo++;}
    
    
    
    
    //equals, clone e toString
    @Override
    public boolean equals (Object o){
        if(this==o) return true;
        
        else if(o==null || this.getClass() != o.getClass()) return false;
        
        else{
                Autor a = (Autor) o;
                
                return (this.nome.equals(a.getNome()));
        }
    }
    
    @Override
    public Autor clone(){
        return new Autor(this);
    }
    
    @Override
    //método toString fornece informações básicas acerca do autor
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n########## AUTOR: ").append(this.nome).append(" ##########\n");
        sb.append("Total de publicações: ").append(this.getTotal()).append("\n");
        sb.append("A solo: ").append(this.totalSolo).append("\n");
        sb.append("Com outros autores: ").append(this.totalNSolo).append("\n\n");
        
        return sb.toString();
    }
    
    @Override
    public int hashCode(){
        return Arrays.hashCode(new Object[]{this.nome,this.totalNSolo,this.totalSolo,this.coauts});
    }
    
    
    
    
    //Método que devolve String com tabela de coautorias do autor
    public String tabelaCoAutores(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n###### LISTA DE COAUTORES DE: ").append(this.nome).append(" ######\n");
        for(Map.Entry<String,Integer> entry : this.coauts.entrySet()){
            sb.append("Coautor: ").append(entry.getKey()).append("         Coautorias: ").append(entry.getValue());
        }
        
        return sb.toString();
    }
    
    
    
    /**
     * 
     * @param coautor, nome de um coautor deste autor
     * Método que adiciona (ou atualiza) um coautor ao TreeMap de coautores deste autor.
     */
    public void addCoAutor (String coautor){
        
      if(this.coauts.containsKey(coautor)){
           int x = this.coauts.get(coautor);
           x++;
           this.coauts.put(coautor,x);
      }
      else{this.coauts.put(coautor,1);} //um novo coaut
      
    }
    
    
    
}
