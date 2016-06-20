package business.doacoes;

import business.doacoes.Donativo;
import java.util.GregorianCalendar;
import java.util.Set;

/**Interface que torna classe Donativo acessível fora do package doacoes
 *
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 30.12.2014
 */
public interface IDonativo {
    
    /*gets*/
    public GregorianCalendar getData ();
    public int getNRecibo ();
    public String getObs();
    public Set<Integer> getProjetos();
    /*sets*/
    public void setData (GregorianCalendar data);
    public void setNRecibo (int nRecibo);
    public void setObs (String obs);
    public void setProjetos (Set<Integer> proj);
    
    /*equals, clone, hashcode*/
    @Override
    public boolean equals (Object o);
    public IDonativo clone();
    @Override
    public int hashCode(); 
    
    
    
}
