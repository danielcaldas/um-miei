
import java.io.Serializable;
import java.util.concurrent.locks.*;

/**
 * Classe que define uma ferramenta que é na essência a sua quantidade em stock. 
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.12.13
 */

public class Product implements Serializable {
    private int quantity;
    private Condition c;
    
    public Product(int s, Lock l){
        this.quantity = s;
        c = l.newCondition();
    }    

    public int getQuantity() { return this.quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public void incQuantity(int q) { this.quantity += q; }
        
    public void decQuantity(int x) { this.quantity -= x; }
    
    public Condition getC() { return this.c; }

    public void setC(Condition c) { this.c = c; }
        
    public void signalP(){ this.c.signalAll(); }
       
    public void awaitP() throws InterruptedException { this.c.await(); }
}
