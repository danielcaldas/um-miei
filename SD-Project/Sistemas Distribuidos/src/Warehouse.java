
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.*;

/**
 * Armazém onde são guardadas as ferramentas e feito o controlo de acesso às mesmas.
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.12.13
 */
public class Warehouse implements Serializable {
    private Map<String,Product> m;
    private Lock l;
    
    public Warehouse(){
        this.m = new HashMap<>();
        this.l = new ReentrantLock();
    }

    public HashMap<String,Product> getStock(){return (HashMap<String,Product>)this.m;}
    
    public void supply(String item, int quantity) throws InterruptedException {
        l.lock();
        try {
            Product p = m.get(item);
            
            if(p == null){
                p = new Product(quantity, l);
                m.put(item,p);
            }
            else {
                p.incQuantity(quantity);
                p.signalP();
            }
        } finally { l.unlock(); }
    }
    
    public Map<String,Product> consume (Map<String,Integer> items) throws InterruptedException {
        Map<String,Product> r = new HashMap<>();
        l.lock();
        try {
            String n;
            boolean i = true;
            while(i){
                i = false;
                for (Map.Entry<String,Integer> item : items.entrySet()) {
                    Product p = m.get(item.getKey());
                
                    if(p == null){
                        p = new Product(0,l);
                        m.put(item.getKey(),p);
                    }
                    if (p.getQuantity ()< item.getValue()) {
                        p.awaitP();
                        i = true;
                        break;
                    }
                }
            }
            
            for (Map.Entry<String,Integer> item : items.entrySet()) {
                Product p = m.get(item.getKey());
                r.put(item.getKey(), p);
                p.decQuantity(item.getValue());
            }
            
            return r;
            
        } finally { l.unlock(); }
    }
}