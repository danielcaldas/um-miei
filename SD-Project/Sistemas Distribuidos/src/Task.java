
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.*;

/**
 * Class que define um tipo de tarefa definida por um funcionário.
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.12.15
 */

class Task implements Serializable {
    private String type; /* identificador da tarefa */
    private Map<String,Integer> objects; /* Conjunto de materiais e quantidades a consumir */
    private Condition c; 

    
    public Task(Lock l){
        type="";
        objects= new HashMap<>();
        c = l.newCondition();
    }
    
    public Task(String type, Map<String,Integer> objects, Lock l){
        this.type=type;
        this.c = l.newCondition();
        this.objects = new HashMap<>();
        for(Map.Entry<String,Integer> entry : objects.entrySet()){
            this.objects.put(entry.getKey(), entry.getValue());
        }
    }
    
    public String getType(){return this.type;}
    
    public Map<String,Integer> getObjects(){
        HashMap<String,Integer> aux = new HashMap<>();
        
        for(Map.Entry<String,Integer> entry : this.objects.entrySet()){
            aux.put(entry.getKey(),entry.getValue());
        }
        return aux;
    }
    
    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        
        else if(o==null || this.getClass()!=o.getClass()) return false;
        
        else{
            Task t = (Task) o;
            
            for(Map.Entry<String,Integer> entry : t.getObjects().entrySet()){
                if(!this.objects.containsKey(entry.getKey())) return false;
                else{
                    if(this.objects.get(entry.getKey()) != entry.getValue()) return false;
                }
            }
            return (this.type.equals(t.getType()));
        }
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("Tarefa: ").append(this.type).append("\n");
        sb.append("Lista de requisitos:\n");
        for(Map.Entry<String,Integer> entry : this.objects.entrySet()){
            sb.append("Material: ").append(entry.getKey()).append("     Qtd: ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
    
    /* Adicionar objeto */
    public void addObject(String material, int qtd){
        this.objects.put(material,qtd); // mesmo que exista já um registo, o mesmo é atualizado
    }
    
    /* Remover um objeto */
    public void remObject(String obj){
        this.objects.remove(obj);
    }
    
    /* Ir buscar um específico objeto */
    public int getQtdObject(String obj){
        return this.objects.get(obj);
    }
    
    public void signalP(){ this.c.signalAll(); }
       
    public void awaitP() throws InterruptedException { this.c.await(); }
    
}
