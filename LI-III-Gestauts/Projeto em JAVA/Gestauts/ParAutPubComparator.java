 
import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Tiago
 */
public class ParAutPubComparator implements Comparator<ParAutPub>, Serializable{    
    
    @Override
    public int compare (ParAutPub p1, ParAutPub p2){        
        if (p1.getCoautorias() > p2.getCoautorias()) return -1 ;
        
        else if(p1.comparaNomes(p2)) return 0; //são iguais
        
        else return 1;
    }   
    
}