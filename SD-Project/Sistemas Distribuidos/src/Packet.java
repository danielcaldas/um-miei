
import java.io.Serializable;
import java.util.*;

/**
 * Pequeno pacote de dados que serve de estafeta, encapsulando os dados que são
 * transmitidos entre servidor-cliente e vice-versa.
 * 
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.12.15
 */

public class Packet implements Serializable{
    private String action;
    private HashMap<String,String> args; 

    public Packet(String a) {
        this.action = a;
        args = null;
    }
    
    public Packet(String a, HashMap<String,String> args) {
        this.action = a;
        this.args = args;
    }

    public String getAction() { return action; }

    public HashMap<String, String> getArgs() { return args; }
}

