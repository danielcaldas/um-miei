package business.familias;

import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 30.12.2014
 */

public interface ICandidatura {    
    
    
   // Get`s e Set`s
    public int getNr();
    public void setNr(int nr);
    public GregorianCalendar getDataSubmissao();
    public void setDataSubmissao(GregorianCalendar dataSubmissao);
    public GregorianCalendar getDataDecisao();
    public void setDataDecisao(GregorianCalendar dataDecisao);
    public String getEstado();
    public void setEstado(String estado);
    public int getFuncionarioRegistou();
    public void setFuncionarioRegistou(int funcionarioRegistou);
    public int getFuncionarioAprovou();
    public void setFuncionarioAprovou(int funcionarioAprovou);
    public List<Integer> getMembros();
    public void setMembros(List<Integer> membros);
    public int getRepresentante();
    public void setRepresentante(int representante);
    public String getDescricao();
    public void setDescricao(String descricao);
    
// Clone
    public ICandidatura clone();

// Equals
    @Override
    public boolean equals(Object obj);
    
    @Override
    public int hashCode();
    
    public void add(int m);
    
    public void rem(int m);
}
