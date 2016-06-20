package business.doacoes;

import java.util.GregorianCalendar;
import java.util.Set;
import java.util.HashSet;

/** Classe que agrega todos os campos comuns a um donativo
 *
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 30.12.2014
 */

abstract class Donativo implements IDonativo {
    //Variáveis de instância
    private int nRecibo;
    private GregorianCalendar data;
    private String obs;
    private Set<Integer> projetos;
    
    /**
     * Construtor vazio 
     */
    public Donativo ()
    {
        this.data = new GregorianCalendar();
        this.nRecibo = 0;
        this.obs="";
        this.projetos = new HashSet<Integer>();
    }
    
    /**Construtor parametrizado
     * 
     * @param data Data de emissão 
     * @param nrecibo Número do recibo
     * @param obs Observações adicionais
     * @param proj Lista de projetos para as quais este donativo foi aplicado
     */
    public Donativo (GregorianCalendar data, int nrecibo, String obs, Set<Integer> proj)
    {
        this.data = data;
        this.nRecibo = nrecibo;
        this.obs = obs;
        this.projetos = proj;
    }
    
    /**Construtor de cópia
     * 
     * @param d Um donativo
     */
    public Donativo (Donativo d)
    {
        this.data = d.getData();
        this.nRecibo = d.getNRecibo();
        this.obs = d.getObs();
        this.projetos = d.getProjetos();
    }
    
    /*gets*/
    @Override
    public GregorianCalendar getData() { return this.data;}
    @Override
    public int getNRecibo() {return this.nRecibo;}
    @Override
    public String getObs() { return this.obs;}
    @Override
    public Set<Integer> getProjetos() { return this.projetos;}
    /*sets*/
    @Override
    public void setData (GregorianCalendar data) {this.data = data;}
    @Override
    public void setNRecibo (int nRecibo) {this.nRecibo = nRecibo;}
    @Override
    public void setObs (String obs) {this.obs = obs;}
    @Override
    public void setProjetos (Set<Integer> proj) {this.projetos = proj;}
    
    /*Equals, clone e hashcode*/
    @Override
    public boolean equals (Object o)
    {
        if (this==o) return true;
        if (o==null || this.getClass()!= o.getClass()) return false;
        Donativo d = (Donativo) o;
        
        for (Integer i: projetos)
            if(!d.getProjetos().contains(i)) return false;
        return (this.data.equals(d.getData()) && this.nRecibo == d.getNRecibo() && this.obs.equals(d.getObs()));
    }
    
    @Override
    public abstract int hashCode ();
    
    @Override
    public abstract IDonativo clone ();
}
    