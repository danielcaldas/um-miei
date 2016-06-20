package business.projetos;

import java.util.*;

/**
 * Classe que representa um Material.
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 30.12.2014
 */
class Material implements IMaterial{
    private int id;
    private String nome; 
    private String descricao;
    private int quantidade;
    
    /**
     * Construtor vazio.
     */
    public Material(){
        this.id = 0; 
        this.nome = ""; 
        this.descricao = "";
        this.quantidade = 0;
    }

    /**
     * Construtor parameterizado.
     * @param id, identificador de um Material
     * @param nome, nome do material 
     * @param descricao, descricao do material
     * @param quantidade, quantidade de material utilizado
     */
    public Material (int id, String nome,
            String descricao, int quantidade){
        this.id = id; 
        this.nome = nome; 
        this.descricao = descricao;
        this.quantidade = quantidade;
    }
    
    /**
     * Construtor de cópia.
     * @param m, um material.
     */
    public Material (Material m){
        this.id = m.getId(); 
        this.nome = m.getNome(); 
        this.descricao = m.getDesc(); 
        this.quantidade = m.getQTD();
    }
    
    /* Gets & Sets */
    @Override
    public int getId(){return this.id;}
    @Override
    public void setId(int id) {this.id = id;}
    @Override
    public int getQTD(){return this.quantidade;}
    @Override
    public void setQTD(int qtd) {this.quantidade = qtd;}
    @Override
    public String getNome(){return this.nome;}
    @Override
    public void setNome(String designacao){this.nome = designacao;}
    @Override
    public String getDesc(){return this.descricao;}
    @Override
    public void setDesc(String descricao){this.descricao = descricao;}
 
    /* Equals e Clone */
    @Override
    public boolean equals (Object o){
        if(this==o) return true;
        
        else if(o==null || this.getClass()!=o.getClass()) return false;
        
        else{
            Material m = (Material) o;
            return( this.id == m.getId() 
                    && this.nome.equals(m.getNome()) 
                    && this.descricao.equals(m.getDesc())
                    && this.quantidade == m.getQTD() );
        }
    }
    
    @Override
    public IMaterial clone(){
        return new Material(this);
    }
    
    @Override
    public int hashCode(){
        return Arrays.hashCode( new Object[] {this.id, this.nome, this.quantidade, this.descricao} );
    }
}
