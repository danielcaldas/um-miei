package exceptions;

/**Excepção para tratar casos em que uma equipa não existe.
 * 
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2015.01.06
 */

public class EquipaNaoExisteException extends Exception {
    public EquipaNaoExisteException(int id){
        super("Equipa com id: "+id+" não existe");
    }
    
    public EquipaNaoExisteException(String pais){
        super("Equipa com país de origem: "+pais+" não existe");
    }
}
