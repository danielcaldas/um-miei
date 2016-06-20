package exceptions;

/**Excepção para tratar casos em que um voluntário não existe.
 * 
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2015.01.06
 */

public class VoluntarioNaoExisteException extends Exception{
    public VoluntarioNaoExisteException(int id){
        super("Voluntário com id: "+id+" não existe");
    }
    
    public VoluntarioNaoExisteException(String nome){
        super("Voluntário com nome: "+nome+" não existe");
    } 
}
