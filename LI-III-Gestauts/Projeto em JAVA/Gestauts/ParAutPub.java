 

import java.util.Arrays;

/**
 *
 * @author Tiago
 */
public class ParAutPub {
    
    
    private String autor1;
    private String autor2;
    private int coautorias;
    
    
    
    
    // Metodo q compara par de autores pelos nomes ComparaNomes (ParAutPub p)
     
    public ParAutPub () {
        this.autor1 = " ";
        this.autor2 = " ";
        this.coautorias = 0;
        
    }
    
    public ParAutPub (String a1, String a2, int CoAut) {
        this.autor1 = a1;
        this.autor2 = a2;
        this.coautorias = CoAut;
    }
    
    public ParAutPub(ParAutPub pa){
        this(pa.getAutor1(),pa.getAutor2(),pa.getCoautorias());
    }
    
    //Métodos de instância
    public String getAutor1() {return this.autor1;}
    public String getAutor2() {return this.autor2;}
    public int getCoautorias() {return this.coautorias;}
    
    public void setAutor1(String a1) {this.autor1 = a1;}
    public void setAutor2(String a2) {this.autor2 = a2;}
    public void setCoautorias (int CoAut) {this.coautorias = CoAut;}  
    public void addCoautorias (int c){this.coautorias+=c;}
    
    
    
    //Método que permite verificar se um par de autores é o mesmo que outro
    public boolean comparaNomes (ParAutPub p){
        if(p.getAutor1().equals(this.autor1) && p.getAutor2().equals(this.autor2)){
            return true; 
        }
        if(p.getAutor1().equals(this.autor2) && p.getAutor2().equals(this.autor1)){//os nomes não estão pela 1ª ordem em que apareceram
            return true;
        }
        return false;        
    }
    
    //equals e clone
    @Override
    public boolean equals (Object o){
        if(this==o) return true;
        
        else if(o==null || this.getClass() != o.getClass()) return false;
        
        else{
                ParAutPub a = (ParAutPub) o;
                
                return (this.comparaNomes(a)); // pares se autores são iguais se são os mesmos nomes
        }
    }
    
    @Override
    public ParAutPub clone(){
        return new ParAutPub(this);
    }
    
    @Override
    public String toString(){
        return("Autores: "+this.autor1+" & "+this.autor2+" Coautorias: "+this.coautorias);
    }
    
    
    /*Neste hashcode teríamos pares repetidos devido à ordem dos objectos (Strings)*/
    @Override
    public int hashCode(){
        return Arrays.hashCode(new Object[]{this.autor1,this.autor2,this.coautorias});
    }
    
    /*@Override
    public int hashCode(){
        return this.autor1.hashCode() + this.autor2.hashCode(); //propriedade comutativa da adição para A e B ou B e A o hascode é o mesmo
    }*/
    
}