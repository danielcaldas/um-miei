package business.doacoes;

import java.util.GregorianCalendar;
import java.util.Set;

/***Interface que torna classe Evento acessível fora do package doações
 *
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 30.12.2014
 */
public interface IEvento {
    /*gets*/
    public int getNr();
    public int getNrPessoas();
    public GregorianCalendar getDataRealizacao();
    public float getTotalAngariado();
    public String getDesignacao();
    public String getNotas();
    public Set<Integer> getDonativos ();
    /*sets*/
    public void setNr(int nr);
    public void setNrPessoas (int nrP);
    public void setDataRealizacao (GregorianCalendar data);
    public void setTotalAngariado (float total);
    public void setDesignacao (String des);
    public void setNotas (String notas);
    public void setDonativos (Set<Integer> don);
    
    /*Equals, clone e hashcode*/
    @Override
    public boolean equals (Object o);
    public IEvento clone ();
    @Override
    public int hashCode ();
}
