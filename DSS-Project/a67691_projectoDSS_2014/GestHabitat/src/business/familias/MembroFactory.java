package business.familias;

/**
 *
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 30.12.2014
 */

public class MembroFactory {
    public MembroFactory(){}
    public IMembro createMembro() {return new Membro(); }
}
