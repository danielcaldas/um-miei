package business.projetos;

import java.util.*;

/**
 * Interface da classe Tarefa.
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 29.12.2014
 */
public interface ITarefa {
    public int getId();
    public void setId(int id);
    public GregorianCalendar getDataInicioT();
    public void setDataInicioT(GregorianCalendar data);
    public GregorianCalendar getDataFinalT();
    public void setDataFinalT(GregorianCalendar data);
    public String getDesig();
    public void setDesig(String designacao);
    public HashMap<Integer, Integer> getMaterial();
    public void setMaterial(HashMap<Integer,Integer> r);
    public String getDesc();
    public void setDesc(String desc);   


    /* Equals e Clone */
    @Override
    public boolean equals (Object o);
    
    public ITarefa clone();
    
    @Override
    public int hashCode();
    
    /**
     * Método que permite saber se a tarefa ja terminou ou não
     * @return boolean, true se a tarefa já terminou
     */
    public boolean isFinished();

}
