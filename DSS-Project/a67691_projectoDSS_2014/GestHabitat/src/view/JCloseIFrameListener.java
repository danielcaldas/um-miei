package view;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**Classe que permite fazer pop-up de diálogo "Tem a certeza que pretende saír?" antes de se fechar uma dada frame.
 *
 * @author Jorge Caldas, José Cortez, Marcelo Gonçalves, Ricardo Silva
 * @version 2014.12.26
 */
public class JCloseIFrameListener implements InternalFrameListener{

    private final JInternalFrame frame;
    
    public JCloseIFrameListener(JInternalFrame frame) {
        this.frame=frame;
    }
    
    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
    }

    /**Rescrever método que define ações a quando fecho.
     * 
     * @param e, evento. 
     */
    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        int option = JOptionPane.showConfirmDialog(null, "Tem a certeza que pretende saír?", "Saír", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION){
            try {
                this.frame.dispose();
            } catch (Exception ex) {
                Logger.getLogger(JCloseIFrameListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {
    }
    
}
