package view;

import javax.swing.JOptionPane;

/**
 * Classe que faz construí diálogo do tipo: "Tem a certeza que pretente apagar...".
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.12.28
 */

public class AreYouSureDeleteDialog {
    
    // Variável de instância
    private final String message;
   
    /**
     * Construtor que torna método de display do diálogo acessível.
     * @param m, uma mensagem específica.
     */
    public AreYouSureDeleteDialog(String m){this.message=m;}
    
    /**
     * Método que faz display do diálogo de opção.
     * @return valor inteiro que define escolha do utilizador (sim/não).
     */
    public int show(){        
        return JOptionPane.showConfirmDialog(null, "Tem a certeza que pretende apagar "+this.message+"?", "Remover do sistema", JOptionPane.YES_NO_OPTION);
    }
}
