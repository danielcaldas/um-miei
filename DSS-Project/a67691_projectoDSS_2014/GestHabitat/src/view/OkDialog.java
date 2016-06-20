package view;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**Gera um diálogo simples com uma mensagem do sistema para o utilizador.
 * 
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2015.01.05
 */

public class OkDialog {
    // Variável de instância
    private final JFrame jframe;
    private final JInternalFrame jiframe;
    
    /**
     * Construtor que torna método de display do diálogo acessível.
     * @param f, uma frame. 
     */
    public OkDialog(Object f){
        if(f instanceof JFrame){
            this.jframe=(JFrame) f; jiframe=null;
        } else{
            this.jframe=null; jiframe=(JInternalFrame) f;
        }
    }
    
    /**
     * Método que faz display do diálogo.
     * @param message
     */
    public void show(String message){        
        if(this.jframe!=null){
            JOptionPane.showMessageDialog(this.jframe, message,
            "Info", JOptionPane.INFORMATION_MESSAGE);
        } else{
            JOptionPane.showMessageDialog(this.jiframe, message,
            "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }    
}
