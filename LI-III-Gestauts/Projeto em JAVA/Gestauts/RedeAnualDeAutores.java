 

/**
 *
 * @author jdc
 * @version 23/05/2014
 */

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Arrays;
public class RedeAnualDeAutores implements Serializable {
    
    //Variáveis de instância
    private int ano;
    private int total;                     //total de publicações neste ano 
    private TreeMap<String,Autor> autores; //KEY: nome do autor  VALUE: objecto Autor correspondente à chave
    
    
    
    //Construtores (moldados ao problema)
    public RedeAnualDeAutores(int ano){
        this.ano = ano;
        this.total = 1;
        this.autores = new TreeMap<>();
    }
    
    public RedeAnualDeAutores(int ano, int total, TreeMap<String,Autor> autores){
        this.ano = ano;
        this.total = total;
        this.autores =  new TreeMap<>();
        
        for(Map.Entry<String,Autor> entry : autores.entrySet())
            this.autores.put(entry.getKey(),entry.getValue().clone());
    }
    
    
    public RedeAnualDeAutores(RedeAnualDeAutores raa){
        this(raa.getAno(),raa.getTotal(),raa.getAutoresDoAno());
    }
    
    
    
    //Métodos de instãncia
    //gets e sets
    
    public int getAno(){return this.ano;}
    public int getTotal(){return this.total;}
    public TreeMap<String,Autor> getAutoresDoAno(){
        TreeMap<String,Autor> aux = new TreeMap<>();
        
        for(Map.Entry<String,Autor> entry : this.autores.entrySet())
            aux.put(entry.getKey(),entry.getValue().clone());
        
        return aux;
    }
    
    //método que devolve autor caso o mesmo tenha publicado neste ano
    public Autor getAutor(String autor){
        if(!this.autores.containsKey(autor)) return null;
        
        return this.autores.get(autor).clone();
    }
    
    
    public void setAno(int ano){this.ano = ano;}
    public void setTotal(int total){this.total = total;}
    public void setAutores(TreeMap<String,Autor> aux){
        for(Map.Entry<String,Autor> entry : aux.entrySet()){
            this.autores.put(entry.getKey(),entry.getValue().clone());
        }
    }
    
    
    
    //equals, clone e toString
    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        
        else if(o==null || o.getClass() != this.getClass()) return false;
        
        else{
                RedeAnualDeAutores raa = (RedeAnualDeAutores) o;
                
                return (this.ano==raa.getAno());
        }
    }
    
    @Override
    public RedeAnualDeAutores clone(){
        return new RedeAnualDeAutores(this);
    }
    
    @Override
    //Informação básica acerca deste ano de publicações
    public String toString(){
        return("###### ANO: "+this.ano+" ######\n"
                +"Total de publicações: "+this.total+"\n"
                +"Total de autores que publicaram neste ano: "+this.numAutoresQuePublicaram()+"\n");
    }
    
    
    
    //Método que informa quantos autores (distintos) publicaram neste ano
    public int numAutoresQuePublicaram(){
        return this.autores.size();
    }

    
    //Método que testa se uma dado autor já foi adiconado no map de autores
    public boolean jaAdicionado(String nome){
        return this.autores.containsKey(nome);
    }
    
    //Método que incrementa o total de publicações deste ano
    public void incTotalPublicacoesDoAno(){this.total++;}
    
    /**
     * 
     * @param autores, nomes dos autores de uma publicação
     * Método que adiciona os autores de uma publicação à rede deste ano
     */
    public void addAutoresRedeDoAno(HashSet<String> autores){

        //uma publicação a solo
        if(autores.size()==1){
            String aut = autores.iterator().next();
            
            if(this.autores.containsKey(aut)){//autor já existe
                this.autores.get(aut).incTotalSolo();
            }
            else{//autor surge pela 1ª vez
                Autor a = new Autor();
                a.setNome(aut);
                a.incTotalSolo();
                this.autores.put(a.getNome(),a);
            }
        }
        
        //publicação com mais de um autor
        else{
        
                for(String autor : autores){

                    if(this.autores.containsKey(autor)){//autor já presente na estrutura            
                        for(String autorin : autores){
                            if(!autor.equals(autorin)){//se não for o próprio
                               this.autores.get(autor).addCoAutor(autorin);
                            }
                        }                       
                        
                        this.autores.get(autor).incTotalNSolo();
                    }
                    
                    else{//autor surge pela 1ª vez
                            Autor a = new Autor(autor,0,1);
            
                            for(String autorin : autores){
                                if(!autor.equals(autorin)){
                                    a.addCoAutor(autorin);
                                }
                            }

                            this.autores.put(autor,a);
                    }
               }
        }
    }
    
    
    //Método que devolve um set dos autores que publicaram nesse ano por ordem decrescente do nº de publicações
    public TreeSet<Autor> getSetOrdenadoAutores(){ /*UTIL 2.1 a)*/
        TreeSet<Autor> aux = new TreeSet<>(new AutorPubsComparator());
        
        for(Map.Entry<String,Autor> entry : this.autores.entrySet())
            aux.add(entry.getValue().clone());
        
        return aux;
    }
    
    public HashSet<String> getNomesDeAutores(){
        HashSet<String> aux = new HashSet<>();
        
        for(Autor a : this.autores.values())
            aux.add(a.getNome());
        
        return aux;
    }
    
    
    
    //Método que devolve o nº de publicações de um dado autor neste ano caso nele tenha publicado
    public int publicacoesDoAutorNoAno(String autor){
        if(this.autores.containsKey(autor))
            return this.autores.get(autor).getTotal();
        else return 0;
    }
    
          
    @Override
    public int hashCode(){
        return Arrays.hashCode(new Object[]{this.ano,this.total,this.autores});
    }
    
}
