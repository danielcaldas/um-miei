package business.projetos;

/**Implementação de factory pattern que permite instanciar Tarefas do exterior.
 *
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 30.12.2014
 */
public interface IMaterial {
    /* Gets & Sets */
    public int getId();
    public void setId(int id);
    public int getQTD();
    public void setQTD(int qtd);
    public String getNome();
    public void setNome(String designacao);
    public String getDesc();
    public void setDesc(String descricao);
 
    /* Equals e Clone */
    @Override
    public boolean equals (Object o);
    
    public IMaterial clone();
    
    @Override
    public int hashCode();
}
