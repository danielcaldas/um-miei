package business.doacoes;

/**
 * Classe factory dos diversos sub tipos de donativo.
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.30.12
 */
public class DonativoFactory {
 
    public DonativoFactory(){}
    
    public IDonativo createDonativo(String className){
        
        if(className.equalsIgnoreCase("DONATIVOMATERIAL")){
                return new DonativoMaterial();
        } else if(className.equalsIgnoreCase("DONATIVOMONETARIO")){
                return new DonativoMonetario();
        } else if(className.equalsIgnoreCase("DONATIVOSERVICOS")){
                return new DonativoServicos();
        } else{
                return null;
        }
    }
}
