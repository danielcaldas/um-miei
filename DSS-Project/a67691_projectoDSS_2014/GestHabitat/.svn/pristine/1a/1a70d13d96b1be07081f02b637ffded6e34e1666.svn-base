package view;

import java.util.Comparator;

/**Comparator genérico para linhas de tabelas.
 *
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.15.12
 * 
 */

public class RowComparator implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        if(o1 instanceof Integer){
            int a=(Integer)o1;
            int b=(Integer)o2;
            if(a>=b) return 1;
            else return -1;            
        } else if(o1 instanceof Float){
            float a=(Float)o1;
            float b=(Float)o2;
            if(a>=b) return 1;
            else return -1; 
        } else if(o1 instanceof Double){
            Double a=(Double)o1;
            Double b=(Double)o2;
            if(a>=b) return 1;
            else return -1;
        } else if (o1 instanceof String){
            String a=(String)o1;
            String b=(String)o2;
            return a.compareToIgnoreCase(b);
        } else return 1;
    }
}
