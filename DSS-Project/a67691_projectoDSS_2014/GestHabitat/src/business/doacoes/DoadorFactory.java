
package business.doacoes;

/**Implementação de factory pattern que permite instanciar doadores do exterior.
 *
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 30.12.2014
 */
public class DoadorFactory {
    
    public DoadorFactory () {}
    public IDoador createDoador() {return new Doador();}
    
}
