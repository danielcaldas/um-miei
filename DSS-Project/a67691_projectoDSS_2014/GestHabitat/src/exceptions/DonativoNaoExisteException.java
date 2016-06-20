
package exceptions;

/**Excepção para tratar casos em que um donativo não existe.
 * 
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2015.01.07
 */
public class DonativoNaoExisteException extends Exception{
    
    public DonativoNaoExisteException (int nrRecibo)
    {
        super ("O donativo com o número de recibo "+nrRecibo+" não existe.");
    }
    
}
