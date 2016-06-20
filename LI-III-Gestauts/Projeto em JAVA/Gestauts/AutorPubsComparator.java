 

/**
 *
 * @author jdc
 * @version 29/05/2014
 */

import java.io.Serializable;
import java.util.Comparator;
public class AutorPubsComparator implements Comparator<Autor>, Serializable {
    
    @Override
    public int compare(Autor a1, Autor a2){
        int a = a1.getTotal();
        int b = a2.getTotal();
        
        if(a>b) return -1;
        else if(a<b) return 1;
        else return a1.getNome().compareTo(a2.getNome());
    }
}
