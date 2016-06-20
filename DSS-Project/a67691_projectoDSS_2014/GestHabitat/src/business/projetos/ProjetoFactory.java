package business.projetos;

/**Implementação de factory pattern que permite instanciar Projetos do exterior.
 *
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 28.12.2014
 */
public class ProjetoFactory {
    public ProjetoFactory() {}
    public IProjeto createProjeto() { return new Projeto(); }
}
